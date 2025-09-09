package com.company.security_jwt1.controller;

import com.company.security_jwt1.dto.StudentReq;
import com.company.security_jwt1.dto.StudentResponse;
import com.company.security_jwt1.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/student")
    public ResponseEntity<StudentResponse> register(@RequestBody @Valid StudentReq studentReq){
        StudentResponse studentResponse = studentService.register(studentReq);
        return ResponseEntity.created(URI.create("http://localhost:8080/api/get")).body(studentResponse);
    }
    @GetMapping("/students")
    public ResponseEntity<List<StudentResponse>> getStudents(){
        List<StudentResponse> students = studentService.getStudents();
        if (students.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        return ResponseEntity.ok().body(students);
    }
    @GetMapping("/student/{email}")
    public ResponseEntity<?> getStudent(@PathVariable String email){
        StudentResponse student = studentService.getStudent(email);
        log.warn("getStudent::: student :: "+student);
        System.out.println("getStudent::: student :: "+student);
        if(student == null) {
            Map<String, String> response = Map.of("message", "No Data found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return ResponseEntity.ok(student);
    }
    @PutMapping("/student")
    public ResponseEntity<StudentResponse> updateStudent(@Valid @RequestBody StudentReq studentReq){
        StudentResponse updatedStudent = studentService.updateStudent(studentReq);
        return ResponseEntity.ok(updatedStudent);
    }
    @DeleteMapping("/student/{email}")
    public ResponseEntity<Map<String,String>> deleteStudent(@PathVariable String email){
        return ResponseEntity.ok(studentService.deleteStudent(email));

    }


}
