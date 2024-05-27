package com.example.alumni.feature.employ;

import com.example.alumni.domain.Employ;
import com.example.alumni.feature.employ.EmployDto.CreateEmployRequest;
import com.example.alumni.feature.employ.EmployDto.EmployResponse;
import com.example.alumni.mapper.EmployMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployServiceImplTest {

    @InjectMocks
    private EmployServiceImpl employService;

    @Mock
    private EmployRepository employRepository;

    @Mock
    private EmployMapper employMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateEmployType() {
        CreateEmployRequest request = new CreateEmployRequest(
                "Employ Type"
        );
        Employ employ = new Employ();
        EmployResponse response = new EmployResponse(
                "1",
                "Employ Type"
        );

        when(employMapper.requestToEmploy(request)).thenReturn(employ);
        when(employMapper.toEmployResponse(employ)).thenReturn(response);

        employService.createEmployType(request);

        verify(employRepository).save(employ);
    }

    @Test
    public void testUpdateEmployType() {
        String id = "1";
        CreateEmployRequest request = new CreateEmployRequest(
                "Employ Type"
        );
        Employ employ = new Employ();
        EmployResponse response = new EmployResponse(
                "1",
                "Employ Type"
        );

        when(employRepository.findById(id)).thenReturn(java.util.Optional.of(employ));
        when(employMapper.toEmployResponse(employ)).thenReturn(response);

        employService.updateEmployType(id, request);

        verify(employRepository).save(employ);
    }

    @Test
    public void testDeleteEmployType() {
        String id = "1";

        employService.deleteEmployType(id);

        verify(employRepository).deleteById(id);
    }
}