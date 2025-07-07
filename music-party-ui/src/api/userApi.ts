import { request } from "./baseApi";

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