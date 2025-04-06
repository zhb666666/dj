import request from '@/utils/request'
/**
 * @description 获取积分商品列表
 * @return { Promise }
 */
export function getIntegralShopLists(data : Record<string, any>) {
	return request.get({ url: '/integralShop/list', data: data })
}
/**
 * @description 获取积分商品详情
 * @return { Promise }
 */
export function getIntegralShopDetail(data : { id : number }) {
	return request.get({ url: '/integralShop/detail', data: data })
}
