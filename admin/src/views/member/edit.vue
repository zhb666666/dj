<template>
    <div class="edit-popup">
        <popup ref="popupRef" :title="popupTitle" :async="true" width="550px" :clickModalClose="true"
            @confirm="handleSubmit" @close="handleClose">
            <el-form ref="formRef" :model="formData" label-width="84px" :rules="formRules">

                <el-form-item label="姓名" prop="name">
                    <el-input v-model="formData.name" placeholder="请输入姓名" />
                </el-form-item>
                <el-form-item label="党员头像" prop="avatar">
                    <material-picker v-model="formData.avatar" />
                </el-form-item>
                <el-form-item label="党员岗位" prop="level">
                    <el-select v-model="formData.level" placeholder="请选择党员岗位">
                        <el-option v-for="(item, index) of optionsData.post_all" :key="index" :label="item.name"
                            :value="item.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="党支部" prop="department">
                    <el-select v-model="formData.department" placeholder="请选择党支部">
                        <el-option v-for="(item, index) of optionsData.dept_all" :key="index" :label="item.name"
                            :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="个人简介" prop="description">
                    <editor v-model="formData.description" :height="250" mode="simple" />
                </el-form-item>
                <el-form-item label="是否显示" prop="isShow">
                    <el-select class="flex-1" v-model="formData.isShow" placeholder="请选择是否显示">
                        <el-option v-for="(item, index) in dictData.YesOrNo" :key="index" :label="item.name"
                            :value="parseInt(item.value)" clearable :disabled="!item.status" />
                    </el-select>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                    <el-input v-model="formData.sort" placeholder="请输入排序" />
                </el-form-item>
            </el-form>
        </popup>
    </div>
</template>
<script lang="ts" setup>
import type { FormInstance } from 'element-plus'
import { memberEdit, memberAdd, memberDetail } from '@/api/member'
import Popup from '@/components/popup/index.vue'
import feedback from '@/utils/feedback'
import type { PropType } from 'vue'
import { useDictOptions } from '@/hooks/useDictOptions'
import { deptAll } from '@/api/org/department'
import { postAll } from '@/api/org/post'
defineProps({
    dictData: {
        type: Object as PropType<Record<string, any[]>>,
        default: () => ({})
    }
})
const emit = defineEmits(['success', 'close'])
const formRef = shallowRef<FormInstance>()
const popupRef = shallowRef<InstanceType<typeof Popup>>()
const mode = ref('add')
const popupTitle = computed(() => {
    return mode.value == 'edit' ? '编辑党员' : '新增党员'
})
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
const formData = reactive({
    id: '',
    name: '',
    avatar: '',
    level: '',
    department: '',
    description: '',
    isShow: '',
    sort: '',
})

const formRules = {
    id: [
        {
            required: true,
            message: '请输入id',
            trigger: ['blur']
        }
    ],
    name: [
        {
            required: true,
            message: '请输入姓名',
            trigger: ['blur']
        }
    ],
    avatar: [
        {
            required: true,
            message: '请输入党员头像',
            trigger: ['blur']
        }
    ],
    level: [
        {
            required: true,
            message: '请输入党员级别',
            trigger: ['blur']
        }
    ],
    department: [
        {
            required: true,
            message: '请输入党支部',
            trigger: ['blur']
        }
    ],
    description: [
        {
            required: true,
            message: '请输入个人简介',
            trigger: ['blur']
        }
    ],
    isShow: [
        {
            required: true,
            message: '请选择是否显示',
            trigger: ['blur']
        }
    ],
    sort: [
        {
            required: true,
            message: '请输入排序',
            trigger: ['blur']
        }
    ],
}

const handleSubmit = async () => {
    await formRef.value?.validate()
    const data: any = { ...formData }
    mode.value == 'edit' ? await memberEdit(data) : await memberAdd(data)
    popupRef.value?.close()
    feedback.msgSuccess('操作成功')
    emit('success')
}

const open = (type = 'add') => {
    mode.value = type
    popupRef.value?.open()
}

const setFormData = async (data: Record<string, any>) => {
    for (const key in formData) {
        if (data[key] != null && data[key] != undefined) {
            //@ts-ignore
            formData[key] = data[key]
        }
    }
}

const getDetail = async (row: Record<string, any>) => {
    const data = await memberDetail({
        id: row.id
    })
    setFormData(data)
}

const handleClose = () => {
    emit('close')
}

defineExpose({
    open,
    setFormData,
    getDetail
})
</script>
