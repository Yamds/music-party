package fun.yamds.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.yamds.pojo.Result;
import fun.yamds.pojo.UserPojo;
import fun.yamds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author ：Yamds
 * @createtime ：2025/4/25
 * @description：TestController
 */

@RestController
@RequestMapping("/user")
@CrossOrigin
public class TestController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserPojo user) {
        return userService.login(user);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserPojo user) {
        return userService.register(user);
    }

    @GetMapping("/info/{userId}")
    public Result getUserInfoById(@PathVariable Long userId) {  // 从路径中提取userID
        UserPojo user = new UserPojo();
        user.setId(userId);
        return userService.getUserInfoById(user);
    }

    @GetMapping("/checkUsername/{username}")
    public Result getUserInfoById(@PathVariable String username) {  // 从路径中提取userID
        QueryWrapper<UserPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        if(userService.getOne(queryWrapper) != null) {
            return Result.error().msg("已存在该用户名");
        }
        return Result.ok().msg("用户名可用");
    }


}
