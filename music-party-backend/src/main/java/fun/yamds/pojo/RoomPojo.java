package fun.yamds.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("room")
public class RoomPojo {

    @TableId(value = "room_id", type = IdType.ASSIGN_ID)
    private Long roomId;

    @TableField("room_name")
    private String roomName;

    @TableField("room_password")
    private String roomPassword;

    @TableField("room_creator")
    private String roomCreator; // 用户id

    @TableField("create_time")
    private String createTime;
}
