package com.caviding.urlshorteningservice.service;

import com.caviding.urlshorteningservice.dto.CreateUrlRequest;
import com.caviding.urlshorteningservice.dto.UrlResponse;
import com.caviding.urlshorteningservice.entity.Url;
import com.caviding.urlshorteningservice.repository.UrlRepository;
import com.caviding.urlshorteningservice.util.ShortCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlResponse createUrl(CreateUrlRequest request) {

        String shortCode = ShortCodeGenerator.generate();

        while(urlRepository.existsByShortCode(shortCode)){
            shortCode = ShortCodeGenerator.generate();
        }

        Url url = new Url();
        url.setCreatedAt(LocalDateTime.now());
        url.setUpdatedAt(LocalDateTime.now());
        url.setOriginalUrl(request.getOriginalUrl());
        url.setShortCode(shortCode);
        url.setClickCount(0L);

        Url savedUrl = urlRepository.save(url);

        UrlResponse response = new UrlResponse();
        response.setId(savedUrl.getId());
        response.setOriginalUrl(savedUrl.getOriginalUrl());
        response.setShortCode(savedUrl.getShortCode());
        response.setShortUrl("http://localhost:8080/" + savedUrl.getShortCode());
        response.setClickCount(savedUrl.getClickCount());

        return response;
    }

    public String redirect(String shortCode){
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short code not found"));
        url.setClickCount(url.getClickCount() + 1);
        url.setUpdatedAt(LocalDateTime.now());
        urlRepository.save(url);

        return url.getOriginalUrl();
    }

    public UrlResponse getUrlById(Long id){
        Url url = urlRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Url not found"));

        return UrlResponse.builder()
                .id(url.getId())
                .originalUrl(url.getOriginalUrl())
                .shortCode(url.getShortCode())
                .shortUrl("http://localhost:8080/" + url.getShortCode())
                .clickCount(url.getClickCount())
                .build();
    }

    public List<UrlResponse> getAllUrls() {
        List<Url> urls = urlRepository.findAll();
        List<UrlResponse> urlResponse = new ArrayList<>();
        for (Url u : urls) {
            UrlResponse response = UrlResponse.builder()
                    .id(u.getId())
                    .originalUrl(u.getOriginalUrl())
                    .shortCode(u.getShortCode())
                    .shortUrl("http://localhost:8080/" + u.getShortCode())
                    .clickCount(u.getClickCount())
                    .build();
            urlResponse.add(response);
        }
        return urlResponse;
    }


}
