export interface BiliReturnInter {
    code?: number,
    message?: string,
    ttl?: number,
    data?: object
}

export interface BiliBackstageReturnInter {
    bili_config?: {
        cookieName?: string,
        cookieContext?: string,
    }
}

export interface BiliQRcodeReturnInter {
    url: string,        // 二维码内容(登录页面 url)
    qrcode_key: string, // 扫码登录秘钥 	恒为32字符
}

export interface BiliQRcodeLoginReturnInter {
    url?: string,
    refresh_token?: string,
    timestamp?: string,
    code?: number,
    message?: string
}

export interface BiliSearchTypeUserReturnInter {
    result?: {
        mid: string,
        uname: string,
        upic: string,
    }[]
}