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

/**
 * Youtubeから動画一覧を取得して Json形式で返却するコントローラー
 * TODO 出来れば レスポンスで返したJsonをViewに渡して画面表示させたい
 */
@Slf4j
@RestController
@RequestMapping(value = "youtube/v1/search/")
@RequiredArgsConstructor
public class YoutubeController {

    private final YouTube youtubeDataApi;
    private final YoutubeConfig youtubeConfig;

    @GetMapping(value = "miko/movies")
    public ResponseEntity<?> getMikoMovies() {

        try {
            // 検索の実行 動画一覧をJsonで取得できる
            SearchListResponse response = this.createSearchApi(youtubeConfig.getMikoChId()).execute();
            return new ResponseEntity<>(response.toPrettyString(), this.createHeader(), HttpStatus.OK);
        } catch (Exception exception) {
            // エラーレスポンスの作成
            log.error("Youtubeへのリクエスト送信に失敗しました。", exception);
            return this.youtubeErrorResponse("Miko Channel Get Movies Failed");
        }
    }

    @GetMapping(value = "suisei/movies")
    public ResponseEntity<?> getSuiseiMovies() {

        try {
            SearchListResponse response = this.createSearchApi(youtubeConfig.getSuiseiChId()).execute();
            return new ResponseEntity<>(response.toPrettyString(), this.createHeader(), HttpStatus.OK);
        } catch (Exception exception) {
            // エラーレスポンスの作成
            log.error("Youtubeへのリクエスト送信に失敗しました。", exception);
            return this.youtubeErrorResponse("Suisei Channel Get Movies Failed");
        }
    }

    @GetMapping(value = "yashikizu/movies")
    public ResponseEntity<?> getYashikizuMovies() {

        try {
            SearchListResponse response = this.createSearchApi(youtubeConfig.getYashikizuChId()).execute();
            return new ResponseEntity<>(response.toPrettyString(), this.createHeader(), HttpStatus.OK);
        } catch (Exception exception) {
            // エラーレスポンスの作成
            log.error("Youtubeへのリクエスト送信に失敗しました。", exception);
            return this.youtubeErrorResponse("Yashikizu Channel Get Movies Failed");
        }
    }

    /**
     * ヘッダの作成
     */
    private HttpHeaders createHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    /**
     * エラーレスポンスの作成
     *
     * @param errorMsg : エラーメッセージ
     * @return エラーレスポンス
     */
    private ResponseEntity<?> youtubeErrorResponse(String errorMsg) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", "Youtube Search Result Error");
        errorDetails.put("message", errorMsg);
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorDetails, this.createHeader(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Youtubeの検索条件の設定
     *
     * @param channelId : 検索先のチャンネルID
     * @return : 検索用クラス
     * @throws Exception : 設定時例外
     */
    private YouTube.Search.List createSearchApi(String channelId) throws Exception {

        // 検索対象のチャンネルや IDの指定
        YouTube.Search.List search = youtubeDataApi.search().list("id,snippet");
        search.setChannelId(channelId);
        search.setKey(youtubeConfig.getKey());
        search.setQ("Minecraft"); // 検索条件 マインクラフトの動画でフィルタ
        search.setType("video");  // 検索対象のTypeを設定。他にchannelとplaylistが設定できる videoで動画
        search.setMaxResults(20L); // 取得するヒット数の最大値を設定
        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
        search.setOrder("date"); // 最新の投稿から優先して取得できるように設定
        return search;
    }
}
