package com.example.alumni.adviser;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class BaseResponseError {
    private BaseError error;
}
