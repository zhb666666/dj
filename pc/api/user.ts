export function getUserCenter(headers?: any) {
    return $request.get({ url: '/user/center', headers })
}

// 个人信息
export function getUserInfo() {
    return $request.get({ url: '/user/info' })
}

// 个人编辑
export function userEdit(params: any) {
    return $request.post({ url: '/user/edit', params })
}

// 绑定手机
export function userBindMobile(params: any, headers?: any) {
    const requestHeaders = { ...headers }
    if (requestHeaders?.token && !requestHeaders?.open_party_java) {
        requestHeaders.open_party_java = requestHeaders.token
        delete requestHeaders.token
    }
    return $request.post(
        { url: '/user/bindMobile', params, headers: requestHeaders },
        { withToken: !requestHeaders?.open_party_java }
    )
}

// 更改密码
export function userChangePwd(params: any) {
    return $request.post({ url: '/user/changePwd', params })
}
