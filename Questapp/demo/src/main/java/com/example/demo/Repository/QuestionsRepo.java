package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Questions;

public interface QuestionsRepo extends JpaRepository <Questions, Long>{

    List<Questions> findByQuiz(Long quiz);

}
