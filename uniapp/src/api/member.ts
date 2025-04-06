import request from '@/utils/request'
/**
 * @description 获取党员列表
 * @return { Promise }
 */
export function getMemberList(data : Record<string, any>) {
	return request.get({ url: '/member/list', data: data })
}

/**
 * 获取党员详情
 * @param member_id 党员id
 * @returns response
 */
export function getMemberDetail(member_id: Number){
    return request.get({url: "/member/detail",data:{id:member_id}})
}