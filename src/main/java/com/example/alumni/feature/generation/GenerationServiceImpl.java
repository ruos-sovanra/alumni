package com.example.alumni.feature.generation;

import com.example.alumni.domain.Generation;
import com.example.alumni.feature.generation.GenerationDto.CreateGenerationRequest;
import com.example.alumni.feature.generation.GenerationDto.GenerationResponse;
import com.example.alumni.mapper.GenerationMapper;
import com.example.alumni.utils.CustomPageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GenerationServiceImpl implements GenerationService{

    private final GenerationRepository generationRepository;
    private final GenerationMapper generationMapper;


    @Override
    public GenerationResponse createGeneration(CreateGenerationRequest request) {

        Generation generation = generationMapper.requestToGeneration(request);

        generationRepository.save(generation);

        return generationMapper.toGenerationResponse(generation);
    }

    @Override
    public GenerationResponse updateGeneration(String id, CreateGenerationRequest request) {

        Generation generation = generationRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Generation not found")
        );

        generation.setGenType(request.genType());
        generation.setNumGen(request.numGen());

        generationRepository.save(generation);

        return generationMapper.toGenerationResponse(generation);
    }

    @Override
    public void deleteGeneration(String id) {
        generationRepository.deleteById(id);
    }

    @Override
    public CustomPageUtils<GenerationResponse> getGeneration(int page, int size, String baseUrl) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Generation> generations = generationRepository.findAll(pageable);


        return CustomPagination(generations.map(generationMapper::toGenerationResponse), baseUrl);
    }

    public CustomPageUtils<GenerationResponse> CustomPagination(Page<GenerationResponse> page, String baseUrl){
        CustomPageUtils<GenerationResponse> customPage = new CustomPageUtils<>();

        if (page.hasNext()){
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
