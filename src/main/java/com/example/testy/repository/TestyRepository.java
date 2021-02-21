package com.example.testy.repository;

import com.example.testy.entity.Testy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestyRepository extends JpaRepository<Testy, Long> {

}
