package com.example.educonnect.service.security;

import com.example.educonnect.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("studentSecurityService")
public class StudentSecurityService {
    private final StudentRepository studentRepository;

    public boolean isOwnProfile(UserDetails principal, Long studentId){
        String currentUserEmail = principal.getUsername();
      return studentRepository.findById(studentId).map(student ->
                      student.getEmail().equals(currentUserEmail))
              .orElse(false);
    }

}
