package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
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
public class TrelloMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        //Given
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "Test", createTrelloListDtoForTest());
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(trelloBoardDto);
        //When
        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);
        int size = trelloBoardList.size();
        String id = trelloBoardList.get(0).getId();
        String name = trelloBoardList.get(0).getName();
        //Then
        assertEquals(1, size);
        assertEquals("1", id);
        assertEquals("Test", name);
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        TrelloBoard trelloBoard = new TrelloBoard("1", "Test", createTrelloListForTest());
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard);
        //When
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);
        int size = trelloBoardDtoList.size();
        String id = trelloBoardDtoList.get(0).getId();
        String name = trelloBoardDtoList.get(0).getName();
        //Then
        assertEquals(1, size);
        assertEquals("1", id);
        assertEquals("Test", name);
    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> trelloListDto = createTrelloListDtoForTest();
        //When
        List<TrelloList> trelloList = trelloMapper.mapToList(trelloListDto);
        int size = trelloList.size();
        //Then
        assertEquals(3, size);
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> trelloList = createTrelloListForTest();
        //When
        List<TrelloListDto> trelloListDto = trelloMapper.mapToListDto(trelloList);
        int size = trelloList.size();
        //Then
        assertEquals(3, size);
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test", "Test test", "test", "1");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        String name = trelloCard.getName();
        String desc = trelloCard.getDescription();
        String pos = trelloCard.getPos();
        String listId = trelloCard.getListId();
        //Then
        assertEquals("Test", name);
        assertEquals("Test test", desc);
        assertEquals("test", pos);
        assertEquals("1", listId);
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Test", "Test test", "test", "1");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        String name = trelloCardDto.getName();
        String desc = trelloCardDto.getDescription();
        String pos = trelloCardDto.getPos();
        String listId = trelloCardDto.getListId();
        //Then
        assertEquals("Test", name);
        assertEquals("Test test", desc);
        assertEquals("test", pos);
        assertEquals("1", listId);
    }

    public List<TrelloList> createTrelloListForTest() {
        TrelloList testList1 = new TrelloList("1", "Test List np. 1", false);
        TrelloList testList2 = new TrelloList("2", "Test List no. 2", true);
        TrelloList testList3 = new TrelloList("3", "Test List no. 3", false);

        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(testList1);
        trelloList.add(testList2);
        trelloList.add(testList3);
        return trelloList;
    }

    public List<TrelloListDto> createTrelloListDtoForTest() {
        TrelloListDto testListDto1 = new TrelloListDto("1", "Test List no. 1", false);
        TrelloListDto testListDto2 = new TrelloListDto("2", "Test List no. 2", true);
        TrelloListDto testListDto3 = new TrelloListDto("3", "Test List no. 3", false);

        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(testListDto1);
        trelloListDto.add(testListDto2);
        trelloListDto.add(testListDto3);
        return trelloListDto;
    }

}
