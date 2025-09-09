package com.company.security_jwt1.util;

import com.company.security_jwt1.dto.StudentReq;
import com.company.security_jwt1.dto.StudentResponse;
import com.company.security_jwt1.entity.Role;
import com.company.security_jwt1.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    //DTO -> Entity
//  Here Target: student.roles, Source: dto.roles
    @Mapping(target = "roles", source = "roles",qualifiedByName = "setStringToSetRole")
    Student toEntity(StudentReq studentReq);

//  Here Target: dto.roles, student.roles
    @Mapping(target="roles", source ="roles", qualifiedByName = "setRoleToSetString")
    StudentResponse toStudentResponse(Student student);

    // Custom mapping-> Set<String> to Set<Role>
    @Named("setStringToSetRole")
    default Set<Role> setStringToSetRole(Set<String> roles){
        return roles.stream()
                .map(role->Role.valueOf(role.toUpperCase()))
                .collect(Collectors.toSet());
    }

    // Custom mapping-> Set<Role> to Set<String>
    @Named("setRoleToSetString")
    default Set<String> setRoleToSetString(Set<Role> roles){
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
