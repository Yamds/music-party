import axios from "axios";
import type { ResultInter } from "@/types/result";
import { ElMessage } from "element-plus";

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
export async function request(method: 'get' | 'post' | 'put' | 'delete', url: string, data?: any): Promise<ResultInter> {
    try {
        const res = await axiosInstance({
            method,
            url,
            ...(method === 'get' ? { params: data } : { data })
        });
        if (res.data.success)
            return res.data;

        console.error("请求: " + url + "\tapi错误信息: " + res.data.code + " " + res.data.msg)
        ElMessage.error("api错误信息: " + res.data.msg)
        return res.data;
    }
    catch (error: any) {
        console.error(error)
        return { code: 500, success: false, msg: "api异常，请稍后再试..." }
    }
}