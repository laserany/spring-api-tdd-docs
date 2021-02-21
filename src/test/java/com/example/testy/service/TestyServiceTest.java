package com.example.testy.service;

import com.example.testy.entity.Testy;
import com.example.testy.repository.TestyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TestyService.class)
public class TestyServiceTest {

    private Testy testy;

    @Autowired
    private TestyService testyService;

    @MockBean
    TestyRepository testyRepository;

    @BeforeEach
    public void setUp() {
        testy = Testy.builder().name("Mustafa").build();
    }

    @Test
    public void addTesty() {
        testyService.addTesty(testy);
        verify(testyRepository).save(testy);
    }

    @Test
    public void findAllTesties() {
        testyService.findAll();
        verify(testyRepository).findAll();
    }

    @Test
    public void findTesty() {
        testyService.getTesty(1L);
        verify(testyRepository).findById(1L);
    }

    @Test
    public void updateTesty() {
        Testy testy = Testy.builder().id(1L).name("Mustafa").build();
        Testy mockedTesty = mock(Testy.class);
        when(testyRepository.getOne(1L)).thenReturn(mockedTesty);
        testyService.updateTesty(1L, testy);
        verify(testyRepository).save(mockedTesty);
        verify(mockedTesty).setName(testy.getName());
    }

    @Test
    public void deleteTesty() {
        testyService.deleteTesty(1L);
        verify(testyRepository).deleteById(1L);
    }

}
