package fun.yamds.service;

import fun.yamds.pojo.*;

public interface BiliService {

    // 以下是前后端交互的方法
    public Result saveCookie(BiliCookiePojo biliCookiePojo);

    public Result getCookie(BiliCookiePojo biliCookiePojo);

    public Result getAllCookie();

    public Result saveBiliUser(BiliuserPojo biliuserPojo, UserPojo userPojo);

    public Result saveFavMusic(FavmusicListPojo favmusicListPojo);

    public Result deleteFavMusic(FavmusicListPojo favmusicListPojo);

    public Result getFavMusic(Long userId);

    // 以下是与b站官方交互的方法
    // 详细响应格式请参考:
    // https://github.com/SocialSisterYi/bilibili-API-collect

    public Result getbuvid();

    public Result getQrCode();

    public Result qrCodeLogin(String qrcode_key);

    public Result bindnameSearch(String bindname);

    public Result getFolderList(String up_mid);

    public Result getFolderInfo(String media_id, int pn);

    public Result getVideoInfo(String bvid);
}
