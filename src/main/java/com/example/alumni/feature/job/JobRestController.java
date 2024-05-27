package com.example.alumni.feature.job;

import com.example.alumni.feature.job.JobDto.CreateJobRequest;
import com.example.alumni.feature.job.JobDto.JobResponse;
import com.example.alumni.utils.BaseResponse;
import com.example.alumni.utils.CustomPageUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobRestController {

    private final JobService jobService;


    @GetMapping
    @Operation(summary = "Get all jobs")
    public ResponseEntity<CustomPageUtils<JobResponse>> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/jobs";
        CustomPageUtils<JobResponse> jobResponses = jobService.getAllJobs(page, size, baseUrl);
        return ResponseEntity.ok(jobResponses);
    }


    @PostMapping
    @Operation(summary = "Create job")
    public BaseResponse<JobResponse> createJob(@RequestBody CreateJobRequest jobRequest) {
        return BaseResponse.<JobResponse>createSuccess()
                .setPayload(jobService.createJob(jobRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update job")
    public BaseResponse<JobResponse> updateJob(@PathVariable String id, @RequestBody CreateJobRequest jobRequest) {
        return BaseResponse.<JobResponse>updateSuccess()
                .setPayload(jobService.updateJob(id, jobRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete job")
    public BaseResponse<Void> deleteJob(@PathVariable String id) {
        jobService.deleteJob(id);
        return BaseResponse.deleteSuccess();
    }

}
