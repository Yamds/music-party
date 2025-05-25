package fun.yamds.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yamds.mapper.BiliMapper;
import fun.yamds.pojo.BiliPojo;
import fun.yamds.pojo.Result;
import fun.yamds.service.BiliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class BiliServiceImpl extends ServiceImpl<BaseMapper<BiliPojo>, BiliPojo> implements BiliService {
    @Autowired
    private BiliMapper biliMapper;

    // 给b站发送请求
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Result saveData(BiliPojo bili) {
        System.out.println(bili.getId());
        System.out.println(bili.getSessdata());
        bili.setId(114514);
        if(bili.getSessdata() != null && !bili.getSessdata().isEmpty()) {
            int rows = biliMapper.updateById(bili);
            if(rows > 0) {
                return Result.ok().msg("Bilibili SESSDATA更新成功！");
            } else {
                return Result.error().msg("Bilibili SESSDATA更新失败...");
            }
        } else {
            return Result.error().msg("SESSDATA项为空");
        }
    }

    @Override
    public Result getData() {
        BiliPojo biliPojo = new BiliPojo();
        String BiliSESSDATA = biliMapper.selectById(114514).getSessdata();
        if(BiliSESSDATA != null && !BiliSESSDATA.isEmpty()) {
            biliPojo.setSessdata(BiliSESSDATA);
            HashMap<String, Object> map = new HashMap();
            map.put("bili_config", biliPojo);
            return Result.ok().msg("SESSDATA获取成功！").data(map);
        }
        return Result.error().msg("SESSDATA为空或不存在...");
    }

    @Override
    public Result getQrCode() {
        try {
            // 目标API地址
            String request_url = "https://passport.bilibili.com/x/passport-login/web/qrcode/generate";

            // 发送GET请求，通过fastjson 将string转为json对象 类似map 可以用get键拿到值
            String response_str = restTemplate.getForObject(request_url, String.class);
            JSONObject response_json = JSON.parseObject(response_str);

            // 提取响应中的data字段
            JSONObject data = response_json.getJSONObject("data");
            if (data == null || data.isEmpty()) {
                return Result.error().msg("B站接口返回数据异常...");
            }

            // 提取二维码URL和密钥
            String qrcodeUrl = (String) data.get("url");
            String qrcodeKey = (String) data.get("qrcode_key");

            // 包装返回结果
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("qrcode_url", qrcodeUrl);
            resultMap.put("qrcode_key", qrcodeKey);

            return Result.ok().msg("二维码获取成功").data(resultMap);


        } catch (RestClientException e) {
            // 处理网络或解析异常
            return Result.error().msg("请求B站接口失败: " + e.getMessage());
        }
    }

    @Override
    public Result qrCodeLogin(String qrcode_key) {
        // System.out.println(qrcode_key);
        String request_url = "https://passport.bilibili.com/x/passport-login/web/qrcode/poll?qrcode_key=" + qrcode_key;
        String response_str = restTemplate.getForObject(request_url, String.class, qrcode_key);
        JSONObject response_json = JSON.parseObject(response_str);

        JSONObject data = response_json.getJSONObject("data");
        if(data == null || data.isEmpty()) {
            return Result.error().msg("B站接口返回数据异常...");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", data);
        return Result.ok().data(map).msg("获取成功");
    }
}
