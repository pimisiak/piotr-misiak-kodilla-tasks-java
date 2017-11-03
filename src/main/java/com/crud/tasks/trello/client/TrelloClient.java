package com.crud.tasks.trello.client;

import com.crud.tasks.trello.config.TrelloConfig;
import com.crud.tasks.trello.domain.TrelloBoardDto;
import com.crud.tasks.trello.domain.TrelloCardDto;
import com.crud.tasks.trello.domain.TrelloCreatedCardDto;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class TrelloClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);
    @Autowired
    private TrelloConfig trelloConfig;
    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getBoards() {
        try {
            final TrelloBoardDto[] boardsResponse = restTemplate.getForObject(buildUriForGetBoards(), TrelloBoardDto[].class);
            return Optional.ofNullable(boardsResponse)
                    .map(Arrays::asList)
                    .orElseGet(Collections::emptyList);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private URI buildUriForGetBoards() {
        return UriComponentsBuilder.fromHttpUrl(String.format("%s/members/%s/boards", trelloConfig.getTrelloApiEndpoint(), trelloConfig.getTrelloUserName()))
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloAppToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build().encode().toUri();
    }

    public TrelloCreatedCardDto createNewCard(final TrelloCardDto trelloCardDto) {
        return restTemplate.postForObject(buildUriForCreateNewCard(trelloCardDto), null, TrelloCreatedCardDto.class);

    }

    private URI buildUriForCreateNewCard(final TrelloCardDto trelloCardDto) {
        return UriComponentsBuilder.fromHttpUrl(String.format("%s/cards", trelloConfig.getTrelloApiEndpoint()))
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloAppToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build().encode().toUri();
    }
}
