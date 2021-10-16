package com.api.youtube.sample.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(value = "youtube/v1/")
public class YoutubeController {

    /**
     * 画面の表示
     */
    @GetMapping(value = "top")
    public String topPage() {
        return "index";
    }
}
