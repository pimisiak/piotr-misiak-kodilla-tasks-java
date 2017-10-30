package com.crud.tasks.controller;

import com.crud.tasks.trello.domain.TrelloCreatedCardDto;
import com.crud.tasks.trello.domain.TrelloBoardDto;
import com.crud.tasks.trello.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;

import com.google.common.base.Preconditions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trello")
public class TrelloController {
    @Autowired
    private TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "/boards", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrelloBoardDto> getTrelloBoards() {
        return trelloClient.getBoards();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cards", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TrelloCreatedCardDto createTrelloCard(@RequestBody final TrelloCardDto trelloCardDto) {
        Preconditions.checkNotNull(trelloCardDto);
        return trelloClient.createNewCard(trelloCardDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public void handleNullPointerException() {
    }
}
