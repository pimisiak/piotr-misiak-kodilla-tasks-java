package com.crud.tasks.trello.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.dto.TrelloAttachmentsByTypeDto;
import com.crud.tasks.trello.dto.TrelloBadgeDto;
import com.crud.tasks.trello.dto.TrelloBoardDto;
import com.crud.tasks.trello.dto.TrelloCardDto;
import com.crud.tasks.trello.dto.TrelloCreatedCardDto;
import com.crud.tasks.trello.dto.TrelloDto;
import com.crud.tasks.trello.dto.TrelloListDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService emailService;
    @Mock
    private AdminConfig adminConfig;
    @InjectMocks
    private TrelloService trelloService;

    @Test
    public void testFetchTrelloBoards() {
        //Given
        final List<TrelloBoardDto> trelloBoardDtos = prepareTestTrelloBoardDtos();
        when(trelloClient.getBoards()).thenReturn(trelloBoardDtos);
        //When
        final List<TrelloBoardDto> resultBoardDtos = trelloService.fetchTrelloBoards();
        //Then
        assertThat(resultBoardDtos).hasSize(trelloBoardDtos.size());
        for (int i = 0; i < resultBoardDtos.size(); i++) {
            Assertions.assertThat(resultBoardDtos.get(i)).isEqualToComparingFieldByFieldRecursively(trelloBoardDtos.get(i));
        }
    }

    @Test
    public void testCreateTrelloCard() {
        //Given
        final TrelloCardDto trelloCardDto = new TrelloCardDto("testCardName",
                "testCardDescription", "testCardPos", "testCardListId");
        final TrelloCreatedCardDto trelloCreatedCardDto = new TrelloCreatedCardDto("id", "testCardName",
                "testCardURL", new TrelloBadgeDto(0, new TrelloAttachmentsByTypeDto(new TrelloDto(0, 0))));
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(trelloCreatedCardDto);
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        //When
        final TrelloCreatedCardDto resultCreatedCardDto = trelloService.createTrelloCard(trelloCardDto);
        //Then
        verify(emailService, times(1)).send(any());
        assertThat(resultCreatedCardDto).isEqualToComparingFieldByFieldRecursively(trelloCreatedCardDto);
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
