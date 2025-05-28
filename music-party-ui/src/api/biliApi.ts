import axios from "axios";
import type { ResultInter } from "@/types/result";

// 注意 这个是像后台发送一些有关bili的请求
// 对bili api发起的请求看另一个ts文件

// 详细响应格式请参考:
// https://github.com/SocialSisterYi/bilibili-API-collect

const baseURL = import.meta.env.MODE === 'development'
    ? 'http://localhost:80/api'
    : 'http://45.95.212.18:34184/api'

const axiosInstance = axios.create({
    baseURL,
    timeout: 5000,
})

// 封装一个请求函数
async function request(method: 'get' | 'post' | 'put' | 'delete', url: string, data?: any): Promise<ResultInter> {
    try {
        const res = await axiosInstance({
            method,
            url,
            ...(method === 'get' ? { params: data } : { data })
        });
        if (res.data.success)
            return res.data;

        console.error("请求: " + url + "\tbili api错误信息: " + res.data.code + " " + res.data.msg)
        return res.data;
    }
    catch (error: any) {
        console.error(error)
        return { code: 500, success: false, msg: "bili api异常，请稍后再试..." }
    }
}

// 前后端交互
export const httpSaveSessdata = (sessdata: string) =>
    request('post', '/bili/cookie', { cookieName: "SESSDATA", cookieContext: sessdata });

export const httpGetSessdata = () =>
    request('get', '/bili/sessdata');

export const httpBindBiliUser = (userId: string, biliId: string, biliName: string, biliPic: string) =>
    request('post', '/bili/bind', { user_id: userId, bili_id: biliId, bili_name: biliName, bili_pic: biliPic })

export const httpGetQrCode = () =>
    request('get', '/bili/qrcode');

export const httpQrCodeLogin = (qrcode_key: string) =>
    request('get', `/bili/qrcodeLogin/${qrcode_key}`);

export const httpBindnameSearch = (bindname: string) =>
    request('get', `/bili/bindnameSearch/${bindname}`);

export const httpGetFolderList = (user_id: string) =>
    request('get', `/bili/folderList/${user_id}`);

export const httpGetFolderInfo = (media_id: string, pn: number) =>
    request('get', `/bili/folderInfo/${media_id}/${pn}`);