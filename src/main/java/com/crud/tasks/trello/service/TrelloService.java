package com.crud.tasks.trello.service;

import static java.util.Optional.ofNullable;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedCardMailWithTemplate;
import com.crud.tasks.domain.MailImpl;
import com.crud.tasks.service.SimpleEmailService;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.dto.TrelloBoardDto;
import com.crud.tasks.trello.dto.TrelloCardDto;
import com.crud.tasks.trello.dto.TrelloCreatedCardDto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrelloService {
    private static final String SUBJECT = "Tasks: New Trello card";
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private TrelloClient trelloClient;
    @Autowired
    private SimpleEmailService emailService;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getBoards();
    }

    public TrelloCreatedCardDto createTrelloCard(final TrelloCardDto trelloCardDto) {
        final TrelloCreatedCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        ofNullable(newCard).ifPresent(card -> emailService.send(
                new CreatedCardMailWithTemplate(
                        new MailImpl.Builder()
                                .mailTo(adminConfig.getAdminMail())
                                .subject(SUBJECT)
                                .message(String.format("New card: %s has been created on your Trello account.", card.getName()))
                                .build())
        ));
        return newCard;
    }
}
