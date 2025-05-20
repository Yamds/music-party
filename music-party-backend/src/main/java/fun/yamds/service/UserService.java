package fun.yamds.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.yamds.pojo.Result;
import fun.yamds.pojo.UserPojo;

/**
 * @author ：Yamds
 * @createtime ：2025/4/25
 * @description：UserService
 */
public interface UserService extends IService<UserPojo> {

    public Result getUser(UserPojo user);

    public Result login(UserPojo user);

    public Result getRoleById(UserPojo user);

    public Result getUserInfoById(UserPojo user);

    public Result register(UserPojo user);

    public Result getPermissionById(UserPojo user);

    public Result changeUserInfo(UserPojo user);

}
