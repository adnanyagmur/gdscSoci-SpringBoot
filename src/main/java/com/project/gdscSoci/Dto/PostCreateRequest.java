package com.project.gdscSoci.Dto;

import lombok.Data;

@Data
public class PostCreateRequest {

    Long id;
    String text;
    String title;
    Long userId;
}
