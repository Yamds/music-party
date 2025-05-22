import axios from "axios";
import type { BilibiliReturnInter } from "@/types/bilibili";

// 详细响应格式请参考:
// https://github.com/SocialSisterYi/bilibili-API-collect

const SESSDATA = "SESSDATA=66b1d216%2C1762819335%2C21e94%2A51CjDoGQAiXM-whu4n9FFR4AM7hhKqjm7TELCzaVSyV4x9W5XevS6sD-X74k0Mo1dxO-sSVkdVMXZhamFRS1J3ZXVuWUEySzc1N0VFUVVOOThqMTIxOU5tT3J1cTRTTkRTT3ZqQVA4MWdTdTZhdXhmNlRkd2R2MWMzbzUwaWlVM1pRN2Z1UWR4WWN3IIEC"
const cookies = ""

const axiosInstance = axios.create({
    timeout: 5000,
    withCredentials: true,  // 允许跨域携带Cookie
    headers: {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:134.0) Gecko/20100101 Firefox/134.0',
        'Cookie': SESSDATA + ";" + cookies + ";",
    }
})

// 封装一个请求函数
async function request(method: 'get' | 'post' | 'put' | 'delete', url: string, data?: any): Promise<BilibiliReturnInter> {
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
        return { code: 500, message: "bili api异常，请稍后再试..." }
    }
}

export const httpLogin = (username: string) =>
    request('post', 'https://api.bilibili.com/x/web-interface/wbi/search/type', { username });

export const httpGetUserList = (username: string) =>
    request('post', 'https://api.bilibili.com/x/web-interface/wbi/search/type', { username });
