import request from '@/utils/request'
/**
 * @description 获取网上课堂列表
 * @return { Promise }
 */
export function getVideoList(data : Record<string, any>) {
	return request.get({ url: '/video/lists', data: data })
}

/**
 * @description 获取视频课程详情
 * @param { number } id
 * @return { Promise }
 */
export function getVideoDetail(data : { id : number }) {
	return request.get({ url: '/video/detail', data: data })
}