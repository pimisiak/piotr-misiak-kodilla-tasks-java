package com.crud.tasks.trello.mapper;

import com.crud.tasks.trello.domain.TrelloBoard;
import com.crud.tasks.trello.domain.TrelloCard;
import com.crud.tasks.trello.domain.TrelloList;
import com.crud.tasks.trello.dto.TrelloBoardDto;
import com.crud.tasks.trello.dto.TrelloCardDto;
import com.crud.tasks.trello.dto.TrelloListDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TrelloMapper {
    public List<TrelloBoard> mapToBoards(final List<TrelloBoardDto> trelloBoardDtos) {
        return trelloBoardDtos.stream()
                .map(this::mapToBoard)
                .collect(Collectors.toList());
    }

    public TrelloBoard mapToBoard(final TrelloBoardDto trelloBoardDto) {
        return new TrelloBoard(trelloBoardDto.getId(), trelloBoardDto.getName(), mapToLists(trelloBoardDto.getLists()));
    }

    public List<TrelloBoardDto> mapToBoardDtos(final List<TrelloBoard> trelloBoards) {
        return trelloBoards.stream()
                .map(this::mapToBoardDto)
                .collect(Collectors.toList());
    }

    public TrelloBoardDto mapToBoardDto(final TrelloBoard trelloBoard) {
        return new TrelloBoardDto(trelloBoard.getId(), trelloBoard.getName(), mapToListDtos(trelloBoard.getLists()));
    }

    public List<TrelloList> mapToLists(final List<TrelloListDto> trelloListDtos) {
        return trelloListDtos.stream()
                .map(this::mapToList)
                .collect(Collectors.toList());
    }

    public TrelloList mapToList(final TrelloListDto trelloListDto) {
        return new TrelloList(trelloListDto.getId(), trelloListDto.getName(), trelloListDto.isClosed());
    }

    public List<TrelloListDto> mapToListDtos(final List<TrelloList> trelloLists) {
        return trelloLists.stream()
                .map(this::mapToListDto)
                .collect(Collectors.toList());
    }

    public TrelloListDto mapToListDto(final TrelloList trelloList) {
        return new TrelloListDto(trelloList.getId(), trelloList.getName(), trelloList.isClosed());
    }

    public TrelloCard mapToCard(final TrelloCardDto trelloCardDto) {
        return new TrelloCard(trelloCardDto.getName(), trelloCardDto.getDescription(), trelloCardDto.getPos(),
                trelloCardDto.getListId());
    }

    public TrelloCardDto mapToCardDto(final TrelloCard trelloCard) {
        return new TrelloCardDto(trelloCard.getName(), trelloCard.getDescription(), trelloCard.getPos(),
                trelloCard.getListId());
    }
}
