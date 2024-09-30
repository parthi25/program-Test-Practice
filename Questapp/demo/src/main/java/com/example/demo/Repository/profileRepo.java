package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.profile;
import java.util.List;


public interface profileRepo extends JpaRepository <profile, Long> {

    List<profile> findByUserId(Long userId);
    void deleteByUserId(Long userId);

}
