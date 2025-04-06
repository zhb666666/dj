<template>
    <view>
        <z-paging auto-show-back-to-top ref="paging" v-model="dataList" @query="queryList" fixed>
            <view class="p-[20rpx] flex flex-wrap justify-between">
                <audio-card v-for="(item, index) in dataList" :key="index" :cid="item.id" :item="item"></audio-card>
            </view>
        </z-paging>
    </view>
    <tabbar />
</template>

<script lang="ts" setup>
import { ref, shallowRef } from "vue"
import { getCateList } from "@/api/audio_cate"
// 类目id初始化
const paging = shallowRef<any>(null)
const dataList = ref([])
const queryList = async (pageNo: number, pageSize: number) => {
    try {
        const { lists } = await getCateList({
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
</script>

<style></style>