package com.example.demo.Services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Model.Scores;
import com.example.demo.Model.Users;
import com.example.demo.Model.profile;
import com.example.demo.Repository.ScoresRepo;
import com.example.demo.Repository.UsersRepository;
import com.example.demo.Repository.profileRepo;

@Service
public class ServiceImplement implements userService {

    private final Path storageloc;


    @Autowired
    private UsersRepository ur;

    @Autowired
    private ScoresRepo SR;

    @Autowired
    private profileRepo pr;

    public ServiceImplement() {
        this.storageloc = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            // Check if the directory already exists before trying to create it
            if (!Files.exists(storageloc)) {
                Files.createDirectory(storageloc);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create directory: " + e);
        }
    }
    

    @Override
    public String insertUser(Users data) {
        try {
            System.out.println( );
            ur.save(data);
            return "Success";
        } catch (Exception e) {
            throw new RuntimeException("Error inserting user", e);
        }
    }

    @Override
    public Users findUser(String email, String password) {
        Optional<Users> optionalUser = ur.findByEmail(email);
        if (optionalUser.isPresent()) {
            Users u = optionalUser.get();
            if (password.trim().equals(u.getPassword().trim())) {
                return u;
            } else {
                u.setPassword("incorrect password");
                return u;
            }
        } else {
            Users u = new Users();
            u.setId(null);
            return u;
        }
    }



    @Override
    public String updateScore(Scores score) {
        if (score != null) {
            SR.save(score);
            return "Updated";
        } else {
            return "Update failed";
        }
    }

    @Override
    public String deleteScore(Long id) {
        try {
            SR.deleteById(id);
            return "Deleted";
        } catch (Exception e) {
            return "Delete failed: " + e.getMessage();
        }
    }

    @Override
    public List<Scores> FindScores() {
        return SR.findAll();
    }


    @Override
    public List<profile> getAllProfiles() {
        return pr.findAll();
    }

    @Override
    public List<profile> getProfileById(Long id) {
        return pr.findByUserId(id);
    }

    @Override
    public String updateProfile(MultipartFile file, Long userId, String userName) {
        try {
            // Retrieve the user by ID
            Optional<Users> optionalUser = ur.findById(userId);
            if (!optionalUser.isPresent()) {
                throw new RuntimeException("User not found");
            }
    
            Users user = optionalUser.get();
            user.setUser_name(userName);
    
            // Handle file upload and profile update
            if (file != null && !file.isEmpty()) {
                @SuppressWarnings("null")
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    
                if (fileName.contains("..")) {
                    throw new RuntimeException("Invalid file path: " + fileName);
                }
    
                Path saveLocation = this.storageloc.resolve(fileName);
    
                // Copy the file and replace if it already exists
                Files.copy(file.getInputStream(), saveLocation, StandardCopyOption.REPLACE_EXISTING);
    
                List<profile> optionalProfile = pr.findByUserId(userId);
                profile p;
                if (optionalProfile != null && !optionalProfile.isEmpty()) {
                    p = optionalProfile.get(0);
                } else {
                    p = new profile();
                    p.setUserId(userId);
                }
    
                p.setFileName(fileName);
                p.setFiletype(file.getContentType());
                p.setUrl(saveLocation.toString());
    
                pr.save(p);
            }
    
            // Save the updated user information
            ur.save(user);
    
            return "Profile updated successfully";
        } catch (Exception e) {
            throw new RuntimeException("Error updating profile: " + e.getMessage(), e);
        }
    }
    
    

    @Override
    public void deleteProfile(Long id) {
        try {
            pr.deleteByUserId(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete profile failed", e);
        }
    }


    @Override
    public Optional<Users> finUsers(Long id) {
        return ur.findById(id);
    }


    @Override
    public List<Scores> findbyid(int id) {
       int user = id;
        return SR.findByUser(user);
    }
}
