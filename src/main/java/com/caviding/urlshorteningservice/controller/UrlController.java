package com.caviding.urlshorteningservice.controller;

import com.caviding.urlshorteningservice.dto.CreateUrlRequest;
import com.caviding.urlshorteningservice.dto.UrlResponse;
import com.caviding.urlshorteningservice.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/urls")
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UrlResponse createUrl(@RequestBody @Valid CreateUrlRequest request){
        return urlService.createUrl(request);
    }

    @GetMapping("/details/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UrlResponse> getUrlById(@PathVariable Long id){
        return ResponseEntity.ok(urlService.getUrlById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UrlResponse>> getAllUrls(){
        return ResponseEntity.ok(urlService.getAllUrls());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUrl(@PathVariable Long id){
        urlService.deleteUrl(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UrlResponse> updateUrl(@PathVariable Long id, @RequestBody @Valid CreateUrlRequest request){
        return ResponseEntity.ok(urlService.updateUrl(id, request));
    }

}
