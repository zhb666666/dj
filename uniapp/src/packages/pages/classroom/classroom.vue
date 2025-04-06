<template>
    <z-paging auto-show-back-to-top ref="paging" v-model="dataList" @query="queryList" fixed>
        <block v-for="(item, index) of dataList" :key="index">
            <classroom-card :item="item" />
        </block>
    </z-paging>
    <tabbar />
</template>

<script lang="ts" setup>
import { ref, shallowRef } from 'vue'
import { getVideoList } from '@/api/video'


const paging = shallowRef<any>(null)
const dataList = ref([])
const queryList = async (pageNo: number, pageSize: number) => {
    try {
        const { lists } = await getVideoList({
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

<style lang="scss" scoped></style>