import request from '@/utils/request'

// 党员档案列表
export function archivesLists(params?: Record<string, any>) {
    return request.get({ url: '/archives/list', params })
}

// 党员档案详情
export function archivesDetail(params: Record<string, any>) {
    return request.get({ url: '/archives/detail', params })
}

// 党员档案新增
export function archivesAdd(params: Record<string, any>) {
    return request.post({ url: '/archives/add', params })
}

// 党员档案编辑
export function archivesEdit(params: Record<string, any>) {
    return request.post({ url: '/archives/edit', params })
}

// 党员档案删除
export function archivesDelete(params: Record<string, any>) {
    return request.post({ url: '/archives/del', params })
}
