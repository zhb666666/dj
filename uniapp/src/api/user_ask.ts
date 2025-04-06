import request from '@/utils/request'
/**
 * @description 提交问卷
 * @param {data} 
 * @return { Promise }
 */
export function addUserAsk(data : Record<string, any>) {
	return request.post({ url: '/user_ask/add', data: data }, { isAuth: true })
}