package com.example.alumni.feature.fogot_password.dto;

import lombok.Builder;

@Builder
public record ChangePassword(String password,String repeatPassword) {
}
