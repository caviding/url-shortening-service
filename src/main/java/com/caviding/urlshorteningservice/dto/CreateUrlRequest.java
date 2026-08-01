package com.caviding.urlshorteningservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
public class CreateUrlRequest {

    @NotBlank
    @URL
    private String originalUrl;
}
