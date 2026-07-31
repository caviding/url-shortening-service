package com.caviding.urlshorteningservice.service;

import com.caviding.urlshorteningservice.exception.UrlNotFoundException;
import com.caviding.urlshorteningservice.dto.CreateUrlRequest;
import com.caviding.urlshorteningservice.dto.UrlResponse;
import com.caviding.urlshorteningservice.entity.Url;
import com.caviding.urlshorteningservice.repository.UrlRepository;
import com.caviding.urlshorteningservice.util.ShortCodeGenerator;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final ShortCodeGenerator shortCodeGenerator;

    @Transactional
    public UrlResponse createUrl(CreateUrlRequest request) {

        String shortCode = shortCodeGenerator.generate();

        while(urlRepository.existsByShortCode(shortCode)){
            shortCode = shortCodeGenerator.generate();
        }

        Url url = Url.builder()
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .originalUrl(request.getOriginalUrl())
                .shortCode(shortCode)
                .clickCount(0L)
                .build();

        Url savedUrl = urlRepository.save(url);
        return mapToResponse(savedUrl);
    }

    @Transactional
    public String redirect(String shortCode){
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("Url code not found"));
        url.setClickCount(url.getClickCount() + 1);
        url.setUpdatedAt(LocalDateTime.now());
        urlRepository.save(url);

        return url.getOriginalUrl();
    }

    @Transactional(readOnly = true)
    public UrlResponse getUrlById(Long id){
        Url url = urlRepository.findById(id)
                .orElseThrow(() -> new UrlNotFoundException("Url not found"));
        return mapToResponse(url);
    }

    @Transactional(readOnly = true)
    public List<UrlResponse> getAllUrls() {
        return urlRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public void deleteUrl(Long id){
        Url url = urlRepository.findById(id)
                .orElseThrow(() -> new UrlNotFoundException("Url not found"));
        urlRepository.delete(url);
    }

    @Transactional
    public UrlResponse updateUrl(Long id, CreateUrlRequest request){
        Url url = urlRepository.findById(id)
                .orElseThrow(() -> new UrlNotFoundException("Url not found"));
        url.setOriginalUrl(request.getOriginalUrl());
        url.setUpdatedAt(LocalDateTime.now());
        urlRepository.save(url);
        return mapToResponse(url);
    }

    private UrlResponse mapToResponse(Url url) {
        return UrlResponse.builder()
                .id(url.getId())
                .originalUrl(url.getOriginalUrl())
                .shortCode(url.getShortCode())
                .shortUrl("http://localhost:8080/" + url.getShortCode())
                .clickCount(url.getClickCount())
                .build();
    }
}
