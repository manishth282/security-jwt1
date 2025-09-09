package com.company.security_jwt1.repository;

import com.company.security_jwt1.entity.Student;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByEmail(String email);

    void deleteByEmail(String email);
}
