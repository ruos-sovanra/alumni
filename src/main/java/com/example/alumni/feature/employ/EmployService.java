package com.example.alumni.feature.employ;

import com.example.alumni.feature.employ.EmployDto.CreateEmployRequest;
import com.example.alumni.feature.employ.EmployDto.EmployResponse;
import com.example.alumni.utils.CustomPageUtils;

public interface EmployService {

    EmployResponse createEmployType(CreateEmployRequest employTypeRequest);
    EmployResponse updateEmployType(String id, CreateEmployRequest employTypeRequest);
    void deleteEmployType(String id);
    CustomPageUtils<EmployResponse> getEmployTypes(int page, int size, String baseUrl);
}
