package com.api.youtube.sample.config;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "extension.youtube")
public class YoutubeConfig {

    // Youtube Data Api の APIキー
    private String key;
    // みこちのチャンネルID
    private String mikoChId;
    // すいせいのチャンネルID
    private String suiseiChId;
    // やしきずのチャンネルID
    private String yashikizuChId;

    /**
     * Youtubeとの接続セットアップ
     */
    @Bean(name = "youtubeDataApi")
    public YouTube youtubeDataAPi() {
        return new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                request -> {
                }).setApplicationName("youtube-search-api").build();
    }
}
