package com.example.testy.controller;

import com.example.testy.exception.TestyNotFoundException;
import com.example.testy.entity.Testy;
import com.example.testy.service.TestyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users")
public class TestyController {

    @Autowired
    private TestyService testyService;

    @GetMapping("")
    public List<Testy> getAllUsers() {
        return testyService.findAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewTesty(@Valid @RequestBody Testy testy) throws MethodArgumentNotValidException {
        testyService.addTesty(testy);
    }

    @GetMapping("/{id}")
    public Testy getTesty(@PathVariable("id") Long id) throws TestyNotFoundException {
        return testyService.getTesty(id).orElseThrow(() -> new TestyNotFoundException(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTesty(@PathVariable("id") Long id, @Valid @RequestBody Testy testy) throws TestyNotFoundException, MethodArgumentNotValidException {
        try {
            testyService.updateTesty(id, testy);
        } catch (EntityNotFoundException e) {
            throw new TestyNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTesty(@PathVariable("id") Long id) {
        testyService.deleteTesty(id);
    }
}
