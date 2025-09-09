package com.company.security_jwt1.exception;

public class StudentNotExist extends RuntimeException{
    public StudentNotExist(String message){
        super(message);
    }
}
