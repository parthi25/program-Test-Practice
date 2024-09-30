package com.example.demo.Services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Model.Questions;
import com.example.demo.Model.Quizzes;
import com.example.demo.Repository.QuestionsRepo;
import com.example.demo.Repository.QuizzesRepo;

@Service
public class AdminServiceImplement implements AdminService {

    @Autowired
    private QuizzesRepo quizRepo;

    @Autowired
    private QuestionsRepo questionRepo;

    @Override
    public String insertQuiz(Quizzes quiz) {
        if (quiz != null) {
            quizRepo.save(quiz);
            return "inserted";
        } else {
            return "insert failed";
        }
    }

    @Override
    public String updateQuiz(Quizzes quiz) {
        if (quiz != null) {
            quizRepo.save(quiz);
            return "updated";
        } else {
            return "update failed";
        }
    }

    @Override
    public String deleteQuiz(Long id) {
        quizRepo.deleteById(id);
        return "deleted";
    }

    @Override
    public String insertQuestion(Questions question) {
        if (question != null) {
            questionRepo.save(question);
            return "inserted";
        } else {
            return "insert failed";
        }
    }

    @Override
    public String updateQuestion(Questions question) {
        if (question != null) {
            questionRepo.save(question);
            return "updated";
        } else {
            return "update failed";
        }
    }

    @Override
    public String deleteQuestion(Long id) {
        questionRepo.deleteById(id);
        return "deleted";
    }

    @Override
    public List<Quizzes> getAllQuiz() {
        return quizRepo.findAll();
    }

    @Override
    public List<Questions> getQuestions() {
        return questionRepo.findAll();
    }

    @Override
    public List<Questions> questionsById(Long Quiz_id) {
        return questionRepo.findByQuiz(Quiz_id);
    }

}
