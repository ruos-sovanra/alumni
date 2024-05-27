package com.example.alumni.feature.job;

import com.example.alumni.feature.job.JobDto.CreateJobRequest;
import com.example.alumni.feature.job.JobDto.JobResponse;
import com.example.alumni.utils.CustomPageUtils;

public interface JobService {

    JobResponse createJob(CreateJobRequest jobRequest);
    JobResponse updateJob(String id, CreateJobRequest jobRequest);
    void deleteJob(String id);
    CustomPageUtils<JobResponse> getAllJobs(int page, int size, String baseUrl);
}
