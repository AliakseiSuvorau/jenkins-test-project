package utils.mappers;

import java.lang.reflect.Array;
import java.util.*;

final public class JSONMapper {
    private StringBuilder builder;
    private String objectOffset = "";
    private static final String DATA_OFFSET = "  ";
    private static final String DELTA_OFFSET = "  ";

    public JSONMapper() {
    }

    public JSONMapper(String offset) {
        this.objectOffset = offset;
    }

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

    private void writeMapPair(String key, Object value, String mapOffset) {
        boolean keyIsComplexStructure = isComplexStructure(value);

        if (keyIsComplexStructure) {
            builder.append("\n").append(mapOffset).append(DATA_OFFSET);
        }

        builder.append("\"").append(key).append("\": ");

        if (keyIsComplexStructure) {
            builder.append("\n");
        }

        writeObject(value, mapOffset + DATA_OFFSET);
    }

    private void writeMap(Map<?, ?> map, String mapOffset) {
        if (!map.isEmpty()) {
            builder.append("\n").append(mapOffset).append(DATA_OFFSET);
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
            builder.append("\n").append(mapOffset).append(DATA_OFFSET);
        }
        builder.append("}");
    }

    private void writeFieldName(String fieldName) {
        builder.append(objectOffset).append(DATA_OFFSET).append("\"").append(fieldName).append("\": ");
    }

    private boolean isPrimitiveNotChar(Object obj) {
        return obj instanceof Byte  || obj instanceof Short  || obj instanceof Integer || obj instanceof Long ||
               obj instanceof Float || obj instanceof Double || obj instanceof Boolean;
    }

    private void writeObject(Object obj, String prevObjOffset) {
        if (obj == null) {
            builder.append("null");
        } else {
            if (isPrimitiveNotChar(obj)) {
                builder.append(obj);
                return;
            }
            if (obj instanceof Character) {
                builder.append("\"").append(obj).append("\"");
                return;
            }
            if (obj instanceof String) {
                builder.append("\"").append(obj).append("\"");
                return;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            var map = objectMapper.toMap(obj);

            JSONMapper serializer = new JSONMapper(prevObjOffset);
            builder.append(serializer.toJSON(map));
        }
    }

    private void writeArrayObject(Object object, boolean isLast, String prevObjOffset) {
        writeObject(object, prevObjOffset + DATA_OFFSET);

        if (!isLast) {
            builder.append(",");
        }

        builder.append("\n");
    }

    private void writeFieldValue(Object fieldValue, boolean last) {
        if (fieldValue == null) {
            builder.append("null");
        } else {
            if (fieldValue.getClass().isArray()) {
                //Object[]
                boolean isEmpty = Array.getLength(fieldValue) == 0;

                builder.append("[");
                if (!isEmpty) {
                    builder.append("\n");
                }

                for (int i = 0; i < Array.getLength(fieldValue); i++) {
                    if (!isComplexStructure(Array.get(fieldValue, i))) {
                        builder.append(objectOffset).append(DELTA_OFFSET).append(DATA_OFFSET);
                    }
                    writeArrayObject(Array.get(fieldValue, i), i == Array.getLength(fieldValue) - 1, objectOffset + DATA_OFFSET);
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
                    if (objArray.length > 0) {
                        builder.append("\n");
                    }

                    for (int i = 0; i < objArray.length; i++) {
                        if (!isComplexStructure(objArray[i])) {
                            builder.append(objectOffset).append(DELTA_OFFSET).append(DATA_OFFSET);
                        }
                        writeArrayObject(objArray[i], i == objArray.length - 1, objectOffset + DATA_OFFSET);
                    }

                    if (objArray.length > 0) {
                        builder.append(DATA_OFFSET).append(objectOffset);
                    }
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
        builder.append("\n");
    }

    public String toJSON(Map<String, Object> map) {
        this.builder = new StringBuilder();
        if (map.isEmpty()) {
            builder.append("{}");
            return builder.toString();
        }
        builder.append(objectOffset).append("{\n");

        Iterator<? extends Map.Entry<String, ?>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            var entry = it.next();

            writeFieldName(entry.getKey());
            writeFieldValue(entry.getValue(), !it.hasNext());
        }

        builder.append(objectOffset).append("}");

        return builder.toString();
    }
}
