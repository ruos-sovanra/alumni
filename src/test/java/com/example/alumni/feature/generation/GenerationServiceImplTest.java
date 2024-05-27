package com.example.alumni.feature.generation;

import com.example.alumni.domain.Generation;
import com.example.alumni.feature.generation.GenerationDto.CreateGenerationRequest;
import com.example.alumni.feature.generation.GenerationDto.GenerationResponse;
import com.example.alumni.mapper.GenerationMapper;
import com.example.alumni.utils.CustomPageUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GenerationServiceImplTest {

    @InjectMocks
    private GenerationServiceImpl generationService;

    @Mock
    private GenerationRepository generationRepository;

    @Mock
    private GenerationMapper generationMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateGeneration() {
        CreateGenerationRequest request = new CreateGenerationRequest(
                "genType",
                1
        );
        Generation generation = new Generation();
        GenerationResponse response = new GenerationResponse(
                "1",
                "genType",
                1
        );

        when(generationMapper.requestToGeneration(request)).thenReturn(generation);
        when(generationRepository.save(generation)).thenReturn(generation);
        when(generationMapper.toGenerationResponse(generation)).thenReturn(response);

        GenerationResponse result = generationService.createGeneration(request);

        assertEquals(response, result);
        verify(generationRepository, times(1)).save(generation);
    }

    @Test
    public void testUpdateGeneration() {
        String id = "1";
        CreateGenerationRequest request = new CreateGenerationRequest(
                "genType",
                1
        );
        Generation generation = new Generation();
        GenerationResponse response = new GenerationResponse(
                "1",
                "genType",
                1
        );

        when(generationRepository.findById(id)).thenReturn(Optional.of(generation));
        when(generationRepository.save(generation)).thenReturn(generation);
        when(generationMapper.toGenerationResponse(generation)).thenReturn(response);

        GenerationResponse result = generationService.updateGeneration(id, request);

        assertEquals(response, result);
        verify(generationRepository, times(1)).save(generation);
    }

    @Test
    public void testDeleteGeneration() {
        String id = "1";
        doNothing().when(generationRepository).deleteById(id);

        generationService.deleteGeneration(id);

        verify(generationRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetGeneration() {
        int page = 0;
        int size = 10;
        String baseUrl = "http://localhost";
        Generation generation = new Generation();
        GenerationResponse response = new GenerationResponse(
                "1",
                "genType",
                1
        );
        Page<Generation> generationPage = new PageImpl<>(Collections.singletonList(generation), PageRequest.of(page, size), 1);

        when(generationRepository.findAll(any(PageRequest.class))).thenReturn(generationPage);
        when(generationMapper.toGenerationResponse(generation)).thenReturn(response);

        CustomPageUtils<GenerationResponse> result = generationService.getGeneration(page, size, baseUrl);

        assertEquals(1, result.getTotal());
        assertEquals(response, result.getResults().get(0));
    }
}