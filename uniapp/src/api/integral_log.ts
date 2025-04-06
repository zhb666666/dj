import request from '@/utils/request'
/**
 * @description 获取用户积分记录
 * @return { Promise }
 */
export function getIntegralLogLists(data : Record<string, any>) {
	return request.get({ url: '/integralLog/listForUser', data: data }, { isAuth: true })
}