<template>
	<view class="audio-detail bg-white">
		<view class="p-[20rpx] flex flex-col items-center">
			<image :src="audioData.cate.image" mode="scaleToFill" class="w-[500rpx] h-[397rpx] rounded-[14rpx]">
			</image>
			<view class="pt-[20rpx] text-3xl w-[560rpx] text-center">{{ audioData.cate.title }}</view>
		</view>
		<view class="p-[20rpx]">
			<view class="article-title text-2xl font-medium flex items-center">音频章节</view>
			<view v-if="audioData.list.length > 0" v-for="(item, index) in audioData.list" :key="index"
				class="flex py-[20rpx] border-bottom">
				<u-icon
					:name="(item?.play == PlayIconEnum.Play && audioPlayer.audioId == item.id) ? PlayIconEnum.Pause : PlayIconEnum.Play"
					size="60rpx" class="text-muted flex-shrink" @click="handlePlayerToogle(item, index)"></u-icon>
				<view class="flex-1 pl-[24rpx]">
					<view class="text-xl text-gray-400">{{ item.title }}</view>
					<view class="text-sm text-muted">{{ timeFormat(item.createTime) }}</view>
				</view>
			</view>
			<view v-else class="py-[20rpx] border-bottom">
				<u-empty />
			</view>
		</view>
	</view>
</template>

<script lang="ts" setup>
	import { ref, reactive, computed, onUnmounted } from 'vue'
	import { onHide, onLoad } from '@dcloudio/uni-app'
	import { getAudioList } from '@/api/audio'
	enum PlayIconEnum {
		Play = "play-circle",
		Pause = "pause-circle"
	}
	const audioData = ref<{
		cate : any,
		list : any[]
	}>({
		cate: {},
		list: []
	})
	const audioPlayer = reactive<{ audioId : string, status : PlayIconEnum }>({
		audioId: "0",
		status: PlayIconEnum.Pause
	})
	// 音频播放上下文
	const innerAudioContext = uni.createInnerAudioContext();
	let audioCid = ''
	// 播放错误回调
	innerAudioContext.onError(() => {
		audioPlayer.audioId = "0"
	})
	const getData = async (cid : any) => {
		audioData.value = await getAudioList({ cid })
	}
	//时间格式化
	const timeFormat = (date : string) => {
		return uni.$u.timeFormat(date, "yyyy-mm-dd")
	}
	const handlePlayerToogle = (data : any, index : number) => {
		if (audioData.value.list[index]["play"] == PlayIconEnum.Pause || audioData.value.list[index]["play"] == undefined) {
			// 播放
			audioData.value.list[index]["play"] = PlayIconEnum.Play
			// 当前播放音频赋值
			audioPlayer.audioId = data.id
			innerAudioContext.src = data.src
			innerAudioContext.play()
		} else {
			// 暂停
			audioPlayer.audioId = "0"
			audioData.value.list[index]["play"] = PlayIconEnum.Pause
			innerAudioContext.pause()
		}

	}
	onUnmounted(() => {
		// 页面销毁时 上下文跟着销毁
		innerAudioContext.destroy()
	})
	onLoad((options : any) => {
		audioCid = options.cid
		getData(audioCid)
	})
</script>

<style lang="scss" scoped>
	.border-bottom {
		border-bottom: 1px solid #f8f8f8;
	}

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
</style>