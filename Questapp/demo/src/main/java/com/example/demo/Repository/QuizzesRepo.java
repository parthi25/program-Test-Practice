package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Quizzes;

public interface QuizzesRepo extends JpaRepository <Quizzes, Long> {

    void deleteAllById(Long id);

}
