package com.example.testy.controller;

import com.example.testy.entity.Testy;
import com.example.testy.service.TestyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(controllers = TestyController.class)
public class TestyControllerTest {

    private Testy testy;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestyService testyService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).
                apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
        testy = Testy.builder().id(1L).name("Mustafa").build();
    }

    @Test
    public void getAllTesties() throws Exception {
        when(testyService.findAll()).thenReturn(listOfTesties());
        mockMvc.perform(get("/users")).andExpect(status().isOk()).andExpect(content().json(jsonified(listOfTesties())));
        verify(testyService).findAll();
    }

    @Test
    public void addNewTesty() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonified(testy))).andExpect(status().isCreated());
        verify(testyService).addTesty(testy);
    }

    @Test
    public void addNewTestyWithoutName() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(jsonified(Testy.builder().id(1L).build()))).andExpect(status().isBadRequest()).andExpect(content().string("name field is required"));
    }

    @Test
    public void getTesty() throws Exception {
        when(testyService.getTesty(1L)).thenReturn(Optional.of(testy));
        mockMvc.perform(get("/users/1")).andExpect(status().isOk()).andExpect(content().json(jsonified(testy)));
        verify(testyService).getTesty(1L);
    }

    @Test
    public void getTestyNotFound() throws Exception {
        mockMvc.perform(get("/users/1")).andExpect(status().isNotFound()).andExpect(content().string("Testy with id 1 was not found"));
        verify(testyService).getTesty(1L);
    }

    @Test
    public void updateTesty() throws Exception {
        mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).content(jsonified(testy))).andExpect(status().isNoContent());
        verify(testyService).updateTesty(1L, testy);
    }

    @Test
    public void updateTestyNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(testyService).updateTesty(1L, testy);
        mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).content(jsonified(testy))).andExpect(status().isNotFound()).andExpect(content().string("Testy with id 1 was not found"));
        verify(testyService).updateTesty(1L, testy);
    }

    @Test
    public void updateTestyWithoutName() throws Exception {
        mockMvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON).content(jsonified(Testy.builder().id(1L).build()))).andExpect(status().isBadRequest()).andExpect(content().string("name field is required"));
    }

    @Test
    public void deleteTesty() throws Exception {
        mockMvc.perform(delete("/users/1")).andExpect(status().isNoContent());
        verify(testyService).deleteTesty(1L);
    }

    private List<Testy> listOfTesties() {
        return List.of(Testy.builder().id(1L).name("Mustafa1").build(), Testy.builder().id(2L).name("Mustafa2").build());
    }

    private String jsonified(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
