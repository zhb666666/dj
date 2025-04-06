import request from '@/utils/request'
// 所有有声读物类目
export function AudioCateAll(){
    return request.get({url:"/AudioCate/all"})
}
// 有声读物类目列表
export function AudioCateLists(params?: Record<string, any>) {
    return request.get({ url: '/AudioCate/list', params })
}

// 有声读物类目详情
export function AudioCateDetail(params: Record<string, any>) {
    return request.get({ url: '/AudioCate/detail', params })
}

// 有声读物类目新增
export function AudioCateAdd(params: Record<string, any>) {
    return request.post({ url: '/AudioCate/add', params })
}

// 有声读物类目编辑
export function AudioCateEdit(params: Record<string, any>) {
    return request.post({ url: '/AudioCate/edit', params })
}

// 有声读物类目删除
export function AudioCateDelete(params: Record<string, any>) {
    return request.post({ url: '/AudioCate/del', params })
}
