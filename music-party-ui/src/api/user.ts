import axios from "axios";
import { type ResultInter } from "@/types/result";
import { newUserInfo, type returnUserInfoInter } from "@/types/account";

export async function httpLogin(username: string, password: string) {
    try {
        const res = await axios({
            url: "http://localhost:10721/user/login",
            method: "post",
            data: {
                username: username,
                password: password
            },
        })
        const result: ResultInter = res.data
        if (result) {
            if (result.success == true) {
                return result.data
            } else {
                throw new Error(result.msg.toString());
            }
        }
        throw new Error('响应结果为空');
    } catch(error) {
        throw error;
    }
}

export async function httpGetUserById(userID: Number): Promise<returnUserInfoInter> {
    try {
        const res = await axios({
            url: "http://localhost:10721/user/info",
            method: "post",
            data: {
                id: userID
            },
        });
        const result: ResultInter = res.data;
        if (result) {
            if (result.success) {
                return result.data as returnUserInfoInter;
            } else {
                throw new Error(result.msg.toString());
            }
        }
        throw new Error('响应结果为空');
    } catch (error) {
        console.error(error)
        return newUserInfo()
    }
}