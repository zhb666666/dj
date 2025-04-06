<template>
    <view>
        <video :src="state.detail.src" class="w-full"></video>
        <view class="main px-[20rpx]">
            <view class="title text-3xl">{{ state.detail.title }}</view>
            <view class="text-sm text-muted my-1">{{ timeFormat(state.detail.create_time) }}</view>
            <view class="flex items-center" @click="handleLike">
                <u-icon name="thumb-up-fill" color="#fa3534" size="40rpx" v-if="state.like.status == 1"></u-icon>
                <u-icon name="thumb-up" size="40rpx" v-else></u-icon>
                <view class="">{{ state.like_count }}人赞</view>
            </view>
            <view class="mt-4">
                <view class="article-title text-2xl font-medium flex items-center">简介</view>
                <view class="text-base">{{ state.detail.description }}</view>
            </view>
            <view class="mt-4">
                <view class="article-title text-2xl font-medium flex items-center">评论({{ state.comment_count }})
                </view>
                <comment-list :serviceId="Number(videoId)" ref="comment_list_ref"
                    @change="onHandleChangeComment"></comment-list>
            </view>
        </view>
        <view class="fixed px-[20rpx] bg-white py-[20rpx] input flex justify-between">
            <u-avatar size="60"></u-avatar>
            <view class="input-area rounded text-muted bg-gray-400" @click="handleClickInput">说点什么...</view>
        </view>
        <comment-popup v-model:show="state.show_comment_popup" :serviceId="videoId" @success="onAddCommentSuccess" />
    </view>
</template>

<script lang="ts" setup>
import { ref, shallowRef } from 'vue'
import { onLoad, onReachBottom } from "@dcloudio/uni-app"
import { getVideoDetail } from '@/api/video'
import { like, dislike } from "@/api/like"
import { LikeType } from '@/enums/appEnums'
import commentList from "./components/comment-list.vue";
const state = ref<{
    detail: any,
    like: any,
    comment_count: number,
    like_count: number,
    show_comment_popup: boolean
}>({
    detail: {},
    like: {},
    comment_count: 0,
    like_count: 0,
    show_comment_popup: false
})

let videoId = ref<Number>(0)
const comment_list_ref = shallowRef()

const getData = async (id: any) => {
    const response = await getVideoDetail({ id })
    state.value.detail = response.detail
    state.value.like = response.like
    state.value.comment_count = response.comment_count
    state.value.like_count = response.like_count
}
//时间格式化
const timeFormat = (date: string) => {
    return uni.$u.timeFormat(date, "yyyy-mm-dd")
}
// 点赞
const handleLike = async () => {
    if (state.value.like.status == 1) {
        // 取消点赞
        uni.$u.throttle(async () => {
            await dislike(state.value.detail.id, LikeType.VIDEO)
            state.value.like.status = 0
            state.value.like_count -= 1
        }, 1000)
    } else {
        // 点赞
        uni.$u.throttle(async () => {
            await like(state.value.detail.id, LikeType.VIDEO)
            state.value.like.status = 1
            state.value.like_count += 1
        }, 1000)
    }
}
const handleClickInput = () => {
    // 打开评论弹出层
    state.value.show_comment_popup = true
}
// 评论成功回调
const onAddCommentSuccess = (response: any) => {
    state.value.show_comment_popup = false
    getData(videoId.value)
    comment_list_ref.value.reloadData()
}

const onHandleChangeComment = (response: any) => {
    getData(videoId.value)
}
onReachBottom(() => {
    comment_list_ref.value.nextPage()
})
onLoad((options: any) => {
    videoId.value = options.id
    getData(videoId.value)
})
</script>

<style lang="scss" scoped>
.article-title {
    &::before {
        content: '';
        width: 8rpx;
        height: 34rpx;
        display: block;
        margin-right: 10rpx;
        background: $u-type-primary;
    }
}

.main {
    padding-bottom: 130rpx;
}

.comments-box {
    height: calc(100vh - 880rpx - env(safe-area-inset-bottom));
}

.input {
    bottom: 0;
    left: 0;
    right: 0;
    border-top: 1px solid #f8f8f8;

    .input-area {
        width: 640rpx;
        height: 60rpx;
        line-height: 60rpx;
        padding: 0 20rpx;
        --tw-bg-opacity: 1;
        background-color: rgba(229, 231, 235, var(--tw-bg-opacity));
    }
}
</style>