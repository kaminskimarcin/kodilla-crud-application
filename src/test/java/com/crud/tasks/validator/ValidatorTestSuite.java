package com.crud.tasks.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidatorTestSuite {

    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    public void testValidateTrelloBoards() {
        //Given
        TrelloList testListOne = new TrelloList("1", "test", false);
        TrelloList testListTwo = new TrelloList("2", "TestTwo", false);
        TrelloList testListThree = new TrelloList("3", "TestThree", true);

        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(testListOne);
        trelloList.add(testListTwo);
        trelloList.add(testListThree);

        TrelloBoard trelloBoard = new TrelloBoard("1", "test", trelloList);

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);
        //When
        List<TrelloBoard> filteredTrelloBoard = trelloValidator.validateTrelloBoards(trelloBoards);
        //Then
        assertEquals(0, filteredTrelloBoard.size());
    }
}
