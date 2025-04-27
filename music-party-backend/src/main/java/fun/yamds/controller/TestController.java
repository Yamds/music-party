package fun.yamds.controller;

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

    @PostMapping("/info")
    public Result getUserInfoById(@RequestBody UserPojo user) {
        return userService.getUserInfoById(user);
    }
}
