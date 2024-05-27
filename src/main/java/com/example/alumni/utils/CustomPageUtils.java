package com.example.alumni.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomPageUtils<T> {
    private String next;
    private String previous;
    private int total;
    private List<T> results;
}
