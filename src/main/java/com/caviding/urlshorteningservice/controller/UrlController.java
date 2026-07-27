package com.caviding.urlshorteningservice.controller;

import com.caviding.urlshorteningservice.dto.CreateUrlRequest;
import com.caviding.urlshorteningservice.dto.UrlResponse;
import com.caviding.urlshorteningservice.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/urls")
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public UrlResponse createUrl(@RequestBody @Valid CreateUrlRequest request){
        return urlService.createUrl(request);
    }



}
