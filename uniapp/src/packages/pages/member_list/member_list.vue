<template>
	<z-paging auto-show-back-to-top ref="paging" v-model="dataList" @query="queryList" fixed>
		<view class="px-[20rpx] bg-white">
			<view v-for="(item, index) in dataList" :key="index" class="flex item items-center py-[20rpx]"
				hover-class="u-hover-class" @click="handleClickItem(item, index)">
				<image :src="item.avatar" mode="scaleToFill" class="w-[200rpx] h-[260rpx] shrink"></image>
				<view class="flex-1 ml-[20rpx] h-[260rpx] py-[10rpx] flex flex-col justify-between">
					<view class="text-xl font-bold">{{ item.name }}</view>
					<view>{{ item.pname?item.pname:"未分配" }} | {{ item.dname?item.dname:"未分配" }}</view>
					<view class="desc text-sm text-muted">{{ item.description }}</view>
				</view>
			</view>
		</view>
	</z-paging>
</template>

<script lang="ts" setup>
	import { ref, shallowRef } from "vue"
	import { getMemberList } from "@/api/member"
	const paging = shallowRef<any>(null)
	const dataList = ref([])
	const queryList = async (pageNo : number, pageSize : number) => {
		try {
			const { lists } = await getMemberList({
				pageNo,
				pageSize
			})
			paging.value.complete(lists)
		} catch (e) {
			console.log('报错=>', e)
			//TODO handle the exception
			paging.value.complete(false)
		}
	}
	// 点击某个项
	const handleClickItem = (item : any, index : number) => {
		// 给子页面提供数据
		// let item_str: string = encodeURIComponent(JSON.stringify(item))
		uni.navigateTo({
			url: "/packages/pages/member_detail/member_detail?id=" + item.id
		})
	}
</script>

<style lang="scss" scoped>
	.item {
		.desc {
			overflow: hidden;
			text-overflow: ellipsis;
			-webkit-line-clamp: 3;
			display: -webkit-box;
			-webkit-box-orient: vertical;
		}
	}
</style>