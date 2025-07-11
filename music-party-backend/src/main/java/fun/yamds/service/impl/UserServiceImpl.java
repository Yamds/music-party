package fun.yamds.service.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yamds.mapper.UserMapper;
import fun.yamds.mapper.UserRoleMapper;
import fun.yamds.pojo.BiliuserPojo;
import fun.yamds.pojo.Result;
import fun.yamds.pojo.UserPojo;
import fun.yamds.pojo.UserRolePojo;
import fun.yamds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author ：Yamds
 * @createtime ：2025/4/25
 * @description：UserServiveImpl
 */

@Service
public class UserServiceImpl extends ServiceImpl<BaseMapper<UserPojo>, UserPojo> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

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
        try {
            Thread.sleep(1000); // 延时 1 秒（1000 毫秒）
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // 恢复中断状态
            return Result.error().msg("线程被中断");
        }
        Result result = getUser(user);

        if(result.getSuccess()) {
            Object obj = result.getData().get("user");
            // 检查类型
            if(obj instanceof UserPojo user2) {
                // 登录逻辑
                if (user.getUsername().equals(user2.getUsername())&&BCrypt.checkpw(user.getPassword(), user2.getPassword())) {
                    StpUtil.login(user2.getId());
                    return Result.ok().msg("登录成功!");
                } else
                    return Result.error().msg("用户名或密码不正确!");
            }else
                return Result.error().msg("接收类型不正确!");
        } else {
            result.setMsg("用户名或密码不正确!");
            return result;
        }
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
    public Result getPermissionById(UserPojo user) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("permission_name", userMapper.getPermissionByUserId(user.getId()));
        if(map.get("permission_name") != null)
            return Result.ok().msg("权限获取成功").data(map);
        else
            return Result.error().msg("权限未获取或不存在");
    }

    @Override
    public Result getBindBiliName(UserPojo user) {
        HashMap<String, Object> map = new HashMap<>();
        List<BiliuserPojo> biliuser = userMapper.getBindBiliInfoByUserId(user.getId());
        if(!biliuser.isEmpty()) {
            map.put("bili_info", Arrays.asList(
                    biliuser.get(0).getBiliId(),
                    biliuser.get(0).getBiliName(),
                    biliuser.get(0).getBiliPic()
            ));
            if(map.get("bili_info") != null)
                return Result.ok().msg("bili信息获取成功").data(map);
        }
        return Result.error().msg("bili信息获取失败或不存在");

    }

    @Override
    public Result getUserInfoById(UserPojo user) {
        Result result1 = getUser(user);
        Result result2 = getRoleById(user);
        Result result3 = getPermissionById(user);
        Result result4 = getBindBiliName(user);

        if(result1.getData() == null || result2.getData() == null || result3.getData() == null) {
            return Result.error().msg("获取结果为空");
        }

        // 确保三项信息获取成功   如果获取失败，返回的result中包含失败信息
        if(!result1.getSuccess())
            return result1;
        if(!result2.getSuccess())
            return result2;
        if(!result3.getSuccess())
            return result3;

        // 返回信息前 清除密码
        Object obj = result1.getData().get("user");
        if(obj instanceof UserPojo user2) {
            user2.setPassword(null);
        } else {
            return Result.error();
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("user", user2);
        map.put("role_name", result2.getData().get("role_name"));
        map.put("permission_name", result3.getData().get("permission_name"));
        if(result4.getData() != null)
            map.put("bili_info", result4.getData().get("bili_info"));
        else
            map.put("bili_info", "");
        return Result.ok().msg("成功获取用户信息、角色、权限和绑定用户名").data(map);
    }

    // service
    @Override
    public Result register(UserPojo user) {
        UserRolePojo userRole = new UserRolePojo();

        if(user.getUsername() == null || user.getPassword() == null)
            return Result.error().msg("用户名或密码为null");
        // 加密
        user.setPassword(BCrypt.hashpw(user.getPassword()));

        if(userMapper.usernameExist(user.getUsername()) != null)
            return Result.error().msg("注册失败: 已存在相同用户名");

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        user.setCreateTime(timestamp);
        user.setUpdateTime(timestamp);

        if(userMapper.insert(user) != 0) {
            // 获取id，插入角色关系(3L 是 user)
            QueryWrapper<UserPojo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", user.getUsername());
            UserPojo user2 = userMapper.selectOne(queryWrapper);
            userRole.setUserId(user2.getId());
            userRole.setRoleId(3L);
            if(userRoleMapper.insert(userRole) != 0)
                return Result.ok().msg("注册成功");
            else
                return Result.error().msg("注册失败: 插入用户角色关系失败！");
        }
        return Result.error().msg("注册失败: 没有成功插入数据");
    }

    @Override
    public Result changeUserInfo(UserPojo user) {
        // 用户名检查
        if(Objects.equals(user.getUsername(), ""))
            user.setUsername(null);
        else {
            if(userMapper.usernameExist(user.getUsername()) != null)
                return Result.error().msg("修改失败: 已存在相同用户名");
        }
        // 密码检查
        if(Objects.equals(user.getPassword(), ""))
            user.setPassword(null);
        else {
            // 密码加密
            user.setPassword(BCrypt.hashpw(user.getPassword()));
        }
        if(user.getId() == null || user.getId() == 0)
            return Result.error().msg("id为空！");
        int rows = userMapper.updateById(user); // 调用 updateById 方法
        if (rows > 0) {
            return Result.ok().msg("修改成功！");
        } else {
            return Result.error().msg("修改失败！");
        }
    }

    @Override
    public Result isBindBili(UserPojo user) {
        QueryWrapper<UserPojo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("bili_id")
                .eq("id", user.getId());
        UserPojo userPojo = userMapper.selectOne(queryWrapper);
        if(userPojo == null)
            return Result.error().msg("未能获取用户关联bili id");

        HashMap<String, Object> map = new HashMap<>();
        map.put("bili_id", userPojo.getBiliId());
        return Result.ok().msg("成功获取用户关联BILI id").data(map);
    }
}
