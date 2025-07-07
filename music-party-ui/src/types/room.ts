export interface RoomInter {
    roomId: string,
    roomName: string,
    roomPassword: string,
    roomCreator: string,
    createTime: string,
}

export interface RoomListInter {
    room_list: RoomInter[]
}