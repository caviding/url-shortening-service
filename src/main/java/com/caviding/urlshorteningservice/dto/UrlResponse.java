package com.caviding.urlshorteningservice.dto;

import lombok.Data;

@Data
public class UrlResponse {

    private Long id;

    private String originalUrl;

    private String shortCode;

    private String shortUrl;

    private Long clickCount;
}
