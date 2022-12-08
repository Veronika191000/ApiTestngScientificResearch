package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import static utils.JsonConverter.convertJsonNodeToObject;
import static utils.JsonConverter.convertJsonStringToNode;

public class StringUtils {

    public static final String POST_QUERY = "1/boards/?key={KEY}&token={TOKEN}&name={boardName}";
    public static final String GET_PUT_DELETE_QUERY = "1/boards/{boardId}?key={KEY}&token={TOKEN}";
    public static final String LIST_POST_QUERY = "1/lists?name={listName}&idBoard={boardId}&key={KEY}&token={TOKEN}";
    public static final String LIST_GET_DELETE_QUERY = "1/lists/{id}?key={KEY}&token={TOKEN}";
    public static final String GET_CARDS_IN_LIST_QUERY = "1/lists/{id}/cards?key={KEY}&token={TOKEN}";
    public static final String CARD_POST_QUERY = "1/cards?idList={id}&key={KEY}&token={TOKEN}&name={cardName}&pos={position}&desc={description}";
    public static final String POST_CHECKLIST_QUERY = "1/cards/{id}/checklists?key={KEY}&token={TOKEN}&name={name}";
    public static final String GET_CHECKLIST_QUERY = "1/cards/{id}/checklists?key={KEY}&token={TOKEN}";
    public static final String CARD_GET_DELETE_QUERY = "1/cards/{id}?key={KEY}&token={TOKEN}";
    public static final String SEARCH_FOR_QUERY = "1/search?query={query}&key={KEY}&token={TOKEN}";
    public static final String GET_PUT_DELETE_MEMBERS = "/boards/{id}/members&key={KEY}&token={TOKEN}";

    public static final String INCORRECT_KEY_VALUE = "e6d76b07153a275edf7831fc3f4ee209e3heg63g6d36";
    public static final String INCORRECT_TOKEN_VALUE = "db7cefbca238d580649b279deb0ce5405a819938aaa8ce9810d729f5356d3311yeb463hdy6d";
    public static final String INCORRECT_BOARD_ID = "cjuiwh74yr34h3724ry237g6t3e362ge";
    public static final String CONTENT_TYPE = "application/json";


    public static String createBoardName() {
        return "New Board " + new Random().nextInt(1000);
    }

    public static String convertObjectToJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static <T> T getObjectFromJson(String jsonString, Class<T> clazz) {
        JsonNode jsonNode = convertJsonStringToNode(jsonString);
        return convertJsonNodeToObject(jsonNode, clazz);
    }

    public static String getProperty(String value) throws IOException {
        InputStream input = StringUtils.class.getClassLoader().getResourceAsStream("credential.properties");
        Properties properties = new Properties();
        if (input == null) {
            System.out.println("Sorry, unable to find " + "credential.properties");
        }
        properties.load(input);
        return properties.getProperty(value);
    }
}
