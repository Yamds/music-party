import axios from "axios";
import { type ResultInter } from "@/types/result";

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

        console.error("请求: " + url + "\t错误信息: " + res.data.code + " " + res.data.msg)
        return res.data;
    }
    catch (error: any) {
        console.error(error)
        return { code: 500, success: false, msg: "服务器异常，请稍后再试..." }
    }
}

export const httpRegister = (username: string, password: string) =>
    request('post', '/user/register', { username, password });

export const httpLogin = (username: string, password: string) =>
    request('post', '/user/login', { username, password });

export const httpLogout = () =>
    request('post', '/user/logout');

export const httpGetUserByLogin = () =>
    request('get', '/user/info');

export const httpIsLogin = () =>
    request('get', '/user/isLogin');

export const httpUsernameExist = (username: string) =>
    request('get', `/user/checkUsername/${username}`);

export const httpChangeUserInfo = (username: string, password: string) =>
    request('post', '/user/changeUserInfo', { username, password });