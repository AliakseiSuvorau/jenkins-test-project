package utils.serializers.factories;

import utils.serializers.Serializer;
import utils.serializers.ReflectionSerializer;

public class ReflectionSerializerFactory<T> implements SerializerFactory<T> {

    @Override
    public Serializer<T> getSerializer(Class<?> clazz) {
        return new ReflectionSerializer<>();
    }
}
