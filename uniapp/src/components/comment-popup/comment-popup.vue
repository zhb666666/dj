<template>
    <view style="z-index: 99999;">
        <u-popup v-model="showPopup" mode="bottom">
            <view class="p-[20rpx]">
                <view class="flex items-center justify-between">
                    <view class="text-2xl">发布评论</view>
                    <view class="py-[20rpx] bg-primary text-white px-[50rpx] rounded" hover-class="u-hover-class"
                        @click="handleSeedComment">发送</view>
                </view>
                <u-input type="textarea" v-model="commentState.content" placeholder="请输入评论内容"></u-input>
            </view>
        </u-popup>
    </view>
</template>

<script lang="ts" setup>
import { reactive, defineEmits, defineProps, computed } from "vue"
import { addComment } from "@/api/comment"
import { CommentType } from '@/enums/appEnums'
const props = defineProps({
    show: {
        type: Boolean,
        default: false
    },
    serviceId: {
        type: Number,
        default: 0
    }
})
const emit = defineEmits<{
    (event: 'update:show', show: boolean): void
    (event: 'success', value: any): void
}>()
const showPopup = computed({
    get() {
        return props.show
    },
    set(value) {
        emit("update:show", value)
    }
})
const commentState = reactive<{
    show: boolean,
    content: String,
    currentIndex: Number
}>({
    show: false,
    content: "",
    currentIndex: -1,
})
// 处理评论事件
const handleSeedComment = async () => {
    let content = uni.$u.trim(commentState.content, "all")
    if (content.length < 4) {
        return uni.$u.toast("评论内容不能小于4个字符")
    }
    if (content.length > 191) {
        return uni.$u.toast("评论内容不能大于191个字符")
    }
    try {
        let response = await addComment(props.serviceId, CommentType.VIDEO, content)
        // 重新加载数据
        emit("success", response)
        uni.$u.toast("评论成功")
    } catch (e) {
        //TODO handle the exception
        console.log(e)

    } finally {
        commentState.show = false
        commentState.content = ""
    }
}

</script>

<style scoped></style>