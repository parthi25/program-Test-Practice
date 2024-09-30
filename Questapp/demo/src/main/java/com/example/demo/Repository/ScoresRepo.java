package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Scores;

public interface ScoresRepo extends JpaRepository <Scores, Long>{

    List<Scores> findByUser(int user);
}
