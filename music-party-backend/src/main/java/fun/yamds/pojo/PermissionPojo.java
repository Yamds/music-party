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
 * @description：PermissionPojo
 */

@TableName("permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionPojo {
    @TableId("permission_id")
    private Long PermissionID;

    @TableField("permission_name")
    private String permissionName;

    @TableField("permission_desc")
    private String permissionDesc;

    @TableField("delete_flag")
    @TableLogic
    private Boolean deleteFlag;
}
