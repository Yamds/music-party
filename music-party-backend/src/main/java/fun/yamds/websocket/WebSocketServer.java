package fun.yamds.websocket;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSONObject;
import fun.yamds.pojo.Result;
import fun.yamds.service.impl.RoomServiceImpl;
import fun.yamds.utils.SpringContextHolder;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/api/ws/{satoken}")
public class WebSocketServer {
    /**concurrent包的线程安全Set，全部WebSocket对象*/
    private static final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    /**concurrent包的线程安全Set，房间里所有的WebSocket对象。*/
    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, Session>> sessionRoomMap = new ConcurrentHashMap<>();

    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("satoken") String satoken) throws IOException {
        // this.session = session;
        Object loginId = StpUtil.getLoginIdByToken(satoken);
        if (loginId == null) {
             session.close();
            throw new SaTokenException("ws连接失败，无效token: " + satoken);
        }

        long userid = Long.parseLong(loginId.toString());
        sessionMap.put("user_id_" + satoken, session);
        String tips = "ws连接成功!sessionid=" + session.getId() + ", userid=" + userid;
        System.out.println(tips);
        sendMessage(session, tips);

    }

    @OnMessage
    public void onMessage(String message, Session session) {

        getMessage(message, session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("ws连接关闭");
        for (String key : sessionMap.keySet()) {
            if(sessionMap.get(key) == session) {
                sessionMap.remove(key);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {}

    //接收消息
    public static void getMessage(String message, Session session) {
        RoomServiceImpl roomService = SpringContextHolder.getBean(RoomServiceImpl.class);

        JSONObject msg = JSONObject.parseObject(message);
        String command = msg.getString("command");
        JSONObject data = msg.getJSONObject("data");
        long userId = Long.parseLong(StpUtil.getLoginIdByToken(data.getString("satoken")).toString());
        switch (command) {
            case "joinRoom": {
                long roomId = Long.parseLong(data.getString("roomId"));
                String roomPassword = data.getString("roomPassword");

                Result isInRoom = roomService.isInRoom(userId);
                if(isInRoom != null && isInRoom.getSuccess()) {
                    sendMessage(session, isInRoom.getMsg() + ", 无法重复加入，请先退出房间。");
                    break;
                }

                Result result = roomService.JoinRoom(userId, roomId, roomPassword);
                if (result != null && result.getSuccess()) {
                    // 房间不存在时创建
                    sessionRoomMap.computeIfAbsent(Long.toString(roomId), k -> new ConcurrentHashMap<>());
                    // 加入房间
                    sessionRoomMap.get(Long.toString(roomId)).put(Long.toString(userId), session);

                    sendMessage(session, "{\"success\": true, \"msg\"" + result.getMsg() + "\"}");
                } else {
                    sendMessage(session, "{\"success\": false, \"msg\": \"" + result.getMsg() + "\"}");
                }
                System.out.println("map : " + sessionRoomMap);
                break;
            }

            case "leaveRoom": {
                Result result = roomService.LeaveRoom(userId);
                if(result != null && result.getSuccess()) {
                    sendMessage(session, "{\"success\": true, \"msg\"" + result.getMsg() + "\"}");
                }
                sendMessage(session, "{\"success\": false, \"msg\": \"" + result.getMsg() + "\"}");
            }

            case "isInRoom": {
                Result result = roomService.isInRoom(userId);
                if(result != null && result.getSuccess()) {
                    sendMessage(session, "{\"success\": true, \"msg\"" + result.getMsg() + "\"}");
                }
                sendMessage(session, "{\"success\": false, \"msg\": \"" + result.getMsg() + "\"}");
            }
        }
    }

    // 向指定客户端推送消息
    public static void sendMessage(Session session, String message) {
        try {
            System.out.println("向sid为：" + session.getId() + "，发送：" + message);
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
