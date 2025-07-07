import { request } from "./baseApi";

// 前后端交互
export const httpSaveSessdata = (sessdata: string) =>
    request('post', '/bili/cookie', { cookieName: "SESSDATA", cookieContext: sessdata });

export const httpGetSessdata = () =>
    request('get', '/bili/sessdata');

export const httpBindBiliUser = ( bili_id: string, bili_name: string, bili_pic: string) =>
    request('post', '/bili/bind', { biliId: bili_id, biliName: bili_name, biliPic: bili_pic })

export const httpGetQrCode = () =>
    request('get', '/bili/qrcode');

export const httpQrCodeLogin = (qrcode_key: string) =>
    request('get', `/bili/qrcodeLogin/${qrcode_key}`);

export const httpBindnameSearch = (bindname: string) =>
    request('get', `/bili/bindnameSearch/${bindname}`);

export const httpGetFolderList = () =>
    request('get', `/bili/folderList`);

export const httpGetFolderInfo = (media_id: string, pn: number) =>
    request('get', `/bili/folderInfo/${media_id}/${pn}`);

export const httpSaveFavMusic = (music_id: string, music_name: string, music_author: string, music_pic: string) =>
    request('post', '/bili/favMusic', { musicId: music_id, musicName: music_name, musicAuthor: music_author, musicPic: music_pic});

export const httpDeleteFavMusic = (music_id: string) =>
    request('delete', `/bili/favMusic/${music_id}`);

export const httpGetFavMusic = () =>
    request('get', '/bili/favMusic');