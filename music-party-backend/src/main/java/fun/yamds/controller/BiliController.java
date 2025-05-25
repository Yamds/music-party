package fun.yamds.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import fun.yamds.pojo.BiliPojo;
import fun.yamds.pojo.Result;
import fun.yamds.service.BiliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bili")
public class BiliController {

    @Autowired
    BiliService biliService;

    @SaCheckLogin
    @PostMapping("/saveData")
    public Result saveData(@RequestBody BiliPojo bili) {
        return biliService.saveData(bili);
    }

    @SaCheckLogin
    @GetMapping("/getSessdata")
    public Result getSessdata() {
        return biliService.getData();
    }

    @SaCheckLogin
    @GetMapping("/getQrCode")
    public Result getQrCode() {
        return biliService.getQrCode();
    }

    @SaCheckLogin
    @GetMapping("/qrCodeLogin/{qrcode_key}")
    public Result qrCodeLogin(@PathVariable String qrcode_key) {
        return biliService.qrCodeLogin(qrcode_key);
    }
}
