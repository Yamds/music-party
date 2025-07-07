import { defineStore } from "pinia";
import { httpAddRoom, httpGetRoom } from "@/api/roomApi";
import { ElMessage } from "element-plus";
import { ref } from "vue";
import type { RoomInter, RoomListInter } from "@/types/room";

export const useRoomStore = defineStore('room', () => {
    const room_list = ref([] as RoomInter[])

    const addRoom = async (room_name: string, room_password: string) => {
        if (room_name.length <= 0) {
            ElMessage.warning("请输入房间名！")
            return
        }
        await httpAddRoom(room_name, room_password).then(data => {
            if (data.success) {
                ElMessage.success(data.msg)
                console.log(data)
            }
        })
    }

    const getRoomList = async () => {
        await httpGetRoom().then(resp => {
            if (resp.success) {
                const data = resp.data as RoomListInter
                room_list.value = data.room_list
                ElMessage.success(resp.msg)
            }
        })
    }

    return {
        room_list,
        addRoom,
        getRoomList,
    }
})