<template>
    <u-popup v-model="showPopup" mode="bottom">
        <view class="p-[20rpx]">
            <view class="flex items-center justify-between">
                <view class="text-2xl">回复评论</view>
                <view class="py-[20rpx] bg-primary text-white px-[50rpx] rounded" hover-class="u-hover-class"
                    @click="handleSeedReply">回复</view>
            </view>
            <u-input type="textarea" v-model="state.content" placeholder="请输入回复内容"></u-input>
        </view>
    </u-popup>
</template>
<script setup lang="ts">
import { defineEmits, defineProps, ref, computed } from "vue"
import { addReply } from "@/api/reply"
const props = defineProps({
    show: {
        type: Boolean,
        default: false
    },
    id: {
        type: Number,
        default: 0
    }
})
const emit = defineEmits<{
    (event: "update:show", show: boolean): void
    (event: "update:id", id: number): void
    (event: "change"): void
    (event: "success", response: any): void
}>()
const state = ref<any>({
    content: ""
})
const showPopup = computed({
    get() {
        return props.show
    },
    set(val) {
        emit("update:show", val)
    }
})
const computedId = computed({
    get() {
        return props.id
    },
    set(val) {
        emit("update:id", val)
    }
})
// 处理用户回复事件
const handleSeedReply = async () => {
    let content = uni.$u.trim(state.value.content, "all")
    if (content.length < 4) {
        return uni.$u.toast("回复内容不能小于4个字符")
    }
    if (content.length > 191) {
        return uni.$u.toast("回复内容不能大于191个字符")
    }
    try {
        let response = await addReply(computedId.value, content)
        // 重新加载数据
        emit("success", response)
        uni.$u.toast("回复成功")
    } catch (e) {
        //TODO handle the exception
    } finally {
        showPopup.value = false
        state.value.content = ""
    }
}
// 处理删除回复
const handleDelReply = (id: Number) => {
    uni.showModal({
        title: "提示",
        content: "确认删除该条回复吗?",
        success: async (res) => {
            if (res.cancel) returnπ
            let response = await delReply(id)
            uni.$u.toast("删除成功")
            // 重新加载数据
            getData(videoId)
        }
    })
}
</script>