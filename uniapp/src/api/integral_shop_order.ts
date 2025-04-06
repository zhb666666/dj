import request from '@/utils/request'
/**
 * @description 获取用户积分商品订单分页
 * @return { Promise }
 */
export function getIntegralShopOrderLists(data : Record<string, any>) {
	return request.get({ url: '/integralShopOrder/listForUser', data: data }, { isAuth: true })
}
/**
 * @description 兑换商品
 * @return { Promise }
 */
export function addIntegralShopOrder(data : Record<string, any>) {
	return request.post({ url: "/integralShopOrder/exchange", data }, { isAuth: true })
}