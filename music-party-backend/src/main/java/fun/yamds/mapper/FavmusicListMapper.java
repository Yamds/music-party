package fun.yamds.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.yamds.pojo.MusiclistPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MusiclistMapper extends BaseMapper<MusiclistPojo> {
}
