package fun.yamds.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.yamds.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author     ：Yamds
 * @createtime       ：2025/4/25
 * @description：UserMapper
 */

@Mapper
public interface UserMapper extends BaseMapper<UserPojo> {

    // 根据user id拿到所有role
    @Select("SELECT r.role_name " +
            "FROM user u " +
            "JOIN user_role ur ON u.id = ur.user_id " +
            "JOIN role r ON ur.role_id = r.role_id " +
            "WHERE u.id = #{userId}")
    List<String> getUserRoleInfoByUserId(Long userId);

    // 根据user id拿到所有permission
    @Select("SELECT p.permission_name " +
            "FROM ( " +
            "    SELECT r.role_id " +
            "    FROM user u " +
            "    JOIN user_role ur ON u.id = ur.user_id " +
            "    JOIN role r ON r.role_id = ur.role_id " +
            "    WHERE u.id = #{userID} " +
            ") r " +
            "JOIN role_permission rp ON r.role_id = rp.role_id " +
            "JOIN permission p ON p.permission_id = rp.permission_id")
    List<String> getPermissionByUserId(Long userId);

    @Select("SELECT username FROM user WHERE username = #{username}")
    String usernameExist(String username);  // 判断用户名是否存在    如果存在，返回名字 不存在返回null

    @Update({
            "<script>",
            "UPDATE user",
            "<set>",
            "  <if test='username != null'>username = #{username},</if>",
            "  <if test='password != null and password != \"\"'>password = #{password},</if>",
            "</set>",
            "WHERE id = #{id}",
            "</script>"
    })
    int updateUserSelective(UserPojo user);
}
