package fun.yamds.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import fun.yamds.pojo.Result;
import fun.yamds.pojo.RoomPojo;
import fun.yamds.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @SaCheckLogin
    @PostMapping("/create")
    public Result addRoom(@RequestBody RoomPojo room) {
        return roomService.addRoom(room);
    }

    @SaCheckLogin
    @GetMapping("/list")
    public Result getRoomList() {
        return roomService.getRoomList();
    }

    // @SaCheckLogin
    // @PostMapping("")
}
