package com.example.demo.Services;

import java.util.List;

import com.example.demo.Model.Questions;
import com.example.demo.Model.Quizzes;

public interface AdminService {

    public String insertQuiz( Quizzes Quiz);
    public String updateQuiz(Quizzes Quiz);
    public String deleteQuiz( Long id);
    public List<Quizzes>  getAllQuiz();

    public String insertQuestion( Questions question);
    public String updateQuestion( Questions question);
    public String deleteQuestion( Long id);
    public List<Questions> getQuestions ( );
    public List<Questions> questionsById(Long Quiz_id);

    
}
