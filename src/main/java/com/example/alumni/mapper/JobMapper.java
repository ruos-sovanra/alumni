package com.example.alumni.mapper;

import com.example.alumni.domain.Job;
import com.example.alumni.feature.job.JobDto.CreateJobRequest;
import com.example.alumni.feature.job.JobDto.JobResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobMapper {

    JobResponse toJobResponse(Job job);

    Job requestToJob(CreateJobRequest request);

}
