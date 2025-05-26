package fun.yamds.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yamds.mapper.BiliMapper;
import fun.yamds.pojo.BiliApiResponsePojo;
import fun.yamds.pojo.BiliCookiePojo;
import fun.yamds.pojo.Result;
import fun.yamds.service.BiliService;
import fun.yamds.utils.JsonObjParseUtils;
import fun.yamds.utils.HttpUtils;
import fun.yamds.utils.ObjMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BiliServiceImpl extends ServiceImpl<BaseMapper<BiliCookiePojo>, BiliCookiePojo> implements BiliService {
    @Autowired
    private BiliMapper biliMapper;

    // 封装一个请求
    @Autowired
    private HttpUtils httpUtils;

    @Override
    public Result saveCookie(BiliCookiePojo bili) {
        if(bili.getCookieName() != null && !bili.getCookieName().isEmpty()) {
            if(bili.getCookieContext() != null && !bili.getCookieContext().isEmpty()) {
                int rows = biliMapper.updateById(bili);
                if(rows > 0) {
                    return Result.ok().msg("Bilibili " + bili.getCookieName() + "更新成功！");
                } else {
                    return Result.error().msg("Bilibili " + bili.getCookieName() + "更新失败...");
                }
            } else
                return Result.error().msg("传参Cookie内容为空");
        } else
            return Result.error().msg("传参Cookie名字为空");
    }

    @Override
    public Result getCookie(BiliCookiePojo bili) {
        if(bili.getCookieName() != null && bili.getCookieContext() != null)
            return Result.error().msg("传参Cookie名字为空");

        BiliCookiePojo cookiePojo = biliMapper.selectById(bili.getCookieName());
        if(cookiePojo != null) {
            bili.setCookieContext(cookiePojo.getCookieContext());
            HashMap<String, Object> map = new HashMap<>();
            map.put("bili_config", bili);
            return Result.ok().msg(bili.getCookieName() + "获取成功！").data(map);
        } else {
            return Result.error().msg("不存在该name的Cookie");
        }
    }

    @Override
    public Result getAllCookie() {
        List<BiliCookiePojo> biliCookiePojos = biliMapper.selectList(null);
        HashMap<String, Object> map = new HashMap<>();
        StringBuilder cookie = new StringBuilder();
        for(BiliCookiePojo biliCookiePojo : biliCookiePojos)
            cookie.append(biliCookiePojo.getCookieName()).append("=").append(biliCookiePojo.getCookieContext()).append("; ");
        if(!cookie.isEmpty()) {
            map.put("cookie", cookie.toString());
            return Result.ok().data(map).msg("成功获取Cookie");
        }
        return Result.error().msg("未能获取Cookie");
    }

    @Override
    public Result getbuvid() {
        try {
            String request_url = "https://api.bilibili.com/x/web-frontend/getbuvid";
            BiliApiResponsePojo<BiliApiResponsePojo.buvid3> response = httpUtils.get(
                    request_url,
                    new JsonObjParseUtils.ParameterizedTypeReference<BiliApiResponsePojo<BiliApiResponsePojo.buvid3>>() {}
            );
            if(response != null) {
                return Result.ok().data(ObjMapUtils.convertToMap(response.getData())).msg("成功获取buvid3");
            }
            return Result.error().msg("响应内容不存在");
        } catch (RestClientException e) {
            return Result.error().msg("响应失败, " + e.getMessage());
        }
    }

    @Override
    public Result getQrCode() {
        try {
            // 目标API地址
            String request_url = "https://passport.bilibili.com/x/passport-login/web/qrcode/generate";
            BiliApiResponsePojo<BiliApiResponsePojo.qrcodeGenerate> response = httpUtils.get(
                    request_url,
                    new JsonObjParseUtils.ParameterizedTypeReference<BiliApiResponsePojo<BiliApiResponsePojo.qrcodeGenerate>>() {}
            );
            if(response != null) {
                return Result.ok().data(ObjMapUtils.convertToMap(response.getData())).msg("成功获取qrcode");
            }
            return Result.error().msg("响应内容不存在");
        } catch (RestClientException e) {
            return Result.error().msg("响应失败, " + e.getMessage());
        }
    }

    @Override
    public Result qrCodeLogin(String qrcode_key) {
        try {
            // 目标API地址
            // System.out.println(qrcode_key);
            String request_url = "https://passport.bilibili.com/x/passport-login/web/qrcode/poll?qrcode_key=" + qrcode_key;
            BiliApiResponsePojo<BiliApiResponsePojo.qrcodeSuccess> response = httpUtils.get(
                    request_url,
                    new JsonObjParseUtils.ParameterizedTypeReference<BiliApiResponsePojo<BiliApiResponsePojo.qrcodeSuccess>>() {}
                    );
            if(response != null) {
                return Result.ok().data(ObjMapUtils.convertToMap(response.getData())).msg("成功获取登录信息");
            }
            return Result.error().msg("响应内容不存在");
        } catch (RestClientException e) {
            return Result.error().msg("响应失败, " + e.getMessage());
        }
    }

    @Override
    public Result bindnameSearch(String bindname) {
        // 提取Map中的Cookie字符串
        String cookies = (String) getAllCookie().getData().get("cookie");
        if(cookies != null && !cookies.isEmpty()) {
            String request_url = "https://api.bilibili.com/x/web-interface/wbi/search/type?search_type=bili_user&keyword=" + bindname;
            Map<String, String> customHeaders = new HashMap<>();
            customHeaders.put("Cookie", cookies);
            BiliApiResponsePojo<BiliApiResponsePojo.searchTypeUser> response = httpUtils.getWithHeaders(
                    request_url,
                    new JsonObjParseUtils.ParameterizedTypeReference<BiliApiResponsePojo<BiliApiResponsePojo.searchTypeUser>>() {},
                    customHeaders
            );
            if(response != null) {
                return Result.ok().data(ObjMapUtils.convertToMap(response.getData())).msg("成功获取用户列表");
            }
            return Result.error().msg("响应内容不存在");
        }
        return Result.error().msg("cookie为空");
    }
}
