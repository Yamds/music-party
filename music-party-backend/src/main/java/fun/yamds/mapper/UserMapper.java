package fun.yamds.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.yamds.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author     ：Yamds
 * @createtime       ：2025/4/25
 * @description：UserMapper
 */

@Mapper
public interface UserMapper extends BaseMapper<UserPojo> {

    // 根据id拿到所有role tag
    @Select("SELECT r.role_name " +
            "FROM user u " +
            "JOIN user_role ur ON u.id = ur.user_id " +
            "JOIN role r ON ur.role_id = r.role_id " +
            "WHERE u.id = #{userId}")
    List<String> getUserRoleInfoByUserId(Long userId);
}
