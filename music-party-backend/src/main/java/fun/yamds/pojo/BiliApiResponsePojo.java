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
    public static class QrcodeGenerate {
        private String url;
        private String qrcode_key;
    }

    @Data
    public static class QrcodeSuccess {
        private String url;
        private String refresh_token;
        private String timestamp;
        private int code;
        private String message;
    }

    @Data
    public static class Buvid3 {
        private String buvid;
    }

    @Data
    public static class SearchTypeUser {
        private List<BiliUser> result;

        @Data
        public static class BiliUser {
            private long mid;
            private String uname;
            private String upic;
        }
    }

    @Data
    public static class FolderList {
        private int count;
        private List<fList> list;

        @Data
        public static class fList {
            private long id;
            private long fid;
            private long mid;
            private String title;
            private int media_count;
        }
    }

    @Data
    public static class FolderInfo {
        private Info info;
        private List<Medias> medias;
        private Boolean has_more;

        @Data
        public static class Info {
            private long id;
            private long fid;
            private long mid;
            private String title;
            private String cover;
            private Upper upper;

            @Data
            public static class Upper{
                private long mid;
                private String name;
            }
        }

        @Data
        public static class Medias {
            private long id;
            private String bvid;
            private long type;
            private String title;
            private String cover;
            private String intro;
            private long page;
            private long duration;
            private Upper upper;

            @Data
            public static class Upper {
                private long mid;
                private String name;
                private String face;
            }
        }
    }

    @Data
    public static class VideoInfo {
        private String bvid;
        private long avid;
        private int videos;
        private String pic;
        private String title;
        private String desc;
        private long duration;
        private Owner owner;
        private long cid;

        @Data
        public static class Owner {
            private long mid;
            private String name;
            private String face;
        }
    }

}
