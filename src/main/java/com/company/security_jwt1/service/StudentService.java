package com.company.security_jwt1.service;

import com.company.security_jwt1.dto.StudentReq;
import com.company.security_jwt1.dto.StudentResponse;
import com.company.security_jwt1.entity.Student;
import com.company.security_jwt1.exception.StudentNotExist;
import com.company.security_jwt1.repository.StudentRepository;
import com.company.security_jwt1.util.StudentMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepo;
    @Autowired
    private StudentMapper studentMapper;
    @Value("${studentNotExist.Exception}")
    private String studentNotExist;
    @Value("${student.Deleted.Message}")
    private String deleteMessage;

    public StudentResponse register(StudentReq studentReq) {
        Student student = studentMapper.toEntity(studentReq);
        Student savedStudent = studentRepo.save(student);
        return studentMapper.toStudentResponse(savedStudent);
    }

    public List<StudentResponse> getStudents() {
        List<Student> students = studentRepo.findAll();
        return students.stream()
                    .map(student -> studentMapper.toStudentResponse(student))
                    .toList();
    }

    public StudentResponse getStudent(String email) {
        Student student = studentRepo.findByEmail(email);
        return studentMapper.toStudentResponse(student);
    }

    public StudentResponse updateStudent(StudentReq studentReq) {
        Student student = studentRepo.findByEmail(studentReq.email());
        if (student==null)
            throw new StudentNotExist(studentNotExist+studentReq.email());
        Student entity = studentMapper.toEntity(studentReq);
        entity.setId(student.getId());
        return studentMapper.toStudentResponse(studentRepo.save(entity));

    }

    @Transactional
    public Map<String, String> deleteStudent(String email) {
        Student student = studentRepo.findByEmail(email);
        if (student==null)
            throw new StudentNotExist(studentNotExist+email);
        studentRepo.deleteByEmail(email);
        return Map.of("message",deleteMessage+email);
    }
}
