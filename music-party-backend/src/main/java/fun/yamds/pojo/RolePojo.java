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
 * @createtime ：2025/4/25
 * @description：RolePojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("role")
public class RolePojo {

    @TableId("role_id")
    private Long roleID;

    @TableField("role_name")
    private String roleName;

    @TableField("role_desc")
    private String roleDesc;

    @TableField("delete_flag")
    @TableLogic
    private Boolean deleteFlag;
}
