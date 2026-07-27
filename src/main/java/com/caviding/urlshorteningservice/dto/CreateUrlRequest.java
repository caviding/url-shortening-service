package com.caviding.urlshorteningservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateUrlRequest {

    @NotBlank
    @URL
    private String originalUrl;
}
