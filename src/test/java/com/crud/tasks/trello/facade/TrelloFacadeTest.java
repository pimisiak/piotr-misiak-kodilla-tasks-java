package com.crud.tasks.trello.facade;

import com.crud.tasks.trello.domain.TrelloBoard;
import com.crud.tasks.trello.domain.TrelloList;
import com.crud.tasks.trello.dto.TrelloBoardDto;
import com.crud.tasks.trello.dto.TrelloListDto;
import com.crud.tasks.trello.mapper.TrelloMapper;
import com.crud.tasks.trello.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {
    @InjectMocks
    private TrelloFacade trelloFacade;
    @Mock
    private TrelloService trelloService;
    @Mock
    private TrelloValidator trelloValidator;
    @Mock
    private TrelloMapper trelloMapper;

    @Test
    public void shouldFetchEmptyList() {
        //Given
        final List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "test_list", false));

        final List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("1", "test", trelloListDtos));

        final List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "test_list", false));

        final List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardDtos);
        when(trelloMapper.mapToBoards(trelloBoardDtos)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardDtos(anyList())).thenReturn(new ArrayList<>());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(new ArrayList<>());

        //When
        final List<TrelloBoardDto> resultTrelloBoardDtos = trelloFacade.fetchTrelloBoards();

        //Then
        assertNotNull(resultTrelloBoardDtos);
        assertEquals(0, resultTrelloBoardDtos.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        final List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "my_list", false));

        final List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("1", "my_task", trelloListDtos));

        final List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "my_list", false));

        final List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "my_task", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardDtos);
        when(trelloMapper.mapToBoards(trelloBoardDtos)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardDtos(anyList())).thenReturn(trelloBoardDtos);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        //When
        final List<TrelloBoardDto> resultTrelloBoardDtos = trelloFacade.fetchTrelloBoards();

        //Then
        assertNotNull(resultTrelloBoardDtos);
        assertEquals(1, resultTrelloBoardDtos.size());

        resultTrelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("my_task", trelloBoardDto.getName());
            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }
}
