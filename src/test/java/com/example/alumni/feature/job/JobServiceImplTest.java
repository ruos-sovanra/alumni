package com.example.alumni.feature.job;

import com.example.alumni.domain.Job;
import com.example.alumni.feature.job.JobDto.CreateJobRequest;
import com.example.alumni.feature.job.JobDto.JobResponse;
import com.example.alumni.feature.repo.PostRepository;
import com.example.alumni.feature.repo.StatusRepository;
import com.example.alumni.feature.user.UserRepository;
import com.example.alumni.mapper.JobMapper;
import com.example.alumni.utils.CustomPageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class JobServiceImplTest {

    @InjectMocks
    private JobServiceImpl jobService;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private JobMapper jobMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllJobs() {
        Job job = new Job();
        Map<String, Object> map = new HashMap<>();
// populate the map with your data

        JobResponse jobResponse = new JobResponse(
                "arg1",
                "arg2",
                "arg3",
                "arg4",
                "arg5",
                "arg6",
                new BigDecimal("123.45"),
                "arg8",
                "arg9",
                map
        );
        Page<Job> jobPage = new PageImpl<>(Collections.singletonList(job), PageRequest.of(0, 10), 1);
        when(jobRepository.findAll(any(PageRequest.class))).thenReturn(jobPage);
        when(jobMapper.toJobResponse(any())).thenReturn(jobResponse);

        CustomPageUtils<JobResponse> result = jobService.getAllJobs(0, 10, "http://localhost:8080/api/v1/jobs");

        assertEquals(1, result.getTotal());
        assertEquals(jobResponse, result.getResults().get(0));
    }

    @Test
    public void testCreateJob() {
        // Create a CreateJobRequest object with required arguments
        CreateJobRequest request = new CreateJobRequest(
                "arg1", "arg2", "arg3", "arg4", "arg5",
                new BigDecimal("123.45"), "arg7", "arg8", "arg9", new HashMap<>()
        );

        // Create a Job object
        Job job = new Job();

        // Create a JobResponse object with required arguments
        JobResponse jobResponse = new JobResponse(
                "1",
                "arg1", "arg2", "arg3", "arg4", "arg5",
                new BigDecimal("123.45"), "arg8", "arg9", new HashMap<>()
        );

        // Define the behavior of mocked objects
        when(jobMapper.requestToJob(any())).thenReturn(job);
        when(jobRepository.save(any())).thenReturn(job);
        when(jobMapper.toJobResponse(any())).thenReturn(jobResponse);

        // Call the method to be tested
        JobResponse result = jobService.createJob(request);

        // Verify the result
        assertEquals(jobResponse, result);
    }

    @Test
    public void testUpdateJob() {
        // Define the id and CreateJobRequest object with required arguments
        String id = "testId";
        CreateJobRequest request = new CreateJobRequest(
                "arg1", "arg2", "arg3", "arg4", "arg5",
                new BigDecimal("123.45"), "arg7", "arg8", "arg9", new HashMap<>()
        );

        // Create a Job object
        Job job = new Job();

        // Create a JobResponse object with required arguments
        JobResponse jobResponse = new JobResponse(
                "1",
                "arg1", "arg2", "arg3", "arg4", "arg5",
                new BigDecimal("123.45"), "arg8", "arg9", new HashMap<>()
        );

        // Define the behavior of mocked objects
        when(jobRepository.findById(any())).thenReturn(Optional.of(job));
        when(jobRepository.save(any())).thenReturn(job);
        when(jobMapper.toJobResponse(any())).thenReturn(jobResponse);

        // Call the method to be tested
        JobResponse result = jobService.updateJob(id, request);

        // Verify the result
        assertEquals(jobResponse, result);
    }

    @Test
    public void testDeleteJob() {
        // Define the id
        String id = "testId";

        // Define the behavior of mocked objects
        doNothing().when(jobRepository).deleteById(any());

        // Call the method to be tested and verify that no exception is thrown
        assertDoesNotThrow(() -> jobService.deleteJob(id));
    }
}