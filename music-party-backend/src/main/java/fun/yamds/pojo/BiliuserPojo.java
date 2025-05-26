package fun.yamds.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：Yamds
 * @createtime ：2025/5/26
 * @description：biliuser
 */

@TableName("biliuser")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BiliuserPojo {

    @TableId("bili_id")
    private long biliId;

    @TableField("bili_name")
    private String biliName;

    @TableField("bili_pic")
    private String biliPic;
}
