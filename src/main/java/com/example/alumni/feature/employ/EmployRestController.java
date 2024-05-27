package com.example.alumni.feature.employ;

import com.example.alumni.feature.employ.EmployDto.CreateEmployRequest;
import com.example.alumni.feature.employ.EmployDto.EmployResponse;
import com.example.alumni.utils.BaseResponse;
import com.example.alumni.utils.CustomPageUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employs")
@RequiredArgsConstructor
public class EmployRestController {

    private final EmployService employService;

    @GetMapping
    @Operation(summary = "Get all employs")
    public ResponseEntity<CustomPageUtils<EmployResponse>> getAllEmploys(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/employs";
        CustomPageUtils<EmployResponse> employResponses = employService.getEmployTypes(page, size, baseUrl);
        return ResponseEntity.ok(employResponses);
    }

    @PostMapping
    @Operation(summary = "Create employ")
    public BaseResponse<EmployResponse> createEmploy(@RequestBody CreateEmployRequest employRequest) {
        return BaseResponse.<EmployResponse>createSuccess()
                .setPayload(employService.createEmployType(employRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employ")
    public BaseResponse<EmployResponse> updateEmploy(@PathVariable String id, @RequestBody CreateEmployRequest employRequest) {
        return BaseResponse.<EmployResponse>updateSuccess()
                .setPayload(employService.updateEmployType(id, employRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employ")
    public BaseResponse<Void> deleteEmploy(@PathVariable String id) {
        employService.deleteEmployType(id);
        return BaseResponse.deleteSuccess();
    }

}
