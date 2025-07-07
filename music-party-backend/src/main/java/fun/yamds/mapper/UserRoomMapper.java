package fun.yamds.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.yamds.pojo.UserRoomPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoomMapper extends BaseMapper<UserRoomPojo> {
}
