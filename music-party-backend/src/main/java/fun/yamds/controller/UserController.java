package fun.yamds.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.yamds.pojo.Result;
import fun.yamds.pojo.UserPojo;
import fun.yamds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：Yamds
 * @createtime ：2025/4/25
 * @description：TestController
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @SaIgnore
    @PostMapping("/login")
    public Result login(@RequestBody UserPojo user) {
        return userService.login(user);
    }

    @SaCheckLogin
    @PostMapping("/logout")
    public Result logout() {
        StpUtil.logout();
        return Result.ok().success(true).msg("注销账号");
    }

    @SaIgnore
    @PostMapping("/register")
    public Result register(@RequestBody UserPojo user) {
        return userService.register(user);
    }

    @SaCheckLogin
    @GetMapping("/isLogin")
    public Result isLogin() {
        if(StpUtil.isLogin()) {
            return Result.ok().msg("已登录").success(true);
        }
        return Result.error().msg("尚未登录").success(false);
    }

    @SaCheckLogin
    @GetMapping("/info")
    public Result getUserInfoById() {
        UserPojo user = new UserPojo();
        user.setId(StpUtil.getLoginIdAsLong());
        return userService.getUserInfoById(user);
    }

    @SaIgnore
    @GetMapping("/checkUsername/{username}")
    public Result checkUsername(@PathVariable String username) {  // 从路径中提取userID
        QueryWrapper<UserPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        if(userService.getOne(queryWrapper) != null) {
            return Result.error().msg("已存在该用户名");
        }
        return Result.ok().msg("用户名可用");
    }

    @SaCheckLogin
    @PostMapping("/changeUserInfo")
    public Result changeUserInfo(@RequestBody UserPojo user) {
        user.setId(StpUtil.getLoginIdAsLong());
        return userService.changeUserInfo(user);
    }
}
