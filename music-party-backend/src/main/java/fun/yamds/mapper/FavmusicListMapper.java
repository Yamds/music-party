package fun.yamds.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.yamds.pojo.FavmusicListPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FavmusicListMapper extends BaseMapper<FavmusicListPojo> {
    @Select("select f.music_id, f.music_name, f.music_author, f.music_pic, f.type from favmusic_list f where f.user_id = ${userId} && f.type = '${type}'")
    List<FavmusicListPojo> findAllMusicIdByUserId(Long userId, String type);
}
