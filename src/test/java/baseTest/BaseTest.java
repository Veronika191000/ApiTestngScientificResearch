package baseTest;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import utils.StringUtils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static utils.StringUtils.getProperty;

public class BaseTest {

    protected String key;
    protected String token;
    protected RequestSpecification requestSpec;

    @BeforeClass(alwaysRun = true)
    public void setup() throws IOException {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.trello.com/")
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();

        key = getProperty("key");
        token = getProperty("token");
    }

    @Step("Request to Trello API to create {listName} List within {boardName} Board with id:{boardId}")
    public Response requestToCreateListWithinBoard(String listName, String boardId) {
        return given()
                .spec(requestSpec)
                .when()
                .body("")
                .post(StringUtils.LIST_POST_QUERY, listName, boardId, key, token)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to get List with id: {id}")
    public Response requestToGetList(String id) {
        return given()
                .spec(requestSpec)
                .urlEncodingEnabled(false)
                .when()
                .contentType(StringUtils.CONTENT_TYPE)
                .get(StringUtils.LIST_GET_DELETE_QUERY, id, key, token)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to get Cards in the List with id: {id}")
    public Response requestToGetCardsInTheList(String id) {
        return given()
                .spec(requestSpec)
                .urlEncodingEnabled(false)
                .when()
                .contentType(StringUtils.CONTENT_TYPE)
                .get(StringUtils.GET_CARDS_IN_LIST_QUERY, id, key, token)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to remove List with id: {boardId}")
    public void requestToRemoveList(String id) {
        given()
                .spec(requestSpec)
                .urlEncodingEnabled(false)
                .when()
                .delete(StringUtils.LIST_GET_DELETE_QUERY, id, key, token)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Step("Request to Trello API to update List with id: {id} ")
    public Response requestToUpdateList(String id, String listDetails) {
        return given()
                .spec(requestSpec)
                .urlEncodingEnabled(false)
                .when()
                .body(listDetails)
                .put(StringUtils.LIST_GET_DELETE_QUERY, id, key, token)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to create Board with name: {boardName}")
    public Response requestToCreateCard(String id, String cardName, int position, String description) throws IOException {
        return given()
                .spec(requestSpec)
                .urlEncodingEnabled(false)
                .when()
                .body("")
                .post(StringUtils.CARD_POST_QUERY, id, key, token, cardName, position, description)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to create Checklist within Card with name: {cardName}")
    public Response requestToCreateChecklist(String id, String checklistName) throws IOException {
        return given()
                .spec(requestSpec)
                .urlEncodingEnabled(false)
                .when()
                .body("")
                .post(StringUtils.POST_CHECKLIST_QUERY, id, key, token, checklistName)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to get checklist with id: {id}")
    public Response requestToGetChecklist(String id) {
        return given()
                .spec(requestSpec)
                .urlEncodingEnabled(false)
                .when()
                .contentType(StringUtils.CONTENT_TYPE)
                .get(StringUtils.GET_CHECKLIST_QUERY, id, key, token)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to get card with id: {id}")
    public Response requestToGetCard(String id) {
        return given()
                .spec(requestSpec)
                .urlEncodingEnabled(false)
                .when()
                .contentType(StringUtils.CONTENT_TYPE)
                .get(StringUtils.CARD_GET_DELETE_QUERY, id, key, token)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to update Card with id: {id}")
    public Response requestToUpdateCard(String id, String cardDetails) {
        return given()
                .spec(requestSpec)
                .when()
                .body(cardDetails)
                .put(StringUtils.CARD_GET_DELETE_QUERY, id, key, token)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to remove Card with id: {id}")
    public void requestToRemoveCard(String id) {
        given()
                .spec(requestSpec)
                .urlEncodingEnabled(false)
                .when()
                .delete(StringUtils.CARD_GET_DELETE_QUERY, id, key, token)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Step("Request to Trello API to create Board with name: {boardName}")
    public Response requestToCreateBoard(String boardName) throws IOException {
        return given()
                .spec(requestSpec)
                .when()
                .body("")
                .post(StringUtils.POST_QUERY, key, token, boardName)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to remove Board with id: {boardId}")
    public void requestToRemoveBoard(String boardId) {
        given()
                .spec(requestSpec)
                .when()
                .delete(StringUtils.GET_PUT_DELETE_QUERY, boardId, key, token)
                .then()
                .log().all()
                .statusCode(200);
    }

    @Step("Request to Trello API to update Board with id: {boardId} with a new name: {boardName}")
    public Response requestToUpdateBoard(String boardDetails, String boardId, String boardName) {
        return given()
                .spec(requestSpec)
                .when()
                .body(boardDetails)
                .put(StringUtils.GET_PUT_DELETE_QUERY, boardId, key, token)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to add Member to Board with id: {boardId}")
    public Response requestToAddMemberToTheBoard(String member, String boardId) {
        return given()
                .spec(requestSpec)
                .when()
                .body(member)
                .put(StringUtils.GET_PUT_DELETE_MEMBERS, boardId, key, token)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to get Board with id: {boardId}")
    public Response requestToGetBoard(String boardId) {
        return given()
                .spec(requestSpec)
                .when()
                .contentType(StringUtils.CONTENT_TYPE)
                .get(StringUtils.GET_PUT_DELETE_QUERY, boardId, key, token)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to get Board with incorrect id: {boardId}")
    public Response requestToGetBoardWithIncorrectBoardId(String boardId) {
        return given()
                .spec(requestSpec)
                .when()
                .contentType(StringUtils.CONTENT_TYPE)
                .get(StringUtils.GET_PUT_DELETE_QUERY, boardId, key, token)
                .then()
                .log().all()
                .statusCode(400)
                .extract().response();
    }

    @Step("Request to Trello API to get Board with id: {boardId}")
    public Response requestToSearchApi(String query) {
        return given()
                .spec(requestSpec)
                .when()
                .contentType(StringUtils.CONTENT_TYPE)
                .get(StringUtils.SEARCH_FOR_QUERY, query, key, token)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
    }

    @Step("Request to Trello API to create Card with incorrect token: {incorrectToken}")
    public Response requestToCreateCardWithIncorrectTokenValue(String incorrectToken, String id, String cardName, int position, String description) {
        return given()
                .spec(requestSpec)
                .urlEncodingEnabled(false)
                .when()
                .body("")
                .post(StringUtils.CARD_POST_QUERY, id, key, incorrectToken, cardName, position, description)
                .then()
                .log().all()
                .statusCode(401)
                .extract().response();
    }

    @Step("Request to Trello API to create {listName} List within {boardName} Board with id:{boardId}")
    public Response requestToCreateListWithIncorrectKeyValue(String incorrectKey, String listName, String boardId) {
        return given()
                .spec(requestSpec)
                .when()
                .body("")
                .post(StringUtils.LIST_POST_QUERY, listName, boardId, incorrectKey, token)
                .then()
                .log().all()
                .statusCode(401)
                .extract().response();
    }
}