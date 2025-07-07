export interface BiliReturnInter {
    code?: number,
    message?: string,
    ttl?: number,
    data?: object
}

export interface BiliBackstageReturnInter {
    bili_cookie?: {
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

export interface BiliFolerListInter {
    count: number,
    list: {
        id: string,
        fid: string,
        mid: string,
        title: string,
        media_count: number,
    }[]
}

export interface BiliFolderInfoInter {
    has_more: boolean,
    page: number,
    info: {
        id: string,
        fid: string,
        mid: string,
        title: string,
        cover: string,
        upper: {
            mid: string,
            name: string,
        }
    },
    medias: {
        id: string,
        bvid: string,
        type: number,
        title: string,
        cover: string,
        intro: string,
        page: number,
        duration: number,
        upper: {
            mid: string,
            name: string,
            face: string,
        }
    }[]
}

export interface BiliMusicItem {
    type?: string,
    musicId?: string,
    musicName?: string,
    musicAuthor?: string,
    musicPic?: string,
    create_time?: string,
}

export interface BiliFavListInter {
    favMusicList?: BiliMusicItem[]
}