package fun.yamds.utils;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class HttpUtils {
    private final RestTemplate restTemplate;

    public HttpUtils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T get(String url, JsonObjParseUtils.ParameterizedTypeReference<? extends T> type) {
        try {
            String resp = restTemplate.getForObject(url, String.class);
            if (resp == null) {
                throw new RuntimeException("B站接口返回空响应");
            }
            return JsonObjParseUtils.parse(resp, type);
        } catch (Exception e) {
            throw new RuntimeException("请求失败", e);
        }
    }

    public <T> T getWithHeaders(String url, JsonObjParseUtils.ParameterizedTypeReference<T> type, Map<String, String> customHeaders) {
        try {
            // 1. 构建请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setAll(customHeaders); // 添加自定义请求头
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 2. 封装请求实体（无请求体）
            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            // 3. 发送请求（使用 exchange 方法）
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity, // 传入带有请求头的 HttpEntity
                    String.class
            );

            // 4. 解析响应
            return JsonObjParseUtils.parse(response.getBody(), type);
        } catch (Exception e) {
            throw new RuntimeException("请求失败", e);
        }
    }

}
