package fun.yamds;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private RestTemplate restTemplate;


    @Test
    void contextLoads() {
    }

    @Test
    void biliGetQrCode() {
        String url = "https://passport.bilibili.com/x/passport-login/web/qrcode/generate";

        // 发送GET请求，直接解析响应为Map
        String response = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSON.parseObject(response);
        System.out.println(jsonObject.get("data"));
    }
}
