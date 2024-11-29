package utils.serializers;

import utils.mappers.JSONMapper;
import utils.mappers.ObjectMapper;

public class ReflectionSerializer <T> implements Serializer<T> {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String serialize(final T object) {
        var map = objectMapper.toMap(object);

        JSONMapper jsonMapper = new JSONMapper();
        return jsonMapper.toJSON( map);
    }
}
