<template>
    <view class="flex mt-[12rpx]" v-for="(item, index) in state.items" :key="index">
        <u-avatar :src="item.userAvatar" size="70" class="flex-shrink"></u-avatar>
        <view class="flex-1 ml-[20rpx]">
            <view class="text-base text-muted mb-[15rpx]">{{ item.userNickname }}</view>
            <view>{{ item.content }}</view>
            <view class="flex items-center text-muted">{{ item.create_time }}
                <view class="ml-3" @click="handleClickReply(item.id)">回复</view>
            </view>
            <view>
                <view v-show="state.comment_index == index">
                    <view class="flex mt-[12rpx]" v-for="(reply, reply_index) in item.replies" :key="reply_index">
                        <u-avatar :src="reply.userAvatar" size="70" class="flex-shrink"></u-avatar>
                        <view class="flex-1 ml-[20rpx]">
                            <view class="text-base text-muted mb-[15rpx]">{{ reply.userNickname }}</view>
                            <view>{{ reply.content }}</view>
                            <view class="flex items-center text-muted">{{ timeFormat(reply.createTime) }}
                                <view class="ml-3" @click="handleClickReply(item.id)">回复
                                </view>
                            </view>
                        </view>
                        <view class="text-muted text-base" @click="handleDelReply(reply.id)"
                            v-if="hasPermission(reply.userId)">删除</view>
                    </view>
                </view>
                <view class="text-muted" v-if="item.replies.length > 0 && state.comment_index != index"
                    @click="handleExpandToogle(index)">
                    展开{{ item.replies.length }}条回复<u-icon name="arrow-down-fill" class="ml-[10rpx]"></u-icon>
                </view>
                <view class="text-muted" v-if="item.replies.length > 0 && state.comment_index == index"
                    @click="handleExpandToogle(index)">收起<u-icon name="arrow-up-fill" class="ml-[10rpx]"></u-icon>
                </view>
            </view>
        </view>
        <view class="text-muted text-base" @click="handleDelComment(item.id)" v-if="hasPermission(item.userId)">删除
        </view>
    </view>
    <reply-popup v-model:id="state.comment_id" v-model:show="state.reply_popup_show" @success="onSuccessReply" />
</template>
<script setup lang="ts">
import { usePermission } from '@/hooks/useUser'
import { ref, onMounted, defineProps, defineExpose } from 'vue';
import { getServiceCommentLists, delComment } from "@/api/comment"

import replyPopup from '@/components/reply-popup/reply-popup.vue';
import { delReply } from '@/api/reply';
const props = defineProps({
    serviceId: {
        type: Number,
        default: 0
    }
})

const emit = defineEmits<{
    (event: "change", e: any): void
}>()
//时间格式化
const timeFormat = (date: string) => {
    return uni.$u.timeFormat(date, "yyyy-mm-dd")
}
let { hasPermission } = usePermission()
const state = ref<{
    items: Array<any>,
    page: {
        status: String,
        pageNo: Number,
        pageSize: Number,
    },
    reply_popup_show: boolean,
    comment_id: Number,
    comment_index: Number
}>({
    items: [],
    page: {
        status: "loadmore",
        pageNo: 1,
        pageSize: 10
    },
    reply_popup_show: false,
    comment_id: 0,
    comment_index: -1
})

// 加载评论列表
const loadLists = async () => {
    const page = state.value.page
    page.status = "loading"
    const response = await getServiceCommentLists({
        pageNo: state.value.page.pageNo,
        pageSize: state.value.page.pageSize,
        service_id: props.serviceId
    })

    if (response.count <= (page.pageNo * page.pageSize)) {
        page.status = "nomore"
    } else {
        page.status = "loadmore"
    }
    state.value.items = state.value.items.concat(response.lists)

}
// 处理用户点击回复事件
const handleClickReply = (comment_id: Number, type = "comment") => {
    state.value.reply_popup_show = true
    state.value.comment_id = comment_id
}


const handleDelComment = (id: Number) => {
    uni.showModal({
        title: "提示",
        content: "确认删除该条评论吗?",
        success: async (res) => {
            if (res.cancel) return
            let response = await delComment(id)
            uni.$u.toast("删除成功")
            // 重新加载数据
            reloadData()
            emit("change", response)
        }
    })
}
const resetPage = () => {
    state.value.items = []
    state.value.page.status = "loadmore"
    state.value.page.pageNo = 1
}

const reloadData = () => {
    resetPage()
    loadLists()
}
// 点击展开和收起评论
const handleExpandToogle = (index: Number) => {
    if (state.value.comment_index == index) {
        // 收起
        state.value.comment_index = -1
    } else {
        // 展开
        state.value.comment_index = index
    }
}
const onSuccessReply = () => {
    reloadData()
}
const handleDelReply = async (reply_id: number) => {
    uni.showModal({
        title: "提示",
        content: "确定要删除此条回复吗？",
        success: async (res) => {
            if (res.cancel) return
            const response = await delReply(reply_id)
            reloadData()
            emit("change", response)
        }
    })
}
const nextPage = async () => {
    if (state.value.page.status == "nomore") return
    state.value.page.pageNo += 1
    await loadLists()
}
defineExpose({
    reloadData,
    nextPage
})

onMounted(() => {
    loadLists()
})
</script>
<style lang="scss" scoped></style>