package fun.yamds.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import fun.yamds.pojo.*;
import fun.yamds.service.BiliService;
import fun.yamds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public Result bindBiliUser(@RequestBody BiliuserPojo biliuser) {
        UserPojo user = new UserPojo();
        user.setId(StpUtil.getLoginIdAsLong());
        user.setBiliId(biliuser.getBiliId());
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
    @GetMapping("/folderList")
    public Result getFolderList() {
        System.out.println(new UserPojo(StpUtil.getLoginIdAsLong()));
        Result isBind = userService.isBindBili(new UserPojo(StpUtil.getLoginIdAsLong()));     // 判断是否绑定了Bili id
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

    @SaCheckLogin
    @PostMapping("/favMusic")
    public Result saveFavMusic(@RequestBody FavmusicListPojo favmusicListPojo) {
        System.out.println(favmusicListPojo);
        favmusicListPojo.setUserId(StpUtil.getLoginIdAsLong());
        favmusicListPojo.setCreateTime(Long.toString(new Date().getTime()));
        return biliService.saveFavMusic(favmusicListPojo);
    }

    @SaCheckLogin
    @DeleteMapping("/favMusic/{music_id}")
    public Result deleteFavMusic(@PathVariable String music_id) {
        FavmusicListPojo favmusicListPojo = new FavmusicListPojo();
        favmusicListPojo.setUserId(StpUtil.getLoginIdAsLong());
        favmusicListPojo.setMusicId(music_id);
        return biliService.deleteFavMusic(favmusicListPojo);
    }


    @SaCheckLogin
    @GetMapping("/favMusic")
    public Result getFavMusic() {
        return biliService.getFavMusic(StpUtil.getLoginIdAsLong());
    }
}
