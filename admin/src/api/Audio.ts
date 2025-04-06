import request from '@/utils/request'

// 有声读物列表
export function AudioLists(params?: Record<string, any>) {
    return request.get({ url: '/Audio/list', params })
}

// 有声读物详情
export function AudioDetail(params: Record<string, any>) {
    return request.get({ url: '/Audio/detail', params })
}

// 有声读物新增
export function AudioAdd(params: Record<string, any>) {
    return request.post({ url: '/Audio/add', params })
}

// 有声读物编辑
export function AudioEdit(params: Record<string, any>) {
    return request.post({ url: '/Audio/edit', params })
}

// 有声读物删除
export function AudioDelete(params: Record<string, any>) {
    return request.post({ url: '/Audio/del', params })
}
