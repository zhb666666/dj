import request from '@/utils/request'


/**
 * @description 获取有声读物带音频列表详情
 * @param { number } cid
 * @return { Promise }
 */
export function getAudioList(data : { cid : number }) {
	return request.get({ url: '/audio/list', data: data })
}