package fun.yamds.service;

import fun.yamds.pojo.Result;
import fun.yamds.pojo.UserPojo;

/**
 * @author ：Yamds
 * @createtime ：2025/4/25
 * @description：UserService
 */
public interface UserService {

    public Result getUser(UserPojo user);

    public Result login(UserPojo user);

    public Result getRoleById(UserPojo user);

    public Result getUserInfoById(UserPojo user);
}
