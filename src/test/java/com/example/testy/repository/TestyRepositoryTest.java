package com.example.testy.repository;

import com.example.testy.entity.Testy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestyRepositoryTest {

    @Autowired
    private TestyRepository testyRepository;

    @Test
    public void save_auto_generated_id() {
        Testy testy = Testy.builder().name("Mustafa").build();
        testyRepository.save(testy);
        assertNotNull(testy.getId());
    }

    @Test
    public void delete() {
        Testy testy = Testy.builder().name("Mustafa").build();
        testyRepository.save(testy);
        assertTrue(testyRepository.findById(1L).isPresent());
        testyRepository.deleteById(1L);
        assertFalse(testyRepository.findById(1L).isPresent());
    }

    @Test
    public void update() {
        Testy testy = Testy.builder().name("Mustafa").build();
        testyRepository.save(testy);
        Testy testyRef = testyRepository.getOne(1L);
        testyRef.setName("Mustafa2");
        testyRepository.save(testyRef);
        assertEquals(testyRepository.getOne(1L).getName(), "Mustafa2");
    }
}
