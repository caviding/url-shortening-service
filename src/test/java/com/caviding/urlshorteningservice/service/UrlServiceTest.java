package com.caviding.urlshorteningservice.service;

import com.caviding.urlshorteningservice.dto.CreateUrlRequest;
import com.caviding.urlshorteningservice.dto.UrlResponse;
import com.caviding.urlshorteningservice.entity.Url;
import com.caviding.urlshorteningservice.repository.UrlRepository;
import com.caviding.urlshorteningservice.util.ShortCodeGenerator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private ShortCodeGenerator shortCodeGenerator;

    private Url testUrl;
    private CreateUrlRequest testRequest;


    @BeforeEach
    void setUp() {

        testUrl = Url.builder()
                .id(1L)
                .originalUrl("https://www.google.com")
                .shortCode("123456")
                .clickCount(0L)
                .createdAt(null)
                .updatedAt(null)
                .build();

        testRequest = CreateUrlRequest.builder()
                .originalUrl("https://www.google.com")
                .build();
    }

    @Test
    void should_createUrl_returnUrlResponse(){

        when(urlRepository.existsByShortCode(anyString())).thenReturn(false);
        when(urlRepository.save(any(Url.class))).thenReturn(testUrl);
        when(shortCodeGenerator.generate()).thenReturn("123456");

        UrlResponse response = urlService.createUrl(testRequest);

        assertNotNull(response);
        assertEquals(testUrl.getId(), response.getId());
        assertEquals(testUrl.getOriginalUrl(), response.getOriginalUrl());
        assertEquals(testUrl.getShortCode(), response.getShortCode());
        assertEquals(testUrl.getClickCount(), response.getClickCount());
        assertNotNull(response.getShortUrl());

        verify(urlRepository, times(1)).existsByShortCode(anyString());
        verify(urlRepository, times(1)).save(any(Url.class));
    }



}
