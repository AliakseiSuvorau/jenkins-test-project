package utils.serializers.factories;

import org.mdkt.compiler.InMemoryJavaCompiler;
import utils.serializers.Serializer;

import java.lang.reflect.AccessFlag;

public class NoReflectionSerializerFactory<T> implements SerializerFactory<T> {
    public String generateSerializer(Class<?> clazz) {
        String className = clazz.getSimpleName() + "Serializer";
        StringBuilder javaCode = new StringBuilder();

        javaCode.append("package utils.serializers;\n\n");
        javaCode.append("import java.util.*;\n");
        javaCode.append("import org.apache.commons.lang3.ArrayUtils;\n");
        javaCode.append("import utils.serializers.Serializer;\n\n");
        javaCode.append("public class ").append(className).append(" implements Serializer {\n");

        javaCode.append("""
                    private StringBuilder builder;
                    private String objectOffset = "";
                    private static final String DATA_OFFSET = "  ";
                    private static final String DELTA_OFFSET = "  ";
                    
                """);

        // Constructors
        javaCode.append("public ").append(className).append("() {}\n")
                .append("\n")
                .append("public ").append(className).append("(String offset) {\n")
                .append("    this.objectOffset = offset;\n")
                .append("}\n")
                .append("\n");

        javaCode.append("""
                    private boolean isComplexStructure(Object obj) {
                        return !(obj instanceof String) &&
                               !(obj instanceof Byte) &&
                               !(obj instanceof Short) &&
                               !(obj instanceof Integer) &&
                               !(obj instanceof Long) &&
                               !(obj instanceof Float) &&
                               !(obj instanceof Double) &&
                               !(obj instanceof Character) &&
                               !(obj instanceof Boolean);
                    }
                    
                """);

        javaCode.append("""
                    private void writeMapPair(String key, Object value, String mapOffset) {
                        boolean keyIsComplexStructure = isComplexStructure(value);
                    
                        if (keyIsComplexStructure) {
                            builder.append("\\n").append(mapOffset).append(DATA_OFFSET);
                        }
                    
                        builder.append("\\"").append(key).append("\\": ");
                    
                        if (keyIsComplexStructure) {
                            builder.append("\\n");
                        }
                    
                        writeObject(value, mapOffset + DATA_OFFSET);
                    }
                    
                """);

        javaCode.append("""
                    private void writeMap(Map<?, ?> map, String mapOffset) {
                                            if (!map.isEmpty()) {
                            builder.append("\\n").append(mapOffset).append(DATA_OFFSET);
                        }
                        builder.append("{");
                        if (!map.isEmpty()) {
                            builder.append(" ");
                        }
                
                        List<?> keyList = new ArrayList<>(Arrays.asList(map.keySet().toArray()));
                        for (int i = 0; i < keyList.size(); i++) {
                            var key = keyList.get(i);
                            var value = map.get(key);
                
                            writeMapPair(key.toString(), value, mapOffset + DATA_OFFSET);
                            if (i < keyList.size() - 1) {
                                builder.append(", ");
                            }
                        }
                
                        if (!map.isEmpty()) {
                            builder.append("\\n").append(mapOffset).append(DATA_OFFSET);
                        }
                        builder.append("}");
                    }
                
                """);

        javaCode.append("""
                    private void writeFieldName(String fieldName) {
                        builder.append(objectOffset).append(DATA_OFFSET).append("\\"").append(fieldName).append("\\": ");
                    }
                
                """);

        javaCode.append("""
                    private void writeObject(Object obj, String prevObjOffset) {
                        builder.append(prevObjOffset);
                        if (obj == null) {
                            builder.append("null");
                        } else {
                            if (obj instanceof Byte || obj instanceof Short || obj instanceof Integer || obj instanceof Long ||
                                obj instanceof Float || obj instanceof Double || obj instanceof Boolean) {
                                builder.append(obj);
                            } else {
                                if (obj instanceof Character || obj instanceof String) {
                                    builder.append("\\"").append(obj).append("\\"");
                                } else {
                    """)
                .append(className).append("                    serializer = new ").append(className).append("(prevObjOffset + DELTA_OFFSET);\n")
                .append("""
                                    builder.append(serializer.serialize(obj));
                                }
                            }
                        }
                    }
                
                    """);

        javaCode.append("""
                    private void writeArrayObject(Object object, boolean isLast, String prevObjOffset) {
                        writeObject(object, prevObjOffset + DATA_OFFSET);
                
                        if (!isLast) {
                            builder.append(",");
                        }
                
                        builder.append("\\n");
                    }
                
                """);

        javaCode.append("""
                    private void writeFieldValue(Object fieldValue, boolean last) {
                                            if (fieldValue == null) {
                            builder.append("null");
                        } else {
                            if (fieldValue instanceof byte[] || fieldValue instanceof short[] || fieldValue instanceof int[] ||
                                fieldValue instanceof long[] || fieldValue instanceof float[] || fieldValue instanceof double[] ||
                                fieldValue instanceof char[] || fieldValue instanceof boolean[] || fieldValue instanceof Object[]) {
                                
                                //Object[]
                                Object[] objArray = switch (fieldValue) {
                                                        case byte[] bytes -> ArrayUtils.toObject(bytes);
                                                        case short[] shorts -> ArrayUtils.toObject(shorts);
                                                        case int[] ints -> ArrayUtils.toObject(ints);
                                                        case long[] longs -> ArrayUtils.toObject(longs);
                                                        case float[] floats -> ArrayUtils.toObject(floats);
                                                        case double[] doubles -> ArrayUtils.toObject(doubles);
                                                        case char[] chars -> ArrayUtils.toObject(chars);
                                                        case boolean[] booleans -> ArrayUtils.toObject(booleans);
                                                        default -> (Object[]) fieldValue;
                                                    };
                
                                boolean isEmpty = objArray.length == 0;
                
                                builder.append("[");
                                if (!isEmpty) {
                                    builder.append("\\n");
                                }
                
                                for (int i = 0; i < objArray.length; i++) {
                                    writeArrayObject(objArray[i], i == objArray.length - 1, objectOffset + DATA_OFFSET);
                                }
                
                                if (!isEmpty) {
                                    builder.append(DATA_OFFSET).append(objectOffset);
                                }
                                builder.append("]");
                            } else {
                                if (fieldValue instanceof Collection) {
                                    // Collection<?>
                                    Object[] objArray = ((Collection<?>) fieldValue).toArray();
                
                                    builder.append("[");
                                    builder.append("\\n");
                
                                    for (int i = 0; i < objArray.length; i++) {
                                        writeArrayObject(objArray[i], i == objArray.length - 1, objectOffset + DATA_OFFSET);
                                    }
                                    
                                    builder.append(DATA_OFFSET).append(objectOffset);
                                    builder.append("]");
                                } else {
                                    if (fieldValue instanceof Map) {
                                        // Map
                                        writeMap((Map<?, ?>) fieldValue, objectOffset + DATA_OFFSET);
                                    } else {
                                        // Other
                                        writeObject(fieldValue, objectOffset);
                                    }
                                }
                            }
                        }
                
                        if (!last) {
                            builder.append(",");
                        }
                        builder.append("\\n");
                    }
                
                    """);

        javaCode.append("    public String serialize(final Object object) {\n");

        var fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            javaCode.append("        return \"{}\";\n    }\n}");
            return javaCode.toString();
        }

        javaCode.append("""
                                this.builder = new StringBuilder();
                                builder.append(objectOffset).append("{\\n");
                        
                        """);

        for (int i = 0; i < fields.length; i++) {
            var field = fields[i];

            if (field.accessFlags().contains(AccessFlag.PUBLIC)) {
                javaCode.append("        writeFieldName(\"").append(field.getName()).append("\");\n");
                javaCode.append("        writeFieldValue(((").append(clazz.getName()).append(") object).").append(field.getName()).append(", ").append(i == fields.length - 1 ? "true);\n" : "false);\n");
            } else {
                String getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

                try {
                    clazz.getMethod(getterName);
                } catch (NoSuchMethodException e) {
                    System.err.println("Private field \"" + field.getName() + "\" does not have a public getter. Ignoring...");
                    continue;
                }

                javaCode.append("        writeFieldName(\"").append(field.getName()).append("\");\n");
                javaCode.append("        ").append(field.getType().getSimpleName()).append(" fieldValue").append(i).append(" = ((").append(clazz.getName()).append(") object).").append(getterName).append("();\n");
                javaCode.append("        writeFieldValue(fieldValue").append(i).append(", ").append(i == fields.length - 1 ? "true);\n" : "false);\n");
            }
        }

        javaCode.append("""
                                builder.append(objectOffset).append("}");
                        
                                return builder.toString();
                            }
                        }
                        
                        """);

        return javaCode.toString();
    }

    @Override
    public Serializer<T> getSerializer(Class<?> clazz) {
        String serializerJavaCode = generateSerializer(clazz);
        String className = clazz.getSimpleName();

        try {
            Class<?> serializerClass = InMemoryJavaCompiler.newInstance().compile("utils.serializers." + className + "Serializer", serializerJavaCode);
            return (Serializer) serializerClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
