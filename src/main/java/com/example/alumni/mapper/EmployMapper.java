package com.example.alumni.mapper;

import com.example.alumni.domain.Employ;
import com.example.alumni.feature.employ.EmployDto.CreateEmployRequest;
import com.example.alumni.feature.employ.EmployDto.EmployResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployMapper {

    EmployResponse toEmployResponse(Employ employ);

    Employ requestToEmploy(CreateEmployRequest request);
}
