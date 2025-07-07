package fun.yamds.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.yamds.mapper.RoomMapper;
import fun.yamds.pojo.Result;
import fun.yamds.pojo.RoomPojo;

public interface RoomService extends IService<RoomPojo> {


    public Result addRoom(RoomPojo room);

    public Result getRoomList();

    public Result JoinRoom(long userId, long roomId, String roomPassword);

    public Result LeaveRoom(long userId);
}
