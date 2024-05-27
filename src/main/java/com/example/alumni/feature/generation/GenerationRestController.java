package com.example.alumni.feature.generation;


import com.example.alumni.feature.generation.GenerationDto.CreateGenerationRequest;
import com.example.alumni.feature.generation.GenerationDto.GenerationResponse;
import com.example.alumni.utils.BaseResponse;
import com.example.alumni.utils.CustomPageUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/generations")
@RequiredArgsConstructor
public class GenerationRestController {

    private final GenerationService generationService;

    @GetMapping
    @Operation(summary = "Get all generations")
    public ResponseEntity<CustomPageUtils<GenerationResponse>> getAllGenerations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/generations";
        CustomPageUtils<GenerationResponse> generationResponses = generationService.getGeneration(page, size, baseUrl);
        return ResponseEntity.ok(generationResponses);
    }

    @PostMapping
    @Operation(summary = "Create generation")
    public BaseResponse<GenerationResponse> createGeneration(@RequestBody CreateGenerationRequest request) {
        return BaseResponse.<GenerationResponse>createSuccess()
                .setPayload(generationService.createGeneration(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update generation")
    public BaseResponse<GenerationResponse> updateGeneration(@PathVariable String id, @RequestBody CreateGenerationRequest request) {
        return BaseResponse.<GenerationResponse>updateSuccess()
                .setPayload(generationService.updateGeneration(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete generation")
    public BaseResponse<Void> deleteGeneration(@PathVariable String id) {
        generationService.deleteGeneration(id);
        return BaseResponse.deleteSuccess();
    }

}
