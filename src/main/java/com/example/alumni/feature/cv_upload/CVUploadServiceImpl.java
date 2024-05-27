package com.example.alumni.feature.cv_upload;


import com.example.alumni.domain.UserDetail;
import com.example.alumni.feature.cv_upload.dto.CVUploadResponse;
import com.example.alumni.feature.user_detail.UserDetailRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CVUploadServiceImpl implements CVUploadService {

    @Value("${file.storage-dir1}")
    private String cvFileStorageDir;

    private final UserDetailRepository userDetailRepository;

    private static final Set<String> SUPPORTED_FILE_TYPES = Set.of(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    private String generateUrl(HttpServletRequest request, String filename, String prefix) {
        return String.format("%s://%s:%d/%s/%s",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort(),
                prefix,
                filename);
    }

    private String readPdfContent(InputStream inputStream) {
        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            log.error("Error occurred while reading PDF content", e);
            throw new RuntimeException("Error occurred while reading PDF content", e);
        }
    }

    private String readDocxContent(InputStream inputStream) {
        try (XWPFDocument docx = new XWPFDocument(inputStream);
             XWPFWordExtractor extractor = new XWPFWordExtractor(docx)) {
            return extractor.getText();
        } catch (IOException e) {
            log.error("Error occurred while reading DOCX content", e);
            throw new RuntimeException("Error occurred while reading DOCX content", e);
        }
    }

    private String uploadFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (!SUPPORTED_FILE_TYPES.contains(contentType)) {
            log.warn("Unsupported file type: {}", contentType);
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, contentType + " not allowed!!");
        }

        try {
            Path fileStoragePath = Path.of("cv_uploads");
            if (!Files.exists(fileStoragePath)) {
                Files.createDirectories(fileStoragePath);
            }
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
            String fileName = UUID.randomUUID() + fileExtension;

            Files.copy(file.getInputStream(), fileStoragePath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            log.info("File uploaded successfully: {}", fileName);
            return fileName;
        } catch (IOException ex) {
            log.error("Failed to store file", ex);
            throw new RuntimeException("Failed to store file", ex);
        }
    }

    @Override
    public List<String> getAllFileNames() {
        try {
            return Files.list(Path.of("cv_uploads"))
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error occurred while getting all file names", e);
            throw new RuntimeException("Error occurred while getting all file names", e);
        }
    }

    @Override
    public ResponseEntity<Resource> serveFile(String filename, HttpServletRequest request) {
        return null;
    }


    @Override
    public CVUploadResponse uploadSingleFile(MultipartFile file, HttpServletRequest request) throws IOException {
        String filename = uploadFile(file);
        String fullImageUrl = generateUrl(request, filename, "images");
        String content = extractFileContent(file);

        List<String> keywords = Arrays.asList("First Name", "Last Name", "Email", "Telephone", "Nationality", "Marital Status", "Height", "Health Status", "Place of Birth", "Languages", "Education", "Educational Qualifications", "Experience", "Skills", "Skills & Abilities", "Projects", "References", "Interests", "Achievements", "Date of Birth", "Religion", "Sex", "Gender");

        Map<String, String> resumeSections = extractInformation(content, keywords);
        logResumeSections(resumeSections);

        UserDetail userDetail = mapToUserDetail(resumeSections);
        userDetailRepository.save(userDetail);

        return new CVUploadResponse(
                generateUrl(request, filename, "api/v1/cv/download"),
                file.getContentType(),
                (float) file.getSize() / 1024, // in KB
                filename,
                fullImageUrl,
                content
        );
    }

    private String extractFileContent(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        try (InputStream inputStream = file.getInputStream()) {
            if ("application/pdf".equals(contentType)) {
                return readPdfContent(inputStream);
            } else if ("application/msword".equals(contentType) || "application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)) {
                return readDocxContent(inputStream);
            } else {
                throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, contentType + " not allowed!!");
            }
        }
    }

    private void logResumeSections(Map<String, String> resumeSections) {
        resumeSections.forEach((key, value) -> log.info("{}: {}", key, value));
    }

    private UserDetail mapToUserDetail(Map<String, String> resumeSections) {
        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName(resumeSections.get("First Name"));
        userDetail.setLastName(resumeSections.get("Last Name"));
        userDetail.setEmail(resumeSections.get("Email"));
        userDetail.setGender(resumeSections.get("Sex/Gender"));
        userDetail.setTelephone(resumeSections.get("Telephone"));
        userDetail.setNationality(resumeSections.get("Nationality"));

        // Parse each section of the resume into a key-value pair
        Map<String, Object> languages = parseResumeSection(resumeSections.get("Languages"));
        Map<String, Object> interests = parseResumeSection(resumeSections.get("Interests"));
        Map<String, Object> skills = parseResumeSection(resumeSections.get("Skills"));

        // Set the parsed sections in the userDetail object
        userDetail.setLanguages(languages);
        userDetail.setInterests(interests);
        userDetail.setSkills(skills);

        return userDetail;
    }

    private Map<String, Object> parseResumeSection(String section) {
        Map<String, Object> sectionMap = new HashMap<>();
        String[] lines = section.split("\n");
        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length >= 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                sectionMap.put(key, value);
            }
        }
        return sectionMap;
    }

    private Map<String, Object> parseJson(String jsonString, ObjectMapper mapper) {
        if (jsonString == null) {
            return Collections.emptyMap();
        }
        try {
            // Parse the JSON string into a Map
            Map<String, Object> parsedMap = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});

            // Create a new Map with the default key
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("value", parsedMap);

            return resultMap;
        } catch (JsonProcessingException e) {
            log.warn("Invalid JSON format: {}", jsonString);
            return Collections.emptyMap();
        }
    }

    public static Map<String, String> extractInformation(String content, List<String> keywords) {
        Map<String, String> resumeSections = new LinkedHashMap<>();

        keywords.sort(Comparator.comparingInt(String::length).reversed());
        String patternString = String.join("|", keywords.stream().map(Pattern::quote).collect(Collectors.toList()));
        Pattern pattern = Pattern.compile("(?i)\\b(" + patternString + ")\\b");
        Matcher matcher = pattern.matcher(content);

        String lastKeyword = null;
        int lastMatchEnd = 0;
        while (matcher.find()) {
            if (lastKeyword != null) {
                String sectionContent = content.substring(lastMatchEnd, matcher.start()).trim();
                resumeSections.put(lastKeyword, cleanContent(sectionContent));
            }
            lastKeyword = matcher.group(1);
            lastMatchEnd = matcher.end();
        }

        if (lastKeyword != null && lastMatchEnd < content.length()) {
            String lastSectionContent = content.substring(lastMatchEnd).trim();
            resumeSections.put(lastKeyword, cleanContent(lastSectionContent));
        }

        extractAdditionalInfo(content, resumeSections);
        return resumeSections;
    }

    private static void extractAdditionalInfo(String content, Map<String, String> resumeSections) {
        extractAdditionalInformation(content, resumeSections, "Email", "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b");
        extractAdditionalInformation(content, resumeSections, "Telephone", "\\+?[0-9. ()-]{7,}");
        extractAdditionalInformation(content, resumeSections, "First Name", "(?i)\\b(?:first\\s+name|given\\s+name):?\\s*(\\b[a-zA-Z]+\\b)");
        extractAdditionalInformation(content, resumeSections, "Last Name", "(?i)\\b(?:last\\s+name|surname):?\\s*(\\b[a-zA-Z]+\\b)");
        extractLanguagesInformation(content, resumeSections, "Languages");
        extractSkillsInformation(content, resumeSections, "Skills");
        extractAdditionalInformation(content, resumeSections, "Date of Birth", "(?i)\\b(?:date\\s+of\\s+birth|dob):?\\s*([0-9]{2}/[0-9]{2}/[0-9]{4})");
        extractAdditionalInformation(content, resumeSections, "Height", "(?i)\\b(height):?\\s*([0-9]+\\s*(cm|in|ft))");
        extractAdditionalInformation(content, resumeSections, "Health Status", "(?i)\\b(health\\s+status):?\\s*(.*)");
        extractAdditionalInformation(content, resumeSections, "Religion", "(?i)\\b(religion):?\\s*(.*)");
        extractSexOrGenderInformation(content, resumeSections, "Sex/Gender");
        extractEducationInformation(content, resumeSections, "Education");
    }

    private static void extractEducationInformation(String content, Map<String, String> resumeSections, String key) {
        Pattern pattern = Pattern.compile("(?i)\\b(education(?:al)?\\s+(?:qualifications|background)|educational\\s+qualifications):?\\s*(.*)");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            String educationContent = matcher.group(2).trim();
            resumeSections.put(key, educationContent);
        }
    }

    private static void extractSkillsInformation(String content, Map<String, String> resumeSections, String key) {
        Pattern pattern = Pattern.compile("(?i)\\b(skills\\s*(&|and)\\s*abilities|skills):?\\s*(.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            String skillsContent = matcher.group(3).trim();
            resumeSections.put(key, skillsContent);
        }
    }

    private static void extractAdditionalInformation(String content, Map<String, String> resumeSections, String key, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            try {
                resumeSections.put(key, matcher.group(1).trim());
            } catch (IndexOutOfBoundsException e) {
                resumeSections.put(key, matcher.group().trim());
            }
        }
    }

    private static void extractLanguagesInformation(String content, Map<String, String> resumeSections, String key) {
        Pattern pattern = Pattern.compile("(?i)\\b(languages|language):?\\s*(.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            String languagesContent = matcher.group(2).trim();
            Map<String, String> languages = parseLanguages(languagesContent);
            String languagesFormatted = languages.entrySet().stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue())
                    .collect(Collectors.joining(", "));
            resumeSections.put(key, languagesFormatted);
        }
    }

    private static Map<String, String> parseLanguages(String languagesContent) {
        Map<String, String> languages = new LinkedHashMap<>();
        String[] languageEntries = languagesContent.split("(?<!\\w)(?=[A-Z][a-z]*:)");

        for (String entry : languageEntries) {
            String[] parts = entry.split(":", 2);
            if (parts.length == 2) {
                String language = parts[0].trim();
                String proficiency = parts[1].trim();
                languages.put(language, proficiency);
            }
        }
        return languages;
    }

    private static void extractSexOrGenderInformation(String content, Map<String, String> resumeSections, String key) {
        Pattern pattern = Pattern.compile("(?i)\\b(sex|gender):?\\s*(.*)");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            String sexOrGender = matcher.group(2).trim();
            resumeSections.put(key, sexOrGender);
        }
    }

    private static String cleanContent(String content) {
        return content.replaceAll("\\s+", " ").trim();
    }
}
