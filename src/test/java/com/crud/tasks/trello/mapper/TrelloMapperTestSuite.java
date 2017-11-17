package com.crud.tasks.trello.mapper;

import com.crud.tasks.trello.domain.TrelloBoard;
import com.crud.tasks.trello.domain.TrelloCard;
import com.crud.tasks.trello.domain.TrelloList;
import com.crud.tasks.trello.dto.TrelloBoardDto;
import com.crud.tasks.trello.dto.TrelloCardDto;
import com.crud.tasks.trello.dto.TrelloListDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {
    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoard() {
        //Given
        final TrelloBoardDto trelloBoardDto = prepareTestTrelloBoardDto();
        //When
        final TrelloBoard resultBoard = trelloMapper.mapToBoard(trelloBoardDto);
        //Then
        assertThat(resultBoard).isEqualToComparingFieldByFieldRecursively(trelloBoardDto);
    }

    @Test
    public void testMapToBoardDto() {
        //Given
        final TrelloBoard trelloBoard = prepareTestTrelloBoard();
        //When
        final TrelloBoardDto resultBoardDto = trelloMapper.mapToBoardDto(trelloBoard);
        //Then
        assertThat(resultBoardDto).isEqualToComparingFieldByFieldRecursively(trelloBoard);
    }

    @Test
    public void testMapToBoards() {
        //Given
        final List<TrelloBoard> trelloBoards = prepareTestTrelloBoards();
        //When
        final List<TrelloBoardDto> resultBoardDtos = trelloMapper.mapToBoardDtos(trelloBoards);
        //Then
        assertThat(resultBoardDtos).hasSize(trelloBoards.size());
        for (int i = 0; i < resultBoardDtos.size(); i++) {
            assertThat(resultBoardDtos.get(i)).isEqualToComparingFieldByFieldRecursively(trelloBoards.get(i));
        }
    }

    @Test
    public void testMapToBoardDtos() {
        //Given
        final List<TrelloBoardDto> trelloBoardDtos = prepareTestTrelloBoardDtos();
        //When
        final List<TrelloBoard> resultBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        assertThat(resultBoards).hasSize(trelloBoardDtos.size());
        for (int i = 0; i < resultBoards.size(); i++) {
            assertThat(resultBoards.get(i)).isEqualToComparingFieldByFieldRecursively(trelloBoardDtos.get(i));
        }
    }

    @Test
    public void testMapToList() {
        //Given
        final TrelloList trelloList = prepareTestTrelloList();
        //When
        final TrelloListDto resultListDto = trelloMapper.mapToListDto(trelloList);
        //Then
        assertThat(resultListDto).isEqualToComparingFieldByField(trelloList);
    }

    @Test
    public void testMapToListDto() {
        //Given
        final TrelloListDto trelloListDto = prepareTestTrelloListDto();
        //When
        final TrelloList resultList = trelloMapper.mapToList(trelloListDto);
        //Then
        assertThat(resultList).isEqualToComparingFieldByField(trelloListDto);
    }

    @Test
    public void testMapToLists() {
        //Given
        final List<TrelloListDto> trelloListDtos = prepareTestTrelloListDtos();
        //When
        final List<TrelloList> resultLists = trelloMapper.mapToLists(trelloListDtos);
        //Then
        assertThat(resultLists).hasSize(trelloListDtos.size());
        for (int i = 0; i < resultLists.size(); i++) {
            assertThat(resultLists.get(i)).isEqualToComparingFieldByField(trelloListDtos.get(i));
        }
    }

    @Test
    public void testMapToListDtos() {
        //Given
        final List<TrelloList> trelloLists = prepareTestTrelloLists();
        //When
        final List<TrelloListDto> resultListDtos = trelloMapper.mapToListDtos(trelloLists);
        //Then
        assertThat(resultListDtos).hasSize(trelloLists.size());
        for (int i = 0; i < resultListDtos.size(); i++) {
            assertThat(resultListDtos.get(i)).isEqualToComparingFieldByField(trelloLists.get(i));
        }
    }

    @Test
    public void testMapToCard() {
        //Given
        final TrelloCardDto trelloCardDto = new TrelloCardDto("testCardName",
                "testCardDescription", "testCardPos", "testCardListId");
        //When
        final TrelloCard resultCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertThat(resultCard).isEqualToComparingFieldByField(trelloCardDto);
    }

    @Test
    public void testMapToCardDto() {
        //Given
        final TrelloCard trelloCard = new TrelloCard("testCardName",
                "testCardDescription", "testCardPos", "testCardListId");
        //When
        final TrelloCardDto resultCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertThat(resultCardDto).isEqualToComparingFieldByField(trelloCard);
    }

    private List<TrelloBoard> prepareTestTrelloBoards() {
        final List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(prepareTestTrelloBoard());
        return trelloBoards;
    }

    private TrelloBoard prepareTestTrelloBoard() {
        return new TrelloBoard("testBoardId", "testBoardName", prepareTestTrelloLists());
    }

    private List<TrelloList> prepareTestTrelloLists() {
        final List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(prepareTestTrelloList());
        return trelloLists;
    }

    private TrelloList prepareTestTrelloList() {
        return new TrelloList("testListId", "testListName", false);
    }

    private List<TrelloBoardDto> prepareTestTrelloBoardDtos() {
        final List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(prepareTestTrelloBoardDto());
        return trelloBoardDtos;
    }

    private TrelloBoardDto prepareTestTrelloBoardDto() {
        return new TrelloBoardDto("testBoardDtoId", "testBoardDtoName", prepareTestTrelloListDtos());
    }

    private List<TrelloListDto> prepareTestTrelloListDtos() {
        final List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(prepareTestTrelloListDto());
        return trelloListDtos;
    }

    private TrelloListDto prepareTestTrelloListDto() {
        return new TrelloListDto("testListDtoId", "testListDtoName", false);
    }
}
