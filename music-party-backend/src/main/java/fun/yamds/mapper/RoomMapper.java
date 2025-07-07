package fun.yamds.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.yamds.pojo.RoomPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoomMapper extends BaseMapper<RoomPojo> {
}
