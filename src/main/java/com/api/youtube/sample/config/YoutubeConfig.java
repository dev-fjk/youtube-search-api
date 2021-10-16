package com.api.youtube.sample.config;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
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

    /**
     * Global instance of the HTTP transport.
     */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    // youtubeのチャンネルID
    private String channelId;
    // Youtube Data Api の APIキー
    private String key;

    /**
     * Youtubeとの接続セットアップ
     *
     * @return : Youtube
     */
    @Bean(name = "youtubeDataApi")
    public YouTube youtubeDataAPi() {
        return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                request -> {
                }).setApplicationName("youtube-search-api").build();
    }
}
