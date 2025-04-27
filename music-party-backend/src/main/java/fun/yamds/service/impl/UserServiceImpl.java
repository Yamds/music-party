package fun.yamds.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.yamds.mapper.UserMapper;
import fun.yamds.pojo.Result;
import fun.yamds.pojo.UserPojo;
import fun.yamds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author ：Yamds
 * @createtime ：2025/4/25
 * @description：UserServiveImpl
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Result getUser(UserPojo user) {
        QueryWrapper<UserPojo> queryWrapper = new QueryWrapper<>();

        // 有id用id查询，没id尝试用户名查询
        if(user.getId() != null) {
            queryWrapper.eq("id", user.getId());
        } else if(user.getUsername() != null) {
            queryWrapper.eq("username", user.getUsername());
        }

        UserPojo user2 = userMapper.selectOne(queryWrapper);
        if(user2 == null) {
            return Result.error().msg("查询用户不存在");
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("user", user2);
            return Result.ok().msg("查询成功: " + user2.getUsername()).data(map);
        }
    }

    @Override
    public Result login (UserPojo user) {
        Result result = getUser(user);

        if(result.getSuccess()) {
            Object obj = result.getData().get("user");
            // 检查类型
            if(obj instanceof UserPojo user2) {
                // 登录逻辑
                if (user.getUsername().equals(user2.getUsername())&&user.getPassword().equals(user2.getPassword())) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("user", user2);
                    return Result.ok().msg("登录成功").data(map);
                } else
                    return Result.error().msg("用户名或密码不正确");
            }else
                return Result.error().msg("接收类型不正确");
        } else
            return result;
    }

    @Override
    public Result getRoleById(UserPojo user) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("role_name", userMapper.getUserRoleInfoByUserId(user.getId()));
        if(map.get("role_name") != null)
            return Result.ok().msg("角色名获取成功").data(map);
        else
            return Result.error().msg("角色名未成功获取或不存在");
    }

    @Override
    public Result getUserInfoById(UserPojo user) {
        Result result1 = getUser(user);
        Result result2 = getRoleById(user);
        if(result1.getSuccess()) {
            if(result2.getSuccess()) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("user", result1.getData().get("user"));
                map.put("role_name", result2.getData().get("role_name"));
                return Result.ok().msg("成功获取用户信息和角色").data(map);
            } else
                return result2;
        } else
            return result1;
    }
}
