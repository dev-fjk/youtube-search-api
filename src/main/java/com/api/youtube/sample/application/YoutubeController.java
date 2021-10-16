package com.api.youtube.sample.application;

import com.api.youtube.sample.config.YoutubeConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/")
@RequiredArgsConstructor
public class YoutubeController {

    private final YoutubeConfig youtubeConfig;

    @GetMapping(value = "get")
    public ResponseEntity<Integer> getMovies() {
        log.info("Youtube Controller Get Http Request");
        
        String id = youtubeConfig.getChannelId();
        String key = youtubeConfig.getKey();
        log.info("id : {} , key : {}", id, key);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(1, httpHeaders, HttpStatus.OK);
    }
}
