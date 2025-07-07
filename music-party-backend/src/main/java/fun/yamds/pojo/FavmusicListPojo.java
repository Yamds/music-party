package fun.yamds.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("musiclist")
@Builder    // 链式创建对象
public class MusiclistPojo {

    @TableId("id")
    private Long id;

    @TableField("type")
    private String type;    // bili or netease

    @TableField("music_id")
    private String musicId;

    @TableField("music_name")
    private String musicName;

    @TableField("music_author")
    private String musicAuthor;

    @TableField("music_url")
    private String musicUrl;

    @TableField("music_pic")
    private String musicPic;
}
