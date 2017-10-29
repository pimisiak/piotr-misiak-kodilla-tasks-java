package com.crud.tasks.trello.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {
    @InjectMocks
    private TrelloClient trelloClient;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void initMocksBeforeTest() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloAppToken()).thenReturn("test");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        // Given
        final TrelloBoardDto[] trelloBoards = {new TrelloBoardDto("test_id", "test_board", Collections.emptyList())};
        final URI uri = new URI(String.format("http://test.com/members/%s/boards?key=test&token=test&fields=name,id&lists=all",
                trelloConfig.getTrelloUserName()));
        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);
        // When
        final List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getBoards();
        // Then
        assertThat(fetchedTrelloBoards).hasSize(1);
        assertThat(fetchedTrelloBoards.get(0))
                .extracting(TrelloBoardDto::getId, TrelloBoardDto::getName, TrelloBoardDto::getLists)
                .containsExactly("test_id", "test_board", Collections.emptyList());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        // Given
        final TrelloCardDto trelloCardDto = new TrelloCardDto("Test task", "Test Description", "top", "test_id");
        final URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");
        final CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard(
                "1",
                "Test task",
                "http://test.com",
                null
        );
        when(restTemplate.postForObject(uri, null, CreatedTrelloCard.class)).thenReturn(createdTrelloCard);
        // When
        final CreatedTrelloCard newCreatedTrelloCard = trelloClient.createNewCard(trelloCardDto);
        // Then
        assertThat(newCreatedTrelloCard)
                .extracting(CreatedTrelloCard::getId, CreatedTrelloCard::getName, CreatedTrelloCard::getShortUrl, CreatedTrelloCard::getBadges)
                .containsExactly("1", "Test task", "http://test.com", null);
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        // Given
        final URI uri = new URI(String.format("http://test.com/members/%s/boards?key=test&token=test&fields=name,id&lists=all",
                trelloConfig.getTrelloUserName()));
        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);
        // When
        final List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getBoards();
        // Then
        assertThat(fetchedTrelloBoards).hasSize(0);
    }
}