package com.example.alumni.feature.fogot_password.dto;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {
}
