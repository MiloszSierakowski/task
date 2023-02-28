package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    void mapToBoardsTest() {
        TrelloBoardDto dto = new TrelloBoardDto("1", "cos", new ArrayList<>());
        TrelloBoardDto dto1 = new TrelloBoardDto("2", "trol", new ArrayList<>());
        TrelloBoardDto dto2 = new TrelloBoardDto("3", "mol", new ArrayList<>());

        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(dto);
        trelloBoardDtoList.add(dto1);
        trelloBoardDtoList.add(dto2);

        List<TrelloBoard> trelloBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);

        assertEquals(3, trelloBoardList.size());
        assertEquals(dto.getId(), trelloBoardList.get(0).getId());
        assertEquals(dto.getName(), trelloBoardList.get(0).getName());
        assertEquals(dto.getLists().isEmpty(), trelloBoardList.get(0).getLists().isEmpty());
    }

    @Test
    void mapToBoardsDto() {

        TrelloBoard board = new TrelloBoard("1", "cos", new ArrayList<>());
        TrelloBoard board1 = new TrelloBoard("2", "trol", new ArrayList<>());
        TrelloBoard board2 = new TrelloBoard("3", "mol", new ArrayList<>());

        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(board);
        trelloBoardList.add(board1);
        trelloBoardList.add(board2);

        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);

        assertEquals(3, trelloBoardDtoList.size());
        assertEquals(board1.getId(), trelloBoardDtoList.get(1).getId());
        assertEquals(board1.getName(), trelloBoardDtoList.get(1).getName());
        assertEquals(board1.getLists().isEmpty(), trelloBoardDtoList.get(1).getLists().isEmpty());
    }

    @Test
    void mapToList() {
        TrelloListDto listDto = new TrelloListDto("1", "cos", true);
        TrelloListDto listDto1 = new TrelloListDto("2", "trol", true);
        TrelloListDto listDto2 = new TrelloListDto("3", "mol", true);

        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(listDto);
        trelloListDtos.add(listDto1);
        trelloListDtos.add(listDto2);

        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);

        assertEquals(3, trelloLists.size());
        assertEquals(listDto2.getId(), trelloLists.get(2).getId());
        assertEquals(listDto2.getName(), trelloLists.get(2).getName());
        assertEquals(listDto2.isClosed(), trelloLists.get(2).isClosed());
    }

    @Test
    void mapToListDto() {
        TrelloList list = new TrelloList("1", "cos", true);
        TrelloList list1 = new TrelloList("2", "trol", true);
        TrelloList list2 = new TrelloList("3", "mol", true);

        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(list);
        trelloLists.add(list1);
        trelloLists.add(list2);

        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        assertEquals(3, trelloListDtos.size());
        assertEquals(list.getId(), trelloListDtos.get(0).getId());
        assertEquals(list.getName(), trelloListDtos.get(0).getName());
        assertEquals(list.isClosed(), trelloListDtos.get(0).isClosed());
    }

    @Test
    void mapToCardDto() {
        TrelloCard trelloCard = new TrelloCard("karta", "opis karty", "Trele morele", "1");

        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        assertEquals(trelloCard.getName(), trelloCardDto.getName());
        assertEquals(trelloCard.getDescription(), trelloCardDto.getDescription());
        assertEquals(trelloCard.getPos(), trelloCardDto.getPos());
        assertEquals(trelloCard.getListId(), trelloCardDto.getListId());
    }

    @Test
    void mapToCard() {
        TrelloCardDto trelloCardDto = new TrelloCardDto("karta", "opis karty", "Trele morele", "1");

        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        assertEquals(trelloCardDto.getName(), trelloCard.getName());
        assertEquals(trelloCardDto.getDescription(), trelloCard.getDescription());
        assertEquals(trelloCardDto.getPos(), trelloCard.getPos());
        assertEquals(trelloCardDto.getListId(), trelloCard.getListId());
    }
}