export interface BiliReturnInter {
    code?: Number,
    message?: string,
    ttl?: Number,
    data?: Object
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
    timestamp?: number,
    code?: number,
    message?: string
}

export interface BiliSearchTypeUserReturnInter {
    result?: {
        mid: number,
        uname: string,
        upic: string,
    }[]
}