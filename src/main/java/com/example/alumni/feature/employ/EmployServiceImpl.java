package com.example.alumni.feature.employ;

import com.example.alumni.domain.Employ;
import com.example.alumni.feature.employ.EmployDto.CreateEmployRequest;
import com.example.alumni.feature.employ.EmployDto.EmployResponse;
import com.example.alumni.mapper.EmployMapper;
import com.example.alumni.utils.CustomPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployServiceImpl implements EmployService{

    private final EmployRepository employRepository;
    private final EmployMapper employMapper;

    @Override
    public EmployResponse createEmployType(CreateEmployRequest employTypeRequest) {

        Employ employ = employMapper.requestToEmploy(employTypeRequest);

        employRepository.save(employ);

        return employMapper.toEmployResponse(employ);
    }

    @Override
    public EmployResponse updateEmployType(String id, CreateEmployRequest employTypeRequest) {

        Employ employ = employRepository.findById(id).orElseThrow();

        employ.setEmployType(employTypeRequest.employType());

        employRepository.save(employ);

        return employMapper.toEmployResponse(employ);
    }

    @Override
    public void deleteEmployType(String id) {

        employRepository.deleteById(id);

    }

    @Override
    public CustomPageUtils<EmployResponse> getEmployTypes(int page, int size, String baseUrl) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Employ> employTypes = employRepository.findAll(pageable);

        return CustomPagination(employTypes.map(employMapper::toEmployResponse), baseUrl);
    }

    public CustomPageUtils<EmployResponse> CustomPagination(Page<EmployResponse> page, String baseUrl){
        CustomPageUtils<EmployResponse> customPage = new CustomPageUtils<>();

        if(page.hasNext()){
            customPage.setNext(baseUrl + "?page=" + (page.getNumber() + 1) + "&size=" + page.getSize());
        }

        if (page.hasPrevious()){
            customPage.setPrevious(baseUrl + "?page=" + (page.getNumber() - 1) + "&size=" + page.getSize());
        }

        customPage.setTotal((int) page.getTotalElements());
        customPage.setResults(page.getContent());
        return customPage;
    }
}
