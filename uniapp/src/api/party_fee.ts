import request from '@/utils/request'
/**
 * @description 创建订单
 * @return { Promise }
 */
export function createOrder(data :Record<string,any>) {
	return request.post({
		url: "/partyFee/createOrder", data
	}, { isAuth: true })
}

export function listForUser(data: Record<string,any>){
    return request.get({url:"/partyFee/listForUser",data},{isAuth: true})
}

export function detailForUser(month_id: Number){
    return request.get({url: "/partyFee/detailForUser",data: {month_id}},{isAuth: true})
}