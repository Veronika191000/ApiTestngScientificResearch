package smokeTests;

import baseTest.BaseTest;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.StringUtils.*;

public class SmokeTests extends BaseTest {

    @Test
    @Description("Verify getting board with incorrect board id")
    public void verifyGetBoardWithIncorrectBoardId() {
        Response response = requestToGetBoardWithIncorrectBoardId(INCORRECT_BOARD_ID);
        Assert.assertEquals(response.asString(), "invalid id", "Invalid id => Check id value");
    }

    @Test
    @Description("Verify list creation with incorrect key")
    public void verifyListCreationWithIncorrectKey() {
        Response response = requestToCreateListWithIncorrectKeyValue(INCORRECT_KEY_VALUE, "New list", "63891a5dbbfdac034c75d465");
        Assert.assertEquals(response.asString(), "invalid key", "Invalid response.");
    }

    @Test
    @Description("Verify card creation with incorrect key")
    public void verifyCardCreationWithIncorrectToken() {
        Response response = requestToCreateCardWithIncorrectTokenValue(INCORRECT_TOKEN_VALUE, "638b88bcebd4c0058803fdc6", "New_card_name", 1, "Card_description");
        Assert.assertEquals(response.asString(), "invalid token", "Invalid response.");
    }
}
