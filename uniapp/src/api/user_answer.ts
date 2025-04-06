import request from '@/utils/request'
/**
 * @description 获取成绩
 * @param {params} 参数
 * @return { Promise }
 */
export function addUserAnswer(data = {}) {
	return request.post({
		url: "/answer/add",
		data
	}, { isAuth: true })
}
/**
 * 获取答题结果
 * @param data 
 * @returns {Promise}
 */
export function getUserAnswer(data:Record<string,any>){
    return request.get({
        url: "/answer/detail",
        data
    },{isAuth: true})
}
/**
 * @description 获取用户答题记录
 * @return { Promise }
 */
export function getUserAnswerLists(data : Record<string, any>) {
	return request.get({ url: '/userAnswer/listForUser', data: data }, { isAuth: true })
}