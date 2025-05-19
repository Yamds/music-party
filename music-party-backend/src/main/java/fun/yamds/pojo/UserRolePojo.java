package fun.yamds.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：Yamds
 * @createtime ：2025/4/25
 * @description：UserRolePojo 用户角色绑定
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_role")
public class UserRolePojo {

    @TableField("user_id")
    private Long userId;  // 用户id

    @TableField("role_id")
    private Long roleId;  // 角色id
}
