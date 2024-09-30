package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Scores;
import com.example.demo.Model.Users;
import com.example.demo.Model.profile;

public interface userService {

    public String insertUser(Users data);
    public Users findUser(String email,String password);
    public Optional<Users> finUsers(Long id);

    public String updateScore(Scores Score);
    public String deleteScore(Long id);
    public List<Scores> FindScores();
    public List<Scores> findbyid(int id);

    public List<profile> getAllProfiles();
    public List<profile> getProfileById(Long id);
    public String updateProfile(MultipartFile file, Long userId, String name);
    public void deleteProfile(Long id);

}
