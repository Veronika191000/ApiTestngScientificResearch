package positiveTests;

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
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.StringUtils.getObjectFromJson;

public class TrelloListsApiTests extends BaseTest {

    String boardName;
    BoardDetails expectedBoardDetails;

    String listName = "List of Tasks";

    @BeforeTest(alwaysRun = true)
    public void BoardCreation() {
        boardName = StringUtils.createBoardName();
    }

    @Test
    @Description("Verify list can be successfully created on the board")
    public void verifyListCreation() throws IOException {
        //create board
        expectedBoardDetails = getObjectFromJson(requestToCreateBoard(boardName).asString(), BoardDetails.class);
        //create list
        Response createdList = requestToCreateListWithinBoard(listName, expectedBoardDetails.getId());
        ListDetails actualBoardList = getObjectFromJson(createdList.asString(), ListDetails.class);

        Assert.assertNotNull(actualBoardList.getName());
        Assert.assertNotNull(actualBoardList.getId());
        Assert.assertEquals(listName, actualBoardList.getName());
        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }

    @Test
    @Description("Verify list can be successfully received on the board")
    public void verifyGettingListRequest() throws IOException {
        //create board
        expectedBoardDetails = getObjectFromJson(requestToCreateBoard(boardName).asString(), BoardDetails.class);
        //create list
        ListDetails boardList = getObjectFromJson(requestToCreateListWithinBoard(listName, expectedBoardDetails.getId()).asString(), ListDetails.class);
        //get list
        Response listResponse = requestToGetList(boardList.getId());
        ListDetails actualBoardList = getObjectFromJson(listResponse.asString(), ListDetails.class);

        Assert.assertNotNull(actualBoardList.getName());
        Assert.assertNotNull(actualBoardList.getId());
        Assert.assertNotNull(actualBoardList.getIdBoard());
        Assert.assertEquals(listName, actualBoardList.getName());
        Assert.assertEquals(expectedBoardDetails.getId(), actualBoardList.getIdBoard());

        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }

    @Test
    @Description("Verify list can be successfully updated on the board")
    public void verifyUpdatingList() throws IOException {
        //create board
        expectedBoardDetails = getObjectFromJson(requestToCreateBoard(boardName).asString(), BoardDetails.class);
        //create list
        Response createdList = requestToCreateListWithinBoard(listName, expectedBoardDetails.getId());
        ListDetails boardList = getObjectFromJson(createdList.asString(), ListDetails.class);
        //update list
        String listId = boardList.getId();
        String expectedListName = "Updated List Of The Tasks";
        boardList.setName(expectedListName);
        Response updatedListDetails = requestToUpdateList(listId, StringUtils.convertObjectToJsonString(boardList));
        ListDetails actualListDetails = getObjectFromJson(updatedListDetails.asString(), ListDetails.class);
        //validation
        Assert.assertNotNull(actualListDetails.getName());
        Assert.assertNotNull(actualListDetails.getId());
        Assert.assertEquals(expectedListName, actualListDetails.getName());

        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }

    @Test
    @Description("Verify list can be successfully received on the board")
    public void verifyGettingCardsInTheListRequest() throws IOException {
        //create board
        expectedBoardDetails = getObjectFromJson(requestToCreateBoard(boardName).asString(), BoardDetails.class);
        //create list
        ListDetails boardList = getObjectFromJson(requestToCreateListWithinBoard(listName, expectedBoardDetails.getId()).asString(), ListDetails.class);
        //create cards in the list
        CardDetails firstCard = getObjectFromJson(requestToCreateCard(boardList.getId(), "First_Task", 1, "First_Task_To_Do").asString(), CardDetails.class);
        CardDetails secondCard = getObjectFromJson(requestToCreateCard(boardList.getId(), "Second_Task", 1, "Second_Task_To_Do").asString(), CardDetails.class);
        CardDetails thirdCard = getObjectFromJson(requestToCreateCard(boardList.getId(), "Third_Task", 1, "Third_Task_To_Do").asString(), CardDetails.class);
        List<CardDetails> expectedCards = Arrays.asList(firstCard, secondCard, thirdCard);
        //get cards in the list
        List<CardDetails> actualCards = Arrays.asList(getObjectFromJson(requestToGetCardsInTheList(boardList.getId()).asString(), CardDetails[].class));
        //validation
        Assert.assertEquals(actualCards.size(), actualCards.size());
        assertThat(expectedCards).containsExactlyInAnyOrderElementsOf(actualCards);
        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }
}
