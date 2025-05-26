package fun.yamds.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.yamds.pojo.BiliCookiePojo;
import fun.yamds.pojo.Result;

public interface BiliService extends IService<BiliCookiePojo> {

    // 以下是前后端交互的方法
    public Result saveCookie(BiliCookiePojo biliCookiePojo);

    public Result getCookie(BiliCookiePojo biliCookiePojo);

    public Result getAllCookie();

    // 以下是与b站官方交互的方法
    // 详细响应格式请参考:
    // https://github.com/SocialSisterYi/bilibili-API-collect

    public Result getbuvid();

    public Result getQrCode();

    public Result qrCodeLogin(String qrcode_key);

    public Result bindnameSearch(String bindname);
}
