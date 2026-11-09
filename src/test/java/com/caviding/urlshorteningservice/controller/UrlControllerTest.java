package com.caviding.urlshorteningservice.controller;

import com.caviding.urlshorteningservice.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@RequiredArgsConstructor
@WebMvcTest(UrlController.class)
public class UrlControllerTest {

    private final MockMvc mockMvc;

    @MockitoBean
    private UrlService urlService;






}
