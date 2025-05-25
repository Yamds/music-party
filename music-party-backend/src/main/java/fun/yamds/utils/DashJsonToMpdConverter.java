
package fun.yamds.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

// 源代码：https://github.com/DaydreamCafe/Bilibili-API-to-MPD
// 由AI转换而来

// Dash 视频流结构体
class VideoDashVideoStream {
    public int id;
    public String baseUrl;
    public String[] backupUrl;
    public long bandwidth;
    public String mimeType;
    public String codecs;
    public long width;
    public long height;
    public String frameRate;
    public String sar;
    public int startWithSap;
    public SegmentBase segmentBase;
    public int codecid;

    static class SegmentBase {
        public String Initialization;
        public String indexRange;
    }
}

// Dash 音频流结构体
class VideoDashAudioStream {
    public int id;
    public String baseUrl;
    public String[] backupUrl;
    public long bandwidth;
    public String mimeType;
    public String codecs;
    public int width;
    public int height;
    public String frameRate;
    public String sar;
    public int startWithSap;
    public SegmentBase segmentBase;
    public int codecid;

    static class SegmentBase {
        public String Initialization;
        public String indexRange;
    }
}

// Dash 结构体
class VideoDash {
    public int duration;
    public double minBufferTime;
    public VideoDashVideoStream[] video;
    public VideoDashAudioStream[] audio;
    public Object dolby;
    public Object flac;
}

// Data 结构体
class VideoData {
    public String from;
    public String result;
    public String message;
    public int quality;
    public String format;
    public int timelength;
    public String acceptFormat;
    public String[] acceptDescription;
    public int[] acceptQuality;
    public int videoCodecid;
    public String seekParam;
    public String seekType;
    public VideoDash dash;
    public Object[] supportFormats;
    public Object highFormat;
    public int lastPlayTime;
    public int lastPlayCid;
}

// B 站 API 返回结果结构体
class VideoStreamResponse {
    public int code;
    public String message;
    public int ttl;
    public VideoData data;
}

// DashJsonToMpdConverter 类
public class DashJsonToMpdConverter {

    public String convertJsonToMpd(String json) throws IOException {
        // 解析 JSON 为视频流结构体
        ObjectMapper mapper = new ObjectMapper();
        VideoStreamResponse videoStream = mapper.readValue(json, VideoStreamResponse.class);

        // 构造 MPD 字符串
        StringBuilder mpdStr = new StringBuilder();
        mpdStr.append("<MPD xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"urn:mpeg:dash:schema:mpd:2011\" xsi:schemaLocation=\"urn:mpeg:dash:schema:mpd:2011 http://standards.iso.org/ittf/PubliclyAvailableStandards/MPEG-DASH_schema_files/DASH-MPD.xsd\" type=\"static\" mediaPresentationDuration=\"PT").append(videoStream.data.dash.duration).append("S\" minBufferTime=\"PT").append(videoStream.data.dash.minBufferTime).append("S\">");
        mpdStr.append("<Period>");

        // 添加视频 AdaptationSet
        mpdStr.append("<AdaptationSet mimeType=\"video/mp4\" codecs=\"avc1.640028\" segmentAlignment=\"true\" startWithSAP=\"1\" subsegmentAlignment=\"true\" subsegmentStartsWithSAP=\"1\">");
        for (VideoDashVideoStream stream : videoStream.data.dash.video) {
            mpdStr.append("<Representation id=\"").append(stream.id).append("\" bandwidth=\"").append(stream.bandwidth).append("\" width=\"").append(stream.width).append("\" height=\"").append(stream.height).append("\" frameRate=\"").append(stream.frameRate).append("\" codecs=\"").append(stream.codecs).append("\">");
            mpdStr.append("<BaseURL>").append(stream.baseUrl).append("</BaseURL>");
            mpdStr.append("<SegmentBase indexRange=\"").append(stream.segmentBase.indexRange).append("\" initialization=\"").append(stream.segmentBase.Initialization).append("\"/>");
            mpdStr.append("</Representation>");
        }
        mpdStr.append("</AdaptationSet>");

        // 添加音频 AdaptationSet
        mpdStr.append("<AdaptationSet mimeType=\"audio/mp4\" codecs=\"mp4a.40.2\" segmentAlignment=\"true\" startWithSAP=\"1\" subsegmentAlignment=\"true\" subsegmentStartsWithSAP=\"1\">");
        for (VideoDashAudioStream stream : videoStream.data.dash.audio) {
            mpdStr.append("<Representation id=\"").append(stream.id).append("\" bandwidth=\"").append(stream.bandwidth).append("\" codecs=\"").append(stream.codecs).append("\">");
            mpdStr.append("<BaseURL>").append(stream.baseUrl).append("</BaseURL>");
            mpdStr.append("<SegmentBase indexRange=\"").append(stream.segmentBase.indexRange).append("\" initialization=\"").append(stream.segmentBase.Initialization).append("\"/>");
            mpdStr.append("</Representation>");
        }
        mpdStr.append("</AdaptationSet>");

        mpdStr.append("</Period>");
        mpdStr.append("</MPD>");

        return mpdStr.toString();
    }

    public static void main(String[] args) {
        DashJsonToMpdConverter converter = new DashJsonToMpdConverter();
        String json = "{\"code\":0,\"message\":\"0\",\"ttl\":1,\"data\":{\"from\":\"local\",\"result\":\"suee\",\"message\":\"\",\"quality\":127,\"format\":\"dash\",\"timelength\":123456,\"accept_format\":\"flv,dash\",\"accept_description\":[\"高清\",\"超清\"],\"accept_quality\":[80,127],\"video_codecid\":12,\"seek_param\":\"start\",\"seek_type\":\"offset\",\"dash\":{\"duration\":123,\"minBufferTime\":1.5,\"video\":[{\"id\":1,\"baseUrl\":\"http://example.com/video1.mp4\",\"backupUrl\":[],\"bandwidth\":1000000,\"mimeType\":\"video/mp4\",\"codecs\":\"avc1.640028\",\"width\":1920,\"height\":1080,\"frameRate\":\"25\",\"sar\":\"1:1\",\"startWithSap\":1,\"SegmentBase\":{\"Initialization\":\"init.mp4\",\"indexRange\":\"123-456\"},\"codecid\":1}],\"audio\":[{\"id\":2,\"baseUrl\":\"http://example.com/audio1.mp4\",\"backupUrl\":[],\"bandwidth\":128000,\"mimeType\":\"audio/mp4\",\"codecs\":\"mp4a.40.2\",\"width\":0,\"height\":0,\"frameRate\":\"\",\"sar\":\"\",\"startWithSap\":1,\"SegmentBase\":{\"Initialization\":\"init.aac\",\"indexRange\":\"789-101\"},\"codecid\":2}],\"dolby\":null,\"flac\":null},\"support_formats\":[],\"high_format\":null,\"last_play_time\":0,\"last_play_cid\":0}}";
        try {
            String mpd = converter.convertJsonToMpd(json);
            System.out.println(mpd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}