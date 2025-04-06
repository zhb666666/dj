<template>
    <div class="edit-popup">
        <popup ref="popupRef" :title="popupTitle" :async="true" width="550px" :clickModalClose="true"
            @confirm="handleSubmit" @close="handleClose">
            <el-form ref="formRef" :model="formData" label-width="84px" :rules="formRules">
                <el-form-item label="用户" prop="userId">
                    <el-select v-model="formData.userId">
                        <el-option v-for="(item, index) of optionsData.user_all" :key="index"
                            :label="`${item.username}-${item.sn}`" :value="item.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="姓名" prop="name">
                    <el-input v-model="formData.name" placeholder="请输入姓名" />
                </el-form-item>
                <el-form-item label="身份证" prop="idcard">
                    <el-input v-model="formData.idcard" placeholder="请输入身份证" />
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-select v-model="formData.gender" placeholder="请选择性别">
                        <el-option v-for="(item, index) of dictData.sex" :key="index" :label="item.name"
                            :value="parseInt(item.value)" />
                    </el-select>
                </el-form-item>
                <el-form-item label="出生日期" prop="birthdayTime">
                    <el-date-picker class="flex-1 !flex" v-model="formData.birthdayTime" type="datetime" clearable
                        value-format="YYYY-MM-DD hh:mm:ss" placeholder="请选择出生日期" />
                </el-form-item>
                <el-form-item label="民族" prop="nation">
                    <el-input v-model="formData.nation" placeholder="请输入民族" />
                </el-form-item>
                <el-form-item label="籍贯" prop="nativePlace">
                    <el-input v-model="formData.nativePlace" placeholder="请输入籍贯" />
                </el-form-item>
                <el-form-item label="地址" prop="address">
                    <el-input v-model="formData.address" placeholder="请输入地址" />
                </el-form-item>
                <el-form-item label="学历" prop="education">
                    <el-input v-model="formData.education" placeholder="请输入学历" />
                </el-form-item>
                <el-form-item label="毕业院校" prop="college">
                    <el-input v-model="formData.college" placeholder="请输入毕业院校" />
                </el-form-item>
                <el-form-item label="党籍状态" prop="archivesStatus">
                    <el-select v-model="formData.archivesStatus" placeholder="请选择党籍状态">
                        <el-option v-for="(item, index) of dictData.archives_status" :key="index" :label="item.name"
                            :value="parseInt(item.value)" />
                    </el-select>
                </el-form-item>
                <el-form-item label="党支部" prop="department">
                    <el-select v-model="formData.department" placeholder="请输入党支部">
                        <el-option v-for="(item, index) of optionsData.dept_all" :key="index" :label="item.name"
                            :value="item.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="缴纳党费金额" prop="partyFee">
                    <el-input type="number" v-model="formData.partyFee" placeholder="请输入缴纳党费金额" />
                </el-form-item>
                <el-form-item label="联系电话" prop="contact">
                    <el-input v-model="formData.contact" placeholder="请输入联系电话" />
                </el-form-item>
                <el-form-item label="是否为流动党员" prop="isFlow">
                    <el-select class="flex-1" v-model="formData.isFlow" placeholder="请选择是否为流动党员">
                        <el-option v-for="(item, index) in dictData.YesOrNo" :key="index" :label="item.name"
                            :value="parseInt(item.value)" clearable :disabled="!item.status" />
                    </el-select>
                </el-form-item>
                <el-form-item label="婚姻状态" prop="isMerry">
                    <el-select class="flex-1" v-model="formData.isMerry" placeholder="请选择婚姻状态">
                        <el-option v-for="(item, index) in dictData.YesOrNo" :key="index" :label="item.name"
                            :value="parseInt(item.value)" clearable :disabled="!item.status" />
                    </el-select>
                </el-form-item>
                <el-form-item label="加入党组织时间" prop="joinTime">
                    <el-date-picker class="flex-1 !flex" v-model="formData.joinTime" type="datetime" clearable
                        value-format="YYYY-MM-DD hh:mm:ss" placeholder="请选择加入党组织时间" />
                </el-form-item>
                <el-form-item label="成为正式党员时间" prop="realTime">
                    <el-date-picker class="flex-1 !flex" v-model="formData.realTime" type="datetime" clearable
                        value-format="YYYY-MM-DD hh:mm:ss" placeholder="请选择成为正式党员时间" />
                </el-form-item>
                <el-form-item label="主要经历" prop="see">
                    <editor v-model="formData.see" :height="500" />
                </el-form-item>
                <el-form-item label="发展党员情况" prop="situation">
                    <editor v-model="formData.situation" :height="500" />
                </el-form-item>
            </el-form>
        </popup>
    </div>
    <userPicker />
</template>
<script lang="ts" setup>
import type { FormInstance } from 'element-plus'
import { archivesEdit, archivesAdd, archivesDetail } from '@/api/archives'
import Popup from '@/components/popup/index.vue'
import feedback from '@/utils/feedback'
import type { PropType, Ref } from 'vue'
import { useDictOptions } from '@/hooks/useDictOptions'
import { deptAll } from '@/api/org/department'
import { userAll } from '@/api/consumer'
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
    return mode.value == 'edit' ? '编辑党员档案' : '新增党员档案'
})

const formData = reactive({
    id: '',
    userId: '',
    name: '',
    idcard: '',
    gender: '',
    birthdayTime: '',
    nation: '',
    nativePlace: '',
    address: '',
    education: '',
    college: '',
    archivesStatus: '',
    department: '',
    partyFee: 0.00,
    contact: '',
    isFlow: '',
    isMerry: '',
    joinTime: '',
    realTime: '',
    see: '',
    situation: '',
})

const { optionsData } = useDictOptions<{
    dept_all: any[],
    user_all: any[]
}>({
    dept_all: {
        api: deptAll
    },
    user_all: {
        api: userAll
    }
})

const formRules = {
    userId: [
        {
            required: true,
            message: '请输入用户id',
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
    idcard: [
        {
            required: true,
            message: '请输入身份证',
            trigger: ['blur']
        }
    ],
    gender: [
        {
            required: true,
            message: '请输入性别',
            trigger: ['blur']
        }
    ],
    birthdayTime: [
        {
            required: true,
            message: '请选择出生日期',
            trigger: ['blur']
        }
    ],
    nation: [
        {
            required: true,
            message: '请输入民族',
            trigger: ['blur']
        }
    ],
    nativePlace: [
        {
            required: true,
            message: '请输入籍贯',
            trigger: ['blur']
        }
    ],
    address: [
        {
            required: true,
            message: '请输入地址',
            trigger: ['blur']
        }
    ],
    education: [
        {
            required: true,
            message: '请输入学历',
            trigger: ['blur']
        }
    ],
    college: [
        {
            required: true,
            message: '请输入毕业院校',
            trigger: ['blur']
        }
    ],
    archivesStatus: [
        {
            required: true,
            message: '请选择党籍状态',
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
    contact: [
        {
            required: true,
            message: '请输入联系电话',
            trigger: ['blur']
        }
    ],
    isFlow: [
        {
            required: true,
            message: '请选择是否为流动党员',
            trigger: ['blur']
        }
    ],
    isMerry: [
        {
            required: true,
            message: '请选择婚姻状态',
            trigger: ['blur']
        }
    ],
    joinTime: [
        {
            required: true,
            message: '请选择加入党组织时间',
            trigger: ['blur']
        }
    ],
    realTime: [
        {
            required: true,
            message: '请选择成为正式党员时间',
            trigger: ['blur']
        }
    ],
    see: [
        {
            required: true,
            message: '请输入主要经历',
            trigger: ['blur']
        }
    ],
    situation: [
        {
            required: true,
            message: '请输入发展党员情况',
            trigger: ['blur']
        }
    ],
}

const handleSubmit = async () => {
    await formRef.value?.validate()
    const data: any = { ...formData }
    mode.value == 'edit' ? await archivesEdit(data) : await archivesAdd(data)
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
    const data = await archivesDetail({
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
