import request from "@/utils/request"

export function apiAddAccessRecord() {
    return request.post({ url: "/accessRecord/add" })
}
