package com.example.testy.service;

import com.example.testy.entity.Testy;
import com.example.testy.repository.TestyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestyService {

    @Autowired
    private TestyRepository testyRepository;

    public void addTesty(Testy testy) {
        testyRepository.save(testy);
    }

    public List<Testy> findAll() {
        return testyRepository.findAll();
    }

    public Optional<Testy> getTesty(Long id) {
        return testyRepository.findById(id);
    }

    public void updateTesty(Long id, Testy testy) {
        Testy testyRef = testyRepository.getOne(id);
        testyRef.setName(testy.getName());
        testyRepository.save(testyRef);
    }

    public void deleteTesty(Long id) {
        testyRepository.deleteById(id);
    }
}
