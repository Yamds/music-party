package fun.yamds.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("bili_config")
public class BiliPojo {

    @TableId("cookie_name")
    private String cookieName;

    @TableField("cookie_context")
    private String cookieContext;

}
