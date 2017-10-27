package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;

import com.google.common.base.Preconditions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(method = RequestMethod.GET, value = "/boards")
    public void getTrelloBoards() {
        final List<TrelloBoardDto> trelloBoards = trelloClient.getBoards();
        trelloBoards.stream()
                .filter(trelloBoardDto -> trelloBoardDto.getName().contains("Kodilla"))
                .filter(trelloBoardDto -> !trelloBoardDto.getId().isEmpty())
                .forEach(trelloBoardDto -> {
                    System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
                    System.out.println("This board contains lists: ");
                    trelloBoardDto.getLists().forEach(trelloList ->
                            System.out.printf("%s - %s - %s%n", trelloList.getId(), trelloList.getName(), trelloList.isClosed()));
                });
    }

    @RequestMapping(method = RequestMethod.POST, value = "/cards")
    public CreatedTrelloCard createTrelloCard(@RequestBody final TrelloCardDto trelloCardDto) {
        Preconditions.checkNotNull(trelloCardDto);
        return trelloClient.createNewCard(trelloCardDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public void handleNullPointerException() {
    }
}
