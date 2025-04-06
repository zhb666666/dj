<template>
	<view class="px-[20rpx] bg-white">
		<view class="flex py-[20rpx]">
			<image :src="data.avatar" mode="scaleToFill" class="w-[200rpx] h-[260rpx]"></image>
			<view class="flex-1 ml-[20rpx]">
				<view class="text-xl">{{ data.name }}</view>
				<view class="text-muted text-sm pt-[20rpx]">{{ data.pname?data.pname:"未分配" }} |
					{{ data.dname?data.dname:"未分配" }}</view>
			</view>
		</view>
		<view class="py-[40rpx]">
			<view class="text-2xl font-bold">个人简介</view>
			<view class="mt-[20rpx]">
				<u-parse :html="data.description"></u-parse>
			</view>
		</view>
	</view>
</template>

<script lang="ts" setup>
	import { ref } from "vue"
	import { onLoad } from "@dcloudio/uni-app"
	import { getMemberDetail } from "@/api/member"
	const data = ref<any>({})
	let member_id = 0
	const loadData = async () => {
		const response = await getMemberDetail(member_id)
		data.value = response
	}
	onLoad((options : any) => {
		member_id = Number(options.id)
		loadData()
	})
</script>

<style></style>