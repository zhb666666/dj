import request from '@/utils/request'
import { CommentType } from "@/enums/appEnums"
/**
 * 获取业务的评论列表分页
 * @param data 
 * @returns 
 */
export function getServiceCommentLists(data:Record<string,any>){
    return request.get({url: "/comment/service_lists",data})
}
/**
 * @description 添加评论
 * @param {Number} serviceId
 * @param {CommentType} type
 * @param {String} content
 * @return { Promise }
 */
export function addComment(serviceId : Number, type : CommentType, content : String) {
	return request.post({
		url: "/comment/add", data: {
			"serviceId": serviceId,
			"type": type,
			"content": content
		}
	}, { isAuth: true })
}

/**
 * @description 删除评论
 * @param {Number} id
 * @return { Promise }
 */
export function delComment(id : Number) {
	return request.get({
		url: "/comment/del", data: {
			"id": id
		}
	}, { isAuth: true })
}