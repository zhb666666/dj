import request from '@/utils/request'

// 党员列表
export function memberLists(params?: Record<string, any>) {
    return request.get({ url: '/member/list', params })
}

// 党员详情
export function memberDetail(params: Record<string, any>) {
    return request.get({ url: '/member/detail', params })
}

// 党员新增
export function memberAdd(params: Record<string, any>) {
    return request.post({ url: '/member/add', params })
}

// 党员编辑
export function memberEdit(params: Record<string, any>) {
    return request.post({ url: '/member/edit', params })
}

// 党员删除
export function memberDelete(params: Record<string, any>) {
    return request.post({ url: '/member/del', params })
}
