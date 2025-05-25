package fun.yamds.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.yamds.pojo.BiliPojo;
import fun.yamds.pojo.Result;

public interface BiliService extends IService<BiliPojo> {

    // 以下是前后端交互的方法
    public Result saveData(BiliPojo biliPojo);

    public Result getData();

    // 以下是与b站官方交互的方法
    // 详细响应格式请参考:
    // https://github.com/SocialSisterYi/bilibili-API-collect

    public Result getQrCode();

    public Result qrCodeLogin(String qrcode_key);
}
