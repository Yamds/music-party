package fun.yamds.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import fun.yamds.mapper.BiliCookieMapper;
import fun.yamds.mapper.FavmusicListMapper;
import fun.yamds.pojo.*;
import fun.yamds.service.BiliService;
import fun.yamds.utils.JsonObjParseUtils;
import fun.yamds.utils.HttpUtils;
import fun.yamds.utils.ObjMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BiliServiceImpl implements BiliService {

    @Autowired
    private BiliCookieMapper biliCookieMapper;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BiliuserServiceImpl biliuserServiceImpl;

    @Autowired
    private FavmusicListServiceImpl favmusicListServiceImpl;

    // 封装一个请求
    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private FavmusicListMapper favmusicListMapper;

    @Override
    public Result saveCookie(BiliCookiePojo bili) {
        if(bili.getCookieName() != null && !bili.getCookieName().isEmpty()) {
            if(bili.getCookieContext() != null && !bili.getCookieContext().isEmpty()) {
                int rows = biliCookieMapper.updateById(bili);
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

        BiliCookiePojo cookiePojo = biliCookieMapper.selectById(bili.getCookieName());
        if(cookiePojo != null) {
            bili.setCookieContext(cookiePojo.getCookieContext());
            HashMap<String, Object> map = new HashMap<>();
            map.put("bili_cookie", bili);
            return Result.ok().msg(bili.getCookieName() + "获取成功！").data(map);
        } else {
            return Result.error().msg("不存在该name的Cookie");
        }
    }

    @Override
    public Result getAllCookie() {
        List<BiliCookiePojo> biliCookiePojos = biliCookieMapper.selectList(null);
        HashMap<String, Object> map = new HashMap<>();
        StringBuilder cookie = new StringBuilder();
        for(BiliCookiePojo biliCookiePojo : biliCookiePojos)
            cookie.append(biliCookiePojo.getCookieName()).append("=").append(biliCookiePojo.getCookieContext()).append("; ");
        if(!cookie.isEmpty()) {
            map.put("bili_cookie", cookie.toString());
            return Result.ok().data(map).msg("成功获取Cookie");
        }
        return Result.error().msg("未能获取Cookie");
    }

    @Override
    public Result saveBiliUser(BiliuserPojo biliuserPojo, UserPojo userPojo) {
        if(biliuserPojo != null && userPojo != null) {
            boolean r1 = biliuserServiceImpl.saveOrUpdate(biliuserPojo);
            boolean r2 = userServiceImpl.saveOrUpdate(userPojo);
            if(r1 && r2) {
                return Result.ok().msg("成功存储bili绑定数据");
            }
            return Result.error().msg("bili绑定数据存储失败");
        }
        return Result.error().msg("bili绑定对象为空");
    }

    @Override
    public Result saveFavMusic(FavmusicListPojo favmusicListPojo) {
        try {
            if(favmusicListPojo != null) {
                favmusicListPojo.setType("bili");
                boolean r1 = favmusicListServiceImpl.save(favmusicListPojo);
                if(r1)
                    return Result.ok().msg("成功存入收藏歌曲");
            }
            return Result.error().msg("收藏歌曲保存失败");
        } catch (RestClientException e) {
            return Result.error().msg(e.getMessage());
        }
    }

    @Override
    public Result deleteFavMusic(FavmusicListPojo favmusicListPojo) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("music_id", favmusicListPojo.getMusicId());
        columnMap.put("user_id", favmusicListPojo.getUserId());
        boolean resu = favmusicListServiceImpl.removeByMap(columnMap);
        if(resu) {
            return Result.ok().msg("移除成功");
        } else
            return Result.error().msg("移除失败");
    }

    @Override
    public Result getFavMusic(Long userId) {
        List<FavmusicListPojo> favMusicList = favmusicListMapper.findAllMusicIdByUserId(userId, "bili");
        if(favMusicList != null && !favMusicList.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("favMusicList", favMusicList);
            return Result.ok().msg("成功获取收藏列表").data(map);
        }
        return Result.error().msg("收藏列表获取失败");
    }

    @Override
    public Result getbuvid() {
        try {
            String request_url = "https://api.bilibili.com/x/web-frontend/getbuvid";
            BiliApiResponsePojo<BiliApiResponsePojo.Buvid3> response = httpUtils.get(
                    request_url,
                    new JsonObjParseUtils.ParameterizedTypeReference<BiliApiResponsePojo<BiliApiResponsePojo.Buvid3>>() {}
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
            BiliApiResponsePojo<BiliApiResponsePojo.QrcodeGenerate> response = httpUtils.get(
                    request_url,
                    new JsonObjParseUtils.ParameterizedTypeReference<BiliApiResponsePojo<BiliApiResponsePojo.QrcodeGenerate>>() {}
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
            BiliApiResponsePojo<BiliApiResponsePojo.QrcodeSuccess> response = httpUtils.get(
                    request_url,
                    new JsonObjParseUtils.ParameterizedTypeReference<BiliApiResponsePojo<BiliApiResponsePojo.QrcodeSuccess>>() {}
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
        try {
            // 提取Map中的Cookie字符串
            String cookies = (String) getAllCookie().getData().get("bili_cookie");
            if(cookies != null && !cookies.isEmpty()) {
                String request_url = "https://api.bilibili.com/x/web-interface/wbi/search/type?search_type=bili_user&keyword=" + bindname;
                Map<String, String> customHeaders = new HashMap<>();
                customHeaders.put("Cookie", cookies);
                BiliApiResponsePojo<BiliApiResponsePojo.SearchTypeUser> response = httpUtils.getWithHeaders(
                        request_url,
                        new JsonObjParseUtils.ParameterizedTypeReference<BiliApiResponsePojo<BiliApiResponsePojo.SearchTypeUser>>() {},
                        customHeaders
                );
                if(response != null) {
                    return Result.ok().data(ObjMapUtils.convertToMap(response.getData())).msg("成功获取用户列表");
                }
                return Result.error().msg("响应内容不存在");
            }
            return Result.error().msg("cookie为空");
        } catch (RestClientException e) {
            return Result.error().msg("响应失败, " + e.getMessage());
        }
    }

    @Override
    public Result getFolderList(String up_mid) {
        try {
            String request_url = "https://api.bilibili.com/x/v3/fav/folder/created/list-all?up_mid=" + up_mid;
            // 提取Map中的Cookie字符串
            String cookies = (String) getAllCookie().getData().get("bili_cookie");
            if(cookies != null && !cookies.isEmpty()) {
                Map<String, String> customHeaders = new HashMap<>();
                customHeaders.put("Cookie", cookies);
                BiliApiResponsePojo<BiliApiResponsePojo.FolderList> response = httpUtils.getWithHeaders(
                        request_url,
                        new JsonObjParseUtils.ParameterizedTypeReference<BiliApiResponsePojo<BiliApiResponsePojo.FolderList>>() {},
                        customHeaders
                );
                if(response != null) 
                    return Result.ok().data(ObjMapUtils.convertToMap(response.getData())).msg("成功获取收藏夹列表");
                return Result.error().msg("响应内容不存在");
            }
        } catch (RestClientException e) {
            return Result.error().msg("响应失败, " + e.getMessage());
        }
        return Result.error();
    }

    @Override
    public Result getFolderInfo(String media_id, int pn) {
        try {
            String request_url = "https://api.bilibili.com/x/v3/fav/resource/list?type=0&ps=20&media_id=" + media_id + "&pn=" + pn ;
            // 提取Map中的Cookie字符串
            String cookies = (String) getAllCookie().getData().get("bili_cookie");
            if(cookies != null && !cookies.isEmpty()) {
                Map<String, String> customHeaders = new HashMap<>();
                customHeaders.put("Cookie", cookies);
                BiliApiResponsePojo<BiliApiResponsePojo.FolderInfo> response = httpUtils.getWithHeaders(
                        request_url,
                        new JsonObjParseUtils.ParameterizedTypeReference<BiliApiResponsePojo<BiliApiResponsePojo.FolderInfo>>() {},
                        customHeaders
                );
                if(response != null)
                    return Result.ok().data(ObjMapUtils.convertToMap(response.getData())).msg("成功获取收藏夹详细信息");
                return Result.error().msg("响应内容不存在");
            }
        }catch (RestClientException e) {
            return Result.error().msg("响应失败, " + e.getMessage());
        }
        return Result.error();
    }

    @Override
    public Result getVideoInfo(String bvid) {
        try {
            String request_url = "https://api.bilibili.com/x/web-interface/wbi/view?bvid=" + bvid;
            // 提取Map中的Cookie字符串
            String cookies = (String) getAllCookie().getData().get("bili_cookie");
            if(cookies != null && !cookies.isEmpty()) {
                Map<String, String> customHeaders = new HashMap<>();
                customHeaders.put("Cookie", cookies);
                BiliApiResponsePojo<BiliApiResponsePojo.VideoInfo> response = httpUtils.getWithHeaders(
                        request_url,
                        new JsonObjParseUtils.ParameterizedTypeReference<BiliApiResponsePojo<BiliApiResponsePojo.VideoInfo>>() {},
                        customHeaders
                );
                if(response != null)
                    return Result.ok().data(ObjMapUtils.convertToMap(response.getData())).msg("成功获取视频细信息");
                return Result.error().msg("响应内容不存在");
            }
        }catch (RestClientException e) {
            return Result.error().msg("响应失败, " + e.getMessage());
        }
        return Result.error();
    }
}
