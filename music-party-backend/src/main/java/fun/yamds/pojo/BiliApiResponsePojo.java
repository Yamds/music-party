package fun.yamds.pojo;

import lombok.Data;

import java.util.List;

// 感谢https://github.com/SocialSisterYi/bilibili-API-collect
@Data
public class BiliApiResponsePojo<T> {
    private int code;    // 响应码（b站接口一般来说 0代表成功）
    private String message; // 提示信息
    private int ttl;    // 1
    private T data;       // 实际数据（根据接口动态变化）

    // 下面的接口统统指的是data里的内容
    @Data
    public static class qrcodeGenerate {
        private String url;
        private String qrcode_key;
    }
    @Data
    public static class qrcodeSuccess {
        private String url;
        private String refresh_token;
        private String timestamp;
        private int code;
        private String message;
    }

    @Data
    public static class buvid3 {
        private String buvid;
    }

    @Data
    public static class searchTypeUser {
        private List<biliUser> result;

        @Data
        public static class biliUser {
            private long mid;
            private String uname;
            private String upic;
        }
    }

}
