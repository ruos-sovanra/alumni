package com.example.alumni.adviser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@NoArgsConstructor
@Setter
@Getter
public class BaseError<T> {
   private  String code;
   private  T description;
}
