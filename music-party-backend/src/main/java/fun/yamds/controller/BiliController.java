package fun.yamds.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import fun.yamds.pojo.BiliCookiePojo;
import fun.yamds.pojo.BiliuserPojo;
import fun.yamds.pojo.Result;
import fun.yamds.pojo.UserPojo;
import fun.yamds.service.BiliService;
import fun.yamds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bili")
public class BiliController {

    @Autowired
    BiliService biliService;

    @Autowired
    private UserService userService;

    @SaCheckLogin
    @PostMapping("/cookie")
    public Result saveData(@RequestBody BiliCookiePojo bili) {
        return biliService.saveCookie(bili);
    }

    @SaCheckLogin
    @GetMapping("/sessdata")
    public Result getSessdata() {
        BiliCookiePojo bili = new BiliCookiePojo();
        bili.setCookieName("SESSDATA");
        return biliService.getCookie(bili);
    }

    @SaCheckLogin
    @PostMapping("/bind")
    public Result bindBiliUser(@RequestBody Map<String, Object> map) {
        //user_id: string, bili_id: string, bili_name: string, bili_pic: string
        BiliuserPojo biliuser = new BiliuserPojo(
                Long.parseLong(map.get("bili_id").toString()),
                map.get("bili_name").toString(),
                map.get("bili_pic").toString()
        );
        UserPojo user = new UserPojo();
        user.setId(Long.parseLong(map.get("user_id").toString()));
        user.setBiliId(Long.parseLong(map.get("bili_id").toString()));
        return biliService.saveBiliUser(biliuser, user);
    }

    @SaCheckLogin
    @GetMapping("/qrcode")
    public Result getQrCode() {
        return biliService.getQrCode();
    }

    @SaCheckLogin
    @GetMapping("/qrcodeLogin/{qrcode_key}")
    public Result qrCodeLogin(@PathVariable String qrcode_key) {
        return biliService.qrCodeLogin(qrcode_key);
    }

    @SaCheckLogin
    @GetMapping("/bindnameSearch/{bindname}")
    public Result bindnameSearch(@PathVariable String bindname) {
        return biliService.bindnameSearch(bindname);
    }

    @SaCheckLogin
    @GetMapping("/folderList/{user_id}")
    public Result getFolderList(@PathVariable String user_id) {
        Result isBind = userService.isBindBili(new UserPojo(Long.parseLong(user_id)));     // 判断是否绑定了Bili id
        if(isBind.getSuccess()) {
            return biliService.getFolderList(isBind.getData().get("bili_id").toString());
        }
        return isBind;
    }

    @SaCheckLogin
    @GetMapping("/folderInfo/{media_id}/{pn}")
    public Result getFolderInfo(@PathVariable String media_id, @PathVariable int pn) {
        return biliService.getFolderInfo(media_id, pn);
    }
}
