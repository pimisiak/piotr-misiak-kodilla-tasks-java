package com.crud.tasks.trello.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crud.tasks.trello.dto.TrelloAttachmentsByTypeDto;
import com.crud.tasks.trello.dto.TrelloBadgeDto;
import com.crud.tasks.trello.dto.TrelloBoardDto;
import com.crud.tasks.trello.dto.TrelloCardDto;
import com.crud.tasks.trello.dto.TrelloCreatedCardDto;
import com.crud.tasks.trello.dto.TrelloDto;
import com.crud.tasks.trello.dto.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(TrelloController.class)
public class TrelloControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrelloFacade trelloFacade;

    @Test
    public void shouldFetchEmptyTrelloBoards() throws Exception {
        //Given
        final List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoardDtos);
        //When & Then
        mockMvc.perform(get("/api/v1/trello/boards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTrelloBoards() throws Exception {
        //Given
        final List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "Test List", false));
        final List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("1", "Test Task", trelloListDtos));
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoardDtos);
        //When & Then
        mockMvc.perform(get("/api/v1/trello/boards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //Trello board fields
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Test Task"))
                //Trello list fields
                .andExpect(jsonPath("$[0].lists", hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id", is("1")))
                .andExpect(jsonPath("$[0].lists[0].name", is("Test List")))
                .andExpect(jsonPath("$[0].lists[0].closed", is(false)));
    }

    @Test
    public void shouldCreateTrelloCard() throws Exception {
        //Given
        final TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test", "Test description", "top", "1");
        final TrelloCreatedCardDto trelloCreatedCardDto = new TrelloCreatedCardDto(
                "323", "Test", "http://test.com",
                new TrelloBadgeDto(0, new TrelloAttachmentsByTypeDto(new TrelloDto(0, 0))));
        final Gson gson = new Gson();
        final String jsonContent = gson.toJson(trelloCardDto);
        when(trelloFacade.createCard(any(TrelloCardDto.class))).thenReturn(trelloCreatedCardDto);
        //When & Then
        mockMvc.perform(post("/api/v1/trello/cards")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(jsonContent))
                .andExpect(jsonPath("$.id", is("323")))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.shortUrl", is("http://test.com")))
                .andExpect(jsonPath("$.badges.votes", is(0)))
                .andExpect(jsonPath("$.badges.attachmentsByType.trelloDto.board", is(0)))
                .andExpect(jsonPath("$.badges.attachmentsByType.trelloDto.card", is(0)));
    }
}