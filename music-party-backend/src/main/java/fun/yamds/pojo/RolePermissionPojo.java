package fun.yamds.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：Yamds
 * @createtime ：2025/5/19
 * @description：RolePermissionPojo
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role_permission")
public class RolePermissionPojo {
    @TableField("role_id")
    private Long roleID;

    @TableField("permission_id")
    private Long permissionID;

    @TableField("delete_flag")
    @TableLogic
    private Boolean deleteFlag;
}
