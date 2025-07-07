import { request } from "./baseApi";

export const httpAddRoom = (room_name: string, room_password: string) =>
    request('post', '/room/create', { roomName: room_name, roomPassword: room_password });

export const httpGetRoom = () =>
    request('get', '/room/list')