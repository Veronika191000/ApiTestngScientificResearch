package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonConverter {

    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // defaultObjectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        //     ObjectMapper mapper = new ObjectMapper();
        //   defaultObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //   defaultObjectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        return defaultObjectMapper;
    }

    public static <A> A convertJsonNodeToObject(JsonNode node, Class<A> clazz) {
        try {
            return objectMapper.treeToValue(node, clazz);
        } catch (JsonProcessingException e) {
            throw new AssertionError(e);
        }
    }

    public static JsonNode convertJsonStringToNode(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to convert JSON string to JsonNode object");
        }
    }
}
