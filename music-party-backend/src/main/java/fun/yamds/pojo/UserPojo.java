package fun.yamds.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author ：Yamds
 * @createtime ：2025/4/25
 * @description：UserPojo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class UserPojo {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;            // 用户id

    @TableField("username")
    private String username;    // 用户名

    @TableField("password")
    private String password;    // 密码

    @TableField("netease_id")
    private Long neteaseId;     // 网易云音乐id

    @TableField("bili_id")
    private Long biliId;        // b站id

    @TableField("status")
    private Integer status;     // 状态(0启用 1禁用)

    @TableField("create_by")
    private String createBy;    // 创建人

    @TableField("update_by")
    private String updateBy;    // 修改人

    @TableField("create_time")
    private String createTime;  // 创建时间

    @TableField("update_time")
    private String updateTime;  // 修改时间

    @TableField("delete_flag")
    @TableLogic
    private Boolean deleteFlag; // 逻辑删除标识(0正常 1删除)

}
