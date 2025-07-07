package fun.yamds.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yamds.mapper.RoomMapper;
import fun.yamds.pojo.Result;
import fun.yamds.pojo.RoomPojo;
import fun.yamds.pojo.UserRoomPojo;
import fun.yamds.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, RoomPojo> implements RoomService {
    private final UserServiceImpl userServiceImpl;

    public RoomServiceImpl(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
    @Autowired
    UserRoomServiceImpl userRoomService;

    public Result addRoom(RoomPojo room) {
        try {
            room.setCreateTime(Long.toString(new Date().getTime()));
            room.setRoomCreator(Long.toString(StpUtil.getLoginIdAsLong()));
            boolean resu = this.save(room);
            if(resu) {
                return Result.ok().msg("房间创建成功");
            }
            return Result.error().msg("房间新建失败");
        } catch (DuplicateKeyException e) {
            return Result.error().msg("一个用户只能创建一个房间！"); // 唯一索引
        }

    }

    public Result getRoomList() {
        List<RoomPojo> list = this.list();

        if(list.isEmpty())
            return Result.error().msg("房间为空");

        // 根据用户id拿到用户名
        for(RoomPojo room : list) {
            // 修改返回前端的数据，用户id->用户名，密码返回是否需要密码
            room.setRoomCreator(userServiceImpl.getById(room.getRoomCreator()).getUsername());
            room.setRoomPassword(!room.getRoomPassword().isEmpty() ? "true":"false");
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("room_list", list);
        return Result.ok().msg("成功获取房间列表！").data(map);
    }

    public Result JoinRoom(long userId, long roomId, String roomPassword) {
        RoomPojo room = this.getById(roomId);
        if(room == null) {
            return Result.error().msg("房间不存在");
        }
        if(!room.getRoomPassword().isEmpty() && !room.getRoomPassword().equals(roomPassword)) {
            return Result.error().msg("房间密码不正确");
        }
        UserRoomPojo userRoom = new UserRoomPojo();
        userRoom.setUserId(userId);
        userRoom.setRoomId(roomId);
        boolean resu = userRoomService.saveOrUpdate(userRoom);

        if(resu)
            return Result.ok().msg("加入成功");

        return Result.error().msg("加入失败");
    }

    public Result LeaveRoom(long userId) {
        boolean resu = userRoomService.removeById(userId);
        if(resu)
            return Result.ok();
        return Result.error();
    }

    public Result isInRoom(long userId) {
        UserRoomPojo resu = userRoomService.getById(userId);
        if(resu == null)
            return Result.error().msg("该用户不在任何房间");
        return Result.ok().msg("该用户已在某个房间中");
    }
}
