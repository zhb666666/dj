<template>
    <div class="index-lists">
        <el-card class="!border-none" shadow="never">
            <el-form ref="formRef" class="mb-[-16px]" :model="queryParams" :inline="true">
                <el-form-item label="姓名" prop="name">
                    <el-input class="w-[280px]" v-model="queryParams.name" />
                </el-form-item>
                <el-form-item label="党员职位" prop="level">
                    <el-select v-model="queryParams.level">
                        <el-option v-for="(item, index) of optionsData.post_all" :key="index" :label="item.name"
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
                <el-form-item>
                    <el-button type="primary" @click="resetPage">查询</el-button>
                    <el-button @click="resetParams">重置</el-button>
                </el-form-item>
            </el-form>
        </el-card>
        <el-card class="!border-none mt-4" shadow="never">
            <div>
                <el-button v-perms="['member:add']" type="primary" @click="handleAdd()">
                    <template #icon>
                        <icon name="el-icon-Plus" />
                    </template>
                    新增
                </el-button>
            </div>
            <div class="flex">
                <div class="mr-4">
                    <el-table ref="tableRef" class="mt-4" highlight-current-row default-expand-all size="large"
                        v-loading="loading" :data="dept_lists" row-key="id"
                        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
                        @current-change="handleCurrentChange">
                        <el-table-column label="部门名称" prop="name" min-width="150" show-overflow-tooltip />
                        <el-table-column label="部门状态" prop="isStop" min-width="100">
                            <template #default="{ row }">
                                <el-tag class="ml-2" :type="row.isStop ? 'danger' : ''">
                                    {{ row.isStop ? '停用' : '正常' }}
                                </el-tag>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <div class="flex-1">
                    <el-table class="mt-4" size="large" v-loading="pager.loading" :data="pager.lists">
                        <el-table-column label="党支部" prop="dname" min-width="100" />

                        <el-table-column label="姓名" prop="name" min-width="100" />
                        <el-table-column label="党员头像" prop="avatar" min-width="100">
                            <template #default="row">
                                <el-image :src="row.avatar" />
                            </template>
                        </el-table-column>
                        <el-table-column label="党员级别" prop="pname" min-width="100" />
                        <el-table-column label="是否显示" prop="isShow" min-width="100">
                            <template #default="{ row }">
                                <dict-value :options="dictData.YesOrNo" :value="row.isShow" />
                            </template>
                        </el-table-column>
                        <el-table-column label="排序" prop="sort" min-width="100" />
                        <el-table-column label="操作" width="120" fixed="right">
                            <template #default="{ row }">
                                <el-button v-perms="['member:edit']" type="primary" link @click="handleEdit(row)">
                                    编辑
                                </el-button>
                                <el-button v-perms="['member:del']" type="danger" link @click="handleDelete(row.id)">
                                    删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="flex justify-end mt-4">
                        <pagination v-model="pager" @change="getLists" />
                    </div>
                </div>
            </div>
        </el-card>
        <edit-popup v-if="showEdit" ref="editRef" :dict-data="dictData" @success="getLists" @close="showEdit = false" />
    </div>
</template>
<script lang="ts" setup name="member">
import { memberDelete, memberLists } from '@/api/member'
import { useDictData, useDictOptions } from '@/hooks/useDictOptions'
import { usePaging } from '@/hooks/usePaging'
import feedback from '@/utils/feedback'
import EditPopup from './edit.vue'
import { deptAll, deptLists } from '@/api/org/department'
import { postAll } from '@/api/org/post'
import type ElTable from 'element-plus/lib/components/table'
const editRef = shallowRef<InstanceType<typeof EditPopup>>()
const tableRef = shallowRef<InstanceType<typeof ElTable>>()
const loading = ref(false)
const dept_lists = ref<any[]>([])


const showEdit = ref(false)
const queryParams = reactive({
    name: '',
    level: '',
    department: "1",
    isShow: '',
})

const { pager, getLists, resetPage, resetParams } = usePaging({
    fetchFun: memberLists,
    params: queryParams
})

const { dictData } = useDictData<{
    YesOrNo: any[]
}>(['YesOrNo'])

const { optionsData } = useDictOptions<{
    dept_all: Array<any>,
    post_all: Array<any>
}>({
    dept_all: {
        api: deptAll
    },
    post_all: {
        api: postAll
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
    await memberDelete({ id })
    feedback.msgSuccess('删除成功')
    getLists()
}
const handleCurrentChange = (row: any) => {
    queryParams.department = row?.id
    resetPage()
}
const getDeptLists = async () => {
    loading.value = true
    dept_lists.value = await deptLists()
    loading.value = false
}
getDeptLists()
getLists()
</script>
