package crudTrelloApiTests;

import baseTest.BaseTest;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.board.BoardDetails;
import models.search.SearchData;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.StringUtils;

import java.io.IOException;

import static utils.StringUtils.getObjectFromJson;

public class TrelloBoardApiTests extends BaseTest {

    @Test
    @Description("Verify board can be successfully created")
    public void verifyBoardCreation() throws IOException {
        String boardName = StringUtils.createBoardName();
        Response createdBoard = requestToCreateBoard(boardName);
        BoardDetails expectedBoardDetails = getObjectFromJson(createdBoard.asString(), BoardDetails.class);

        Assert.assertNotNull(expectedBoardDetails.getName());
        Assert.assertNotNull(expectedBoardDetails.getId());
        Assert.assertEquals(expectedBoardDetails.getName(), boardName);

        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }

    @Test
    @Description("Verify board can be successfully received")
    public void verifyGettingBoardRequest() throws IOException {
        //create board
        String boardName = StringUtils.createBoardName();
        Response createdBoard = requestToCreateBoard(boardName);
        BoardDetails expectedBoardDetails = getObjectFromJson(createdBoard.asString(), BoardDetails.class);

        //get board
        Response boardResponse = requestToGetBoard(expectedBoardDetails.getId());
        BoardDetails actualBoardDetails = getObjectFromJson(createdBoard.asString(), BoardDetails.class);

        Assert.assertNotNull(actualBoardDetails.getName());
        Assert.assertNotNull(actualBoardDetails.getId());
        Assert.assertEquals(expectedBoardDetails.getName(), actualBoardDetails.getName());
        Assert.assertEquals(expectedBoardDetails.getId(), actualBoardDetails.getId());

        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }

    @Test
    @Description("Verify board can be successfully updated")
    public void verifyUpdatingBoard() throws IOException {
        //create board
        String boardName = StringUtils.createBoardName();
        Response createdBoard = requestToCreateBoard(boardName);
        BoardDetails expectedBoardDetails = getObjectFromJson(createdBoard.asString(), BoardDetails.class);

        //update board
        String updatedBoardName = StringUtils.createBoardName();
        expectedBoardDetails.setName(boardName);
        Response updatedBoardDetails = requestToUpdateBoard(StringUtils.convertObjectToJsonString(expectedBoardDetails), expectedBoardDetails.getId(), updatedBoardName);
        BoardDetails actualBoardDetails = getObjectFromJson(updatedBoardDetails.asString(), BoardDetails.class);

        Assert.assertNotNull(actualBoardDetails.getName());
        Assert.assertNotNull(actualBoardDetails.getId());
        Assert.assertEquals(expectedBoardDetails.getName(), actualBoardDetails.getName());

        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }

    @Test
    @Description("Verify board can be successfully removed")
    public void verifyRemovingBoard() throws IOException {
        //create board
        String boardName = StringUtils.createBoardName();
        Response createdBoard = requestToCreateBoard(boardName);
        BoardDetails expectedBoardDetails = getObjectFromJson(createdBoard.asString(), BoardDetails.class);

        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }

    @Test
    @Description("Verify board can be successfully found in list of boards")
    public void verifySearchFunction() throws IOException {
        //create board
        Response createdBoard = requestToCreateBoard(StringUtils.createBoardName());
        BoardDetails expectedBoardDetails = getObjectFromJson(createdBoard.asString(), BoardDetails.class);
        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }

    @Test
    @Description("Verify board can be successfully found in list of boards by board name")
    public void verifyBoardSearchByName() throws IOException {
        //create board
        BoardDetails expectedBoardDetails = getObjectFromJson(requestToCreateBoard(StringUtils.createBoardName()).asString(), BoardDetails.class);
        //search for board
        SearchData actualSearchData = getObjectFromJson(requestToSearchApi(expectedBoardDetails.getName()).asString(), SearchData.class);
        //validation
        Assert.assertNotNull(actualSearchData.getBoards().get(0).getId());
        Assert.assertNotNull(actualSearchData.getBoards().get(0).getName());
        Assert.assertNotNull(actualSearchData.getBoards().get(0).getIdOrganization());
        Assert.assertEquals(expectedBoardDetails.getId(), actualSearchData.getBoards().get(0).getId());
        Assert.assertEquals(expectedBoardDetails.getName(), actualSearchData.getBoards().get(0).getName());
        Assert.assertEquals(expectedBoardDetails.getIdOrganization(), actualSearchData.getBoards().get(0).getIdOrganization());

        //delete board
        requestToRemoveBoard(expectedBoardDetails.getId());
    }
}
