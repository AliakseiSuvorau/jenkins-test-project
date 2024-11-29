package utils.serializers.factories;

import utils.serializers.Serializer;

public interface SerializerFactory<T> {
    Serializer<T> getSerializer(Class<?> clazz);
}
