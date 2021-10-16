package com.api.youtube.sample.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "extension.youtube")
public class YoutubeConfig {

    // youtubeのチャンネルID
    private String channelId;
    // Youtube Data Api の APIキー
    private String key;
}
