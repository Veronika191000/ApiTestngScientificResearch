package positiveTests;

import baseTest.BaseTest;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.board.BoardDetails;
import models.card.CardDetails;
import models.checklist.Checklist;
import models.list.ListDetails;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static utils.StringUtils.getObjectFromJson;

public class TrelloChecklistApiTest extends BaseTest {
    String boardName;
    BoardDetails expectedBoardDetails;
    String listName = "List of Tasks";
    String cardName = "New_Card_With_Task";
    String description = "Scientific_Research";
    String checklistName = "Things_to_do";
    int position = 1; //position in the list

    @BeforeTest(alwaysRun = true)
    public void BoardCreation() throws IOException {
        boardName = StringUtils.createBoardName();
    }

    @Test
    @Description("Verify checklist can be successfully created within card")
    public void verifyChecklistCreation() throws IOException {
        //create board
        expectedBoardDetails = getObjectFromJson(requestToCreateBoard(boardName).asString(), BoardDetails.class);
        //create list
        ListDetails actualBoardList = getObjectFromJson(requestToCreateListWithinBoard(listName, expectedBoardDetails.getId()).asString(), ListDetails.class);
        //create card
        CardDetails expectedCardDetails = getObjectFromJson(requestToCreateCard(actualBoardList.getId(), cardName, position, description).asString(), CardDetails.class);
        //create checklist
        Response checklistResponse = requestToCreateChecklist(expectedCardDetails.getId(), checklistName);
        Checklist createdChecklist = getObjectFromJson(checklistResponse.asString(), Checklist.class);
        //validation
        Assert.assertNotNull(createdChecklist.getId());
        Assert.assertNotNull(createdChecklist.getName());
        Assert.assertNotNull(createdChecklist.getIdBoard());
        Assert.assertNotNull(createdChecklist.getIdCard());
        Assert.assertEquals(checklistName, createdChecklist.getName());
        Assert.assertEquals(expectedBoardDetails.getId(), createdChecklist.getIdBoard());
        Assert.assertEquals(expectedCardDetails.getId(), createdChecklist.getIdCard());
        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }

    @Test
    @Description("Verify checklist can be successfully received within card")
    public void verifyGettingChecklistRequest() throws IOException {
        //create board
        expectedBoardDetails = getObjectFromJson(requestToCreateBoard(boardName).asString(), BoardDetails.class);
        //create list
        ListDetails actualBoardList = getObjectFromJson(requestToCreateListWithinBoard(listName, expectedBoardDetails.getId()).asString(), ListDetails.class);
        //create card
        CardDetails expectedCardDetails = getObjectFromJson(requestToCreateCard(actualBoardList.getId(), cardName, position, description).asString(), CardDetails.class);
        //create checklist
        Response checklistResponse = requestToCreateChecklist(expectedCardDetails.getId(), checklistName);
        Checklist expectedCreatedChecklist = getObjectFromJson(checklistResponse.asString(), Checklist.class);

        Response recievedChecklistResponse = requestToGetChecklist(expectedCardDetails.getId());
        List<Checklist> actualCreatedChecklist = Arrays.asList(getObjectFromJson(recievedChecklistResponse.asString(), Checklist[].class));

        //validation
        Assert.assertNotNull(actualCreatedChecklist.get(0).getId());
        Assert.assertNotNull(actualCreatedChecklist.get(0).getName());
        Assert.assertNotNull(actualCreatedChecklist.get(0).getIdBoard());
        Assert.assertNotNull(actualCreatedChecklist.get(0).getIdCard());
        Assert.assertEquals(expectedCreatedChecklist.getName(), actualCreatedChecklist.get(0).getName());
        Assert.assertEquals(expectedCreatedChecklist.getId(), actualCreatedChecklist.get(0).getId());
        Assert.assertEquals(expectedCreatedChecklist.getIdBoard(), actualCreatedChecklist.get(0).getIdBoard());
        Assert.assertEquals(expectedCreatedChecklist.getIdCard(), actualCreatedChecklist.get(0).getIdCard());

        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }
}
