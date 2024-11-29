import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import testClasses.*;
import utils.serializers.Serializer;
import utils.serializers.factories.NoReflectionSerializerFactory;
import utils.serializers.factories.SerializerFactory;

import java.io.StringWriter;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SerializerFactoryTest {
    private final SerializerFactory sf = new NoReflectionSerializerFactory();
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().serializeNulls().create();

    @Test
    public void testSimple() {
        SimpleStructures simpleStructures = new SimpleStructures();

        Writer writer = new StringWriter();
        gson.toJson(simpleStructures, writer);

        String expectedJSON = writer.toString();
        Serializer s = sf.getSerializer(simpleStructures.getClass());
        String actualJSON = s.serialize(simpleStructures);

        assertEquals(expectedJSON, actualJSON);
    }

    @Test
    public void testArrays() {
        Arrays arrays = new Arrays();

        Writer writer = new StringWriter();
        gson.toJson(arrays, writer);

        String expectedJSON = writer.toString();
        Serializer s = sf.getSerializer(arrays.getClass());
        String actualJSON = s.serialize(arrays);

        assertEquals(expectedJSON, actualJSON);
    }

    @Test
    public void testCollections() {
        Collections colls = new Collections();
        colls.init();

        Writer writer = new StringWriter();
        gson.toJson(colls, writer);

        String expectedJSON = writer.toString();
        Serializer s = sf.getSerializer(colls.getClass());
        String actualJSON = s.serialize(colls);

        assertEquals(expectedJSON, actualJSON);
    }

    @Test
    public void testNulls() {
        Nulls nulls = new Nulls();

        Writer writer = new StringWriter();
        gson.toJson(nulls, writer);

        String expectedJSON = writer.toString();
        Serializer s = sf.getSerializer(nulls.getClass());
        String actualJSON = s.serialize(nulls);

        assertEquals(expectedJSON, actualJSON);
    }

    @Test
    public void testEmptyStructure() {
        EmptyStructure empty = new EmptyStructure();

        Writer writer = new StringWriter();
        gson.toJson(empty, writer);

        String expectedJSON = writer.toString();
        Serializer s = sf.getSerializer(empty.getClass());
        String actualJSON = s.serialize(empty);

        assertEquals(expectedJSON, actualJSON);
    }
}