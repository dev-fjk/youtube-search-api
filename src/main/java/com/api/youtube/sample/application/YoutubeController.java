package com.api.youtube.sample.application;

import com.api.youtube.sample.config.YoutubeConfig;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import java.util.HashMap;
import java.util.Map;
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
@RequestMapping(value = "youtube/api/v1/")
@RequiredArgsConstructor
public class YoutubeController {

    private final YouTube youtubeDataApi;
    private final YoutubeConfig youtubeConfig;

    @GetMapping(value = "get/movies")
    public ResponseEntity<?> getMovies() {
        log.info("Youtube Controller Get Http Request");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        try {
            // TODO 出来れば レスポンスで返したJsonをViewに渡して画面表示させたい
            // 検索の実行 動画一覧をJsonで取得できる
            SearchListResponse response = this.createSearchApi().execute();
            return new ResponseEntity<>(response.toPrettyString(), httpHeaders, HttpStatus.OK);
        } catch (Exception exception) {
            // エラーレスポンスの作成
            log.error("Youtubeへのリクエスト送信に失敗しました。", exception);
            Map<String, Object> errorDetails = new HashMap<>();
            errorDetails.put("error", "Youtube Connect Error");
            errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorDetails, httpHeaders, HttpStatus.OK);
        }
    }

    /**
     * Youtubeの検索条件の設定
     *
     * @return : 検索用クラス
     * @throws Exception : 設定時例外
     */
    private YouTube.Search.List createSearchApi() throws Exception {

        YouTube.Search.List search = youtubeDataApi.search().list("id,snippet");
        search.setChannelId(youtubeConfig.getChannelId());
        search.setKey(youtubeConfig.getKey());
        search.setQ("Minecraft"); // みこちのマイクラ動画でフィルタする
        search.setType("video");  // 検索対象のTypeを設定。他にchannelとplaylistが設定できる
        search.setMaxResults(10L); // 取得するヒット数の最大値を設定
        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
        return search;
    }
}
