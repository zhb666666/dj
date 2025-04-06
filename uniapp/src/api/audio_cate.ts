import request from "@/utils/request";

/**
 * @description 获取有声读物类目列表
 * @return { Promise }
 */
export function getCateList(data : Record<string, any>) {
	return request.get({ url: '/audioCate/list', data: data })
}