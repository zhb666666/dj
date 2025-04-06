<template>
    <div class="index-lists">
        <el-card class="!border-none" shadow="never">
            <el-form ref="formRef" class="mb-[-16px]" :model="queryParams" :inline="true">
                <el-form-item label="有声读物类目" prop="cid">
                    <el-select v-model="queryParams.cid" class="w-[280px]">
                        <el-option v-for="(item, index) of optionsData.cate_all" :key="index" :label="item.title"
                            :value="item.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="是否显示" prop="isShow">
                    <el-select v-model="queryParams.isShow" class="w-[280px]" clearable>
                        <el-option label="全部" value="" />
                        <el-option v-for="(item, index) in dictData.YesOrNo" :key="index" :label="item.name"
                            :value="item.value" />
                    </el-select>
                </el-form-item>
                <el-form-item label="标题" prop="title">
                    <el-input class="w-[280px]" v-model="queryParams.title" />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="resetPage">查询</el-button>
                    <el-button @click="resetParams">重置</el-button>
                </el-form-item>
            </el-form>
        </el-card>
        <el-card class="!border-none mt-4" shadow="never">
            <div>
                <el-button v-perms="['Audio:add']" type="primary" @click="handleAdd()">
                    <template #icon>
                        <icon name="el-icon-Plus" />
                    </template>
                    新增
                </el-button>
            </div>
            <el-table class="mt-4" size="large" v-loading="pager.loading" :data="pager.lists">
                <el-table-column label="类目" prop="categoryTitle" min-width="100" />
                <el-table-column label="文章标题" prop="title" min-width="100" />
                <el-table-column label="是否显示" prop="isShow" min-width="100">
                    <template #default="{ row }">
                        <dict-value :options="dictData.YesOrNo" :value="row.isShow" />
                    </template>
                </el-table-column>
                <el-table-column label="排序" prop="sort" min-width="100" />
                <el-table-column label="创建时间" prop="createTime" min-width="100" />
                <el-table-column label="更新时间" prop="updateTime" min-width="100" />
                <el-table-column label="操作" width="120" fixed="right">
                    <template #default="{ row }">
                        <el-button v-perms="['Audio:edit']" type="primary" link @click="handleEdit(row)">
                            编辑
                        </el-button>
                        <el-button v-perms="['Audio:del']" type="danger" link @click="handleDelete(row.id)">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="flex justify-end mt-4">
                <pagination v-model="pager" @change="getLists" />
            </div>
        </el-card>
        <edit-popup v-if="showEdit" ref="editRef" :dict-data="dictData" @success="getLists" @close="showEdit = false" />
    </div>
</template>
<script lang="ts" setup name="Audio">
import { AudioDelete, AudioLists } from '@/api/Audio'
import { useDictData, useDictOptions } from '@/hooks/useDictOptions'
import { usePaging } from '@/hooks/usePaging'
import feedback from '@/utils/feedback'
import EditPopup from './edit.vue'
import { AudioCateAll } from '@/api/AudioCate'
const editRef = shallowRef<InstanceType<typeof EditPopup>>()
const showEdit = ref(false)
const queryParams = reactive({
    cid: '',
    isShow: '',
    title: '',
})

const { pager, getLists, resetPage, resetParams } = usePaging({
    fetchFun: AudioLists,
    params: queryParams
})

const { dictData } = useDictData<{
    YesOrNo: any[]
}>(['YesOrNo'])

const { optionsData } = useDictOptions<{
    cate_all: any[]
}>({
    cate_all: {
        api: AudioCateAll
    }
})


const handleAdd = async () => {
    showEdit.value = true
    await nextTick()
    editRef.value?.open('add')
}

const handleEdit = async (data: any) => {
    showEdit.value = true
    await nextTick()
    editRef.value?.open('edit')
    editRef.value?.getDetail(data)
}

const handleDelete = async (id: number) => {
    await feedback.confirm('确定要删除？')
    await AudioDelete({ id })
    feedback.msgSuccess('删除成功')
    getLists()
}

getLists()
</script>
