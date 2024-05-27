package com.example.alumni.feature.job;

import com.example.alumni.domain.Job;
import com.example.alumni.domain.Post;
import com.example.alumni.domain.Status;
import com.example.alumni.domain.User;
import com.example.alumni.feature.job.JobDto.CreateJobRequest;
import com.example.alumni.feature.job.JobDto.JobResponse;
import com.example.alumni.feature.repo.PostRepository;
import com.example.alumni.feature.repo.StatusRepository;
import com.example.alumni.feature.user.UserRepository;
import com.example.alumni.mapper.JobMapper;
import com.example.alumni.utils.CustomPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService{

    private final JobRepository jobRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final JobMapper jobMapper;


    @Override
    public JobResponse createJob(CreateJobRequest jobRequest) {

        Job job = jobMapper.requestToJob(jobRequest);

        Status status = statusRepository.findByStatus(jobRequest.statusCode());

        User user = userRepository.findUserById(jobRequest.userId());

        Post post = postRepository.findByType("JOB");

        job.setStatus(status);
        job.setUser(user);
        job.setPostType(post);

        jobRepository.save(job);
        return jobMapper.toJobResponse(job);
    }

    @Override
    public JobResponse updateJob(String id, CreateJobRequest jobRequest) {

        Job job = jobRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Job not found"));

        Status status = statusRepository.findByStatus(jobRequest.statusCode());

        User user = userRepository.findUserById(jobRequest.userId());

        Post post = postRepository.findByType("JOB");

        job.setStatus(status);
        job.setUser(user);
        job.setPostType(post);
        job.setJobDescription(jobRequest.jobDescription());
        job.setJobTitle(jobRequest.jobTitle());
        job.setJobLocation(jobRequest.jobLocation());
        job.setCompanyName(jobRequest.companyName());
        job.setPosition(jobRequest.position());
        job.setPoster(jobRequest.poster());
        job.setSalary(jobRequest.salary());
        job.setRequirements(jobRequest.requirements());

        jobRepository.save(job);
        return jobMapper.toJobResponse(job);
    }

    @Override
    public void deleteJob(String id) {
        jobRepository.deleteById(id);
    }

    @Override
    public CustomPageUtils<JobResponse> getAllJobs(int page, int size, String baseUrl) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<Job> jobs = jobRepository.findAll(pageable);

        return customPagination(jobs.map(jobMapper::toJobResponse), baseUrl);
    }

    public CustomPageUtils<JobResponse> customPagination(Page<JobResponse> page, String baseUrl) {
        CustomPageUtils<JobResponse> customPageUtils = new CustomPageUtils<>();

        if(page.hasNext()){
            customPageUtils.setNext(baseUrl + "?page=" + (page.getNumber() + 1) + "&size=" + page.getSize());
        }

        if (page.hasPrevious()){
            customPageUtils.setPrevious(baseUrl + "?page=" + (page.getNumber() - 1) + "&size=" + page.getSize());
        }

        customPageUtils.setTotal((int) page.getTotalElements());
        customPageUtils.setResults(page.getContent());

        return customPageUtils;
    }
}
