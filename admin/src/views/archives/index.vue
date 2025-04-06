<template>
    <div class="index-lists">
        <el-card class="!border-none" shadow="never">
            <el-form ref="formRef" class="mb-[-16px]" :model="queryParams" :inline="true">
                <el-form-item label="姓名" prop="name">
                    <el-input class="w-[280px]" v-model="queryParams.name" />
                </el-form-item>
                <el-form-item label="身份证" prop="idcard">
                    <el-input class="w-[280px]" v-model="queryParams.idcard" />
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-select class="w-[280px]" v-model="queryParams.gender">
                        <el-option v-for="(item, index) of dictData.sex" :key="index" :label="item.name"
                            :value="item.value" />
                    </el-select>
                </el-form-item>
                <el-form-item label="党支部" prop="department">
                    <el-select v-model="queryParams.department" class="w-[280px]">
                        <el-option v-for="(item, index) of optionsData.dept_all" :key="index" :label="item.name"
                            :value="item.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="联系电话" prop="contact">
                    <el-input class="w-[280px]" v-model="queryParams.contact" />
                </el-form-item>
                <el-form-item label="婚姻状态" prop="isMerry">
                    <el-select v-model="queryParams.isMerry" class="w-[280px]" clearable>
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
                <el-button v-perms="['archives:add']" type="primary" @click="handleAdd()">
                    <template #icon>
                        <icon name="el-icon-Plus" />
                    </template>
                    新增
                </el-button>
            </div>
            <el-table class="mt-4" size="large" v-loading="pager.loading" :data="pager.lists">
                <el-table-column label="用户名" prop="username" min-width="100" />
                <el-table-column label="姓名" prop="name" min-width="100" />
                <el-table-column label="性别" prop="gender" min-width="100">
                    <template #default="{ row }">
                        <dict-value :options="dictData.sex" :value="row.gender" />
                    </template>
                </el-table-column>
                <el-table-column label="民族" prop="nation" min-width="100" />
                <el-table-column label="党支部" prop="dname" min-width="100" />
                <el-table-column label="联系电话" prop="contact" min-width="100" />
                <el-table-column label="党籍状态" prop="archivesStatus" min-width="100">
                    <template #default="{ row }">
                        <dict-value :options="dictData.archives_status" :value="row.archivesStatus" />
                    </template>
                </el-table-column>
                <el-table-column label="婚姻状态" prop="isMerry" min-width="100">
                    <template #default="{ row }">
                        <dict-value :options="dictData.YesOrNo" :value="row.isMerry" />
                    </template>
                </el-table-column>
                <el-table-column label="加入党组织时间" prop="joinTime" min-width="100"></el-table-column>
                <el-table-column label="成为正式党员时间" prop="realTime" min-width="100" />
                <el-table-column label="创建时间" prop="createTime" min-width="100" />
                <el-table-column label="操作" width="120" fixed="right">
                    <template #default="{ row }">
                        <el-button v-perms="['archives:edit']" type="primary" link @click="handleEdit(row)">
                            编辑
                        </el-button>
                        <el-button v-perms="['archives:del']" type="danger" link @click="handleDelete(row.id)">
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
<script lang="ts" setup name="archives">
import { archivesDelete, archivesLists } from '@/api/archives'
import { useDictData, useDictOptions } from '@/hooks/useDictOptions'
import { usePaging } from '@/hooks/usePaging'
import feedback from '@/utils/feedback'
import EditPopup from './edit.vue'
import { deptAll } from '@/api/org/department'
import { userAll } from '@/api/consumer'
const editRef = shallowRef<InstanceType<typeof EditPopup>>()
const showEdit = ref(false)
const queryParams = reactive({
    name: '',
    idcard: '',
    gender: '',
    department: '',
    contact: '',
    isMerry: '',
    joinTime: '',
    realTimeStart: '',
    realTimeEnd: '',
    see: '',
    situation: '',
})

const { pager, getLists, resetPage, resetParams } = usePaging({
    fetchFun: archivesLists,
    params: queryParams
})

const { dictData } = useDictData<{
    YesOrNo: any[],
    sex: any[],
    archives_status: any[]
}>(['YesOrNo', "sex", "archives_status"])

const { optionsData } = useDictOptions<{
    dept_all: Array<any>,
}>({
    dept_all: {
        api: deptAll
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
    await archivesDelete({ id })
    feedback.msgSuccess('删除成功')
    getLists()
}

getLists()
</script>
