package com.caviding.urlshorteningservice.service;

import com.caviding.urlshorteningservice.dto.CreateUrlRequest;
import com.caviding.urlshorteningservice.dto.UrlResponse;
import com.caviding.urlshorteningservice.entity.Url;
import com.caviding.urlshorteningservice.exception.UrlNotFoundException;
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

import java.util.List;
import java.util.Optional;

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

    @Test
    void should_redirect_returnUrl(){
        when(urlRepository.findByShortCode(anyString())).thenReturn(java.util.Optional.of(testUrl));
        when(urlRepository.save(any(Url.class))).thenReturn(testUrl);

        String url = urlService.redirect("123456");

        assertNotNull(url);
        assertEquals(testUrl.getOriginalUrl(), url);

        verify(urlRepository, times(1)).findByShortCode(anyString());
        verify(urlRepository, times(1)).save(any(Url.class));
    }

    @Test
    void should_redirect_throwUrlNotFoundException(){
        when(urlRepository.findByShortCode(anyString())).thenReturn(Optional.empty());

        UrlNotFoundException exception = assertThrows(
                UrlNotFoundException.class,
                () -> urlService.redirect("123456"),
                "UrlNotFoundException should be thrown"
        );
        assertEquals("Url code not found", exception.getMessage());

        verify(urlRepository, times(1)).findByShortCode(anyString());
        verify(urlRepository, never()).save(any(Url.class));
    }

    @Test
    void should_getUrlById_returnUrlResponse(){
        when(urlRepository.findById(anyLong())).thenReturn(java.util.Optional.of(testUrl));

        UrlResponse response = urlService.getUrlById(1L);

        assertNotNull(response);
        assertEquals(testUrl.getId(), response.getId());
        assertEquals(testUrl.getOriginalUrl(), response.getOriginalUrl());
        assertEquals(testUrl.getShortCode(), response.getShortCode());
        assertEquals(testUrl.getClickCount(), response.getClickCount());
        assertNotNull(response.getShortUrl());

        verify(urlRepository, times(1)).findById(anyLong());
    }

    @Test
    void should_getUrlById_throwUrlNotFoundException(){
        when(urlRepository.findById(anyLong())).thenReturn(Optional.empty());

        UrlNotFoundException exception = assertThrows(
                UrlNotFoundException.class,
                () -> urlService.getUrlById(1L),
                "UrlNotFoundException should be thrown"
        );
        assertEquals("Url not found", exception.getMessage());

        verify(urlRepository, times(1)).findById(anyLong());
    }

    @Test
    void should_getAllUrls_returnUrlResponseList(){
        when(urlRepository.findAll()).thenReturn(java.util.List.of(testUrl));

        List<UrlResponse> response = urlService.getAllUrls();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(testUrl.getId(), response.get(0).getId());
        assertEquals(testUrl.getOriginalUrl(), response.get(0).getOriginalUrl());
        assertEquals(testUrl.getShortCode(), response.get(0).getShortCode());
        assertEquals(testUrl.getClickCount(), response.get(0).getClickCount());
        assertNotNull(response.get(0).getShortUrl());

        verify(urlRepository, times(1)).findAll();
    }

    @Test
    void should_deleteUrl() {
        when(urlRepository.findById(anyLong())).thenReturn(java.util.Optional.of(testUrl));
        doNothing().when(urlRepository).delete(any(Url.class));

        urlService.deleteUrl(1L);

        verify(urlRepository, times(1)).findById(anyLong());
        verify(urlRepository, times(1)).delete(any(Url.class));
    }

    @Test
    void should_deleteUrl_throwUrlNotFoundException() {
        when(urlRepository.findById(anyLong())).thenReturn(Optional.empty());

        UrlNotFoundException exception = assertThrows(
                UrlNotFoundException.class,
                () -> urlService.deleteUrl(1L),
                "UrlNotFoundException should be thrown"
        );
        assertEquals("Url not found", exception.getMessage());

        verify(urlRepository, times(1)).findById(anyLong());
        verify(urlRepository, never()).delete(any(Url.class));
    }

    @Test
    void should_updateUrl_returnUrlResponse() {
        when(urlRepository.findById(anyLong())).thenReturn(java.util.Optional.of(testUrl));
        when(urlRepository.save(any(Url.class))).thenReturn(testUrl);

        UrlResponse response = urlService.updateUrl(1L, testRequest);
        assertNotNull(response);
        assertEquals(testUrl.getId(), response.getId());
        assertEquals(testUrl.getOriginalUrl(), response.getOriginalUrl());
        assertEquals(testUrl.getShortCode(), response.getShortCode());
        assertEquals(testUrl.getClickCount(), response.getClickCount());
        assertNotNull(response.getShortUrl());

        verify(urlRepository, times(1)).findById(anyLong());
        verify(urlRepository, times(1)).save(any(Url.class));
    }

    @Test
    void should_updateUrl_throwUrlNotFoundException() {
        when(urlRepository.findById(anyLong())).thenReturn(Optional.empty());

        UrlNotFoundException exception = assertThrows(
                UrlNotFoundException.class,
                () -> urlService.updateUrl(1L, testRequest),
                "UrlNotFoundException should be thrown"
        );

        assertEquals("Url not found", exception.getMessage());

        verify(urlRepository, times(1)).findById(anyLong());
        verify(urlRepository, never()).save(any(Url.class));
    }
}
