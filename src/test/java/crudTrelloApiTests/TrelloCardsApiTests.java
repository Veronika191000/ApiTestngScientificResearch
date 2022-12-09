package crudTrelloApiTests;

import baseTest.BaseTest;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.board.BoardDetails;
import models.card.CardDetails;
import models.list.ListDetails;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.StringUtils;

import java.io.IOException;

import static utils.StringUtils.getObjectFromJson;

public class TrelloCardsApiTests extends BaseTest {
    String boardName;
    BoardDetails expectedBoardDetails;
    String listName = "List of Tasks";
    String cardName = "New_Card_With_Task";
    String description = "Scientific_Research";
    int position = 1; //position in the list

    @BeforeTest(alwaysRun = true)
    public void BoardCreation() throws IOException {
        boardName = StringUtils.createBoardName();
    }

    @Test
    @Description("Verify card can be successfully created in the list")
    public void verifyCardCreation() throws IOException {
        //create board
        expectedBoardDetails = getObjectFromJson(requestToCreateBoard(boardName).asString(), BoardDetails.class);

        //create list
        String boardId = expectedBoardDetails.getId();
        ListDetails actualBoardList = getObjectFromJson(requestToCreateListWithinBoard(listName, boardId).asString(), ListDetails.class);

        //create card
        Response createdCard = requestToCreateCard(actualBoardList.getId(), cardName, position, description);
        CardDetails actualCardDetails = getObjectFromJson(createdCard.asString(), CardDetails.class);

        Assert.assertNotNull(actualCardDetails.getName());
        Assert.assertNotNull(actualCardDetails.getId());
        Assert.assertNotNull(actualCardDetails.getName());
        Assert.assertNotNull(actualCardDetails.getDesc());
        Assert.assertEquals(cardName, actualCardDetails.getName());
        Assert.assertEquals(position, actualCardDetails.getPos());
        Assert.assertEquals(description, actualCardDetails.getDesc());

        requestToRemoveBoard(expectedBoardDetails.getId());
    }

    @Test
    @Description("Verify card can be successfully received in the list within board")
    public void verifyGettingListRequest() throws IOException {
        //create board
        expectedBoardDetails = getObjectFromJson(requestToCreateBoard(boardName).asString(), BoardDetails.class);

        //create list
        ListDetails actualBoardList = getObjectFromJson(requestToCreateListWithinBoard(listName, expectedBoardDetails.getId()).asString(), ListDetails.class);

        //create card
        CardDetails expectedCardDetails = getObjectFromJson(requestToCreateCard(actualBoardList.getId(), cardName, position, description).asString(), CardDetails.class);

        //get card
        Response cardResponse = requestToGetCard(expectedCardDetails.getId());
        CardDetails actualCardDetails = getObjectFromJson(cardResponse.asString(), CardDetails.class);
        Assert.assertNotNull(actualCardDetails.getId());
        Assert.assertNotNull(actualCardDetails.getName());
        Assert.assertNotNull(actualCardDetails.getDesc());
        Assert.assertEquals(expectedCardDetails.getId(), actualCardDetails.getId());
        Assert.assertEquals(expectedCardDetails.getName(), actualCardDetails.getName());
        Assert.assertEquals(expectedCardDetails.getDesc(), actualCardDetails.getDesc());

        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }

    @Test
    @Description("Verify card can be successfully updated  in the list within board")
    public void verifyUpdatingList() throws IOException {
        //create board
        expectedBoardDetails = getObjectFromJson(requestToCreateBoard(boardName).asString(), BoardDetails.class);

        //create list
        ListDetails actualBoardList = getObjectFromJson(requestToCreateListWithinBoard(listName, expectedBoardDetails.getId()).asString(), ListDetails.class);

        //create card
        CardDetails expectedCardDetails = getObjectFromJson(requestToCreateCard(actualBoardList.getId(), cardName, position, description).asString(), CardDetails.class);

        //update card
        expectedCardDetails.setName("Updated_Card_Name");
        expectedCardDetails.setDesc("Updated_Card_Description");
        Response updatedCardDetails = requestToUpdateCard(expectedCardDetails.getId(), StringUtils.convertObjectToJsonString(expectedCardDetails));
        CardDetails actualCardDetails = getObjectFromJson(updatedCardDetails.asString(), CardDetails.class);

        //validation
        Assert.assertNotNull(actualCardDetails.getId());
        Assert.assertNotNull(actualCardDetails.getName());
        Assert.assertNotNull(actualCardDetails.getDesc());
        Assert.assertEquals(expectedCardDetails.getName(), "Updated_Card_Name");
        Assert.assertEquals(expectedCardDetails.getDesc(), "Updated_Card_Description");

        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }

    @Test
    @Description("Verify list can be successfully removed on the board")
    public void verifyRemovingList() throws IOException {
        expectedBoardDetails = getObjectFromJson(requestToCreateBoard(boardName).asString(), BoardDetails.class);

        //create list
        ListDetails actualBoardList = getObjectFromJson(requestToCreateListWithinBoard(listName, expectedBoardDetails.getId()).asString(), ListDetails.class);

        //create card
        CardDetails expectedCardDetails = getObjectFromJson(requestToCreateCard(actualBoardList.getId(), cardName, position, description).asString(), CardDetails.class);

        //remove list
        requestToRemoveCard(expectedCardDetails.getId());

        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }
}
