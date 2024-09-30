package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.Model.Questions;
import com.example.demo.Model.Quizzes;
import com.example.demo.Model.Scores;
import com.example.demo.Model.Users;
import com.example.demo.Model.profile;
import com.example.demo.Services.AdminServiceImplement;
import com.example.demo.Services.ServiceImplement;




@RestController
@RequestMapping("/api")
public class questController {

    @Autowired
    private ServiceImplement ser;

    @Autowired
    private AdminServiceImplement AS;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UsersRequest request) {
        try {
            Users data = new Users();
            data.setEmail(request.getEmail());
            data.setPassword(request.getPassword());
            data.setUser_name(request.getUserName());

            String result = ser.insertUser(data);

            if ("Success".equals(result)) {
                return ResponseEntity.status(HttpStatus.CREATED).body("User created");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user");
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody Users data) {
        Users user = ser.findUser(data.getEmail(), data.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/quiz")
    public ResponseEntity<List<Quizzes>> getAllQuizzes() {
        return ResponseEntity.ok(AS.getAllQuiz());
    }
    
    @GetMapping("/questions")
    public ResponseEntity<List<Questions>> getAllQuestions() {
        return ResponseEntity.ok(AS.getQuestions());
    }

    @GetMapping("/scores")
    public ResponseEntity<List<Scores>> getAllScores() {
        return ResponseEntity.ok(ser.FindScores());
    }
    
    @PostMapping("/insert/quiz")
    public ResponseEntity<String> insertQuiz(@RequestBody Quizzes quiz) {
        return ResponseEntity.ok(AS.insertQuiz(quiz));
    }
    
    @PostMapping("/insert/questions")
public ResponseEntity<String> insertQuestion(@RequestBody Questions question) {
    try {
        System.out.println("###############################");
        System.out.println(question.getQuiz());
      AS.insertQuestion(question);
       
        return ResponseEntity.ok("inserted");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert question");
    }
}


    @PutMapping("/update/quiz")
    public ResponseEntity<String> updateQuiz(@RequestBody Quizzes quiz) {
        return ResponseEntity.ok(AS.updateQuiz(quiz));
    }
    
    @PutMapping("/update/questions")
    public ResponseEntity<String> updateQuestion(@RequestBody Questions question) {
        return ResponseEntity.ok(AS.updateQuestion(question));
    }

    @DeleteMapping("/delete/quiz")
    public String deleteQuiz(@RequestParam Long id) {
        AS.deleteQuiz(id);
        return "deleted"; // Return appropriate success message
    }
    
    @DeleteMapping("/delete/questions")
       public ResponseEntity<String> deleteQuestion(@RequestParam("id") Long id) {
        try {
            AS.deleteQuestion(id);
            return ResponseEntity.ok("Question deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete question");
        }
    }

   @GetMapping("/admin")
public ResponseEntity<String> admin(@RequestParam String admin, @RequestParam String password) {
    if ("admin".equals(admin.trim()) && "1234".equals(password.trim())) {
        return ResponseEntity.ok("admin");
    } else {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("not admin");
    }
}


    @GetMapping("questions/byid")
    public ResponseEntity<List<Questions>> getQuestionsByQuizId(@RequestParam Long quizId) {
        System.out.println("hi"+quizId);
        return ResponseEntity.ok(AS.questionsById(quizId));
    }


    
      @GetMapping("/profile/all")
    public List<profile> getAllProfiles() {
        return ser.getAllProfiles();
    }



    @PostMapping("/profile")
    public ResponseEntity<List<profile>> getProfileById(@RequestBody Map<String, Long> request) {
        Long id = request.get("id");
        List<profile> profile = ser.getProfileById(id);
        return ResponseEntity.ok(profile);
    }




    @PutMapping("/update/profile")
    public ResponseEntity<String> updateProfile(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId, @RequestParam("userName") String name) {
        try {
            System.out.println(file.getContentType() + " " + file.getOriginalFilename() + " " + userId);
            String updatedProfile = ser.updateProfile(file, userId, name);
            return ResponseEntity.ok(updatedProfile);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    

    @DeleteMapping("delete/profile")
    public ResponseEntity<Void> deleteProfile(@RequestBody Long id) {
        try {
            ser.deleteProfile(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/find/user")
    public ResponseEntity<Optional<Users>> userdetail(@RequestBody Map<String, Long> request){
        Long id = request.get("id");
        Optional<Users> u = ser.finUsers(id);
        return ResponseEntity.ok(u);
    }


    @PostMapping("add/score")
    public String addscore(@RequestBody Scores score) {
        System.out.println(score);
        ser.updateScore(score);
        
        return "inserted";
    }
    
    @GetMapping("/find/score")
    public List<Scores> findscore (@RequestParam("id") int id) {
        
        System.out.println("scores"+"   "+id);
         return ser.findbyid(id);
    }
 
}
    