<template>
    <div class="edit-popup">
        <popup ref="popupRef" :title="popupTitle" :async="true" width="550px" :clickModalClose="true"
            @confirm="handleSubmit" @close="handleClose">
            <el-form ref="formRef" :model="formData" label-width="84px" :rules="formRules">
                <el-form-item label="类目" prop="cid">
                    <el-select v-model="formData.cid" placeholder="请选择类目">
                        <el-option v-for="(item, index) of optionsData.cate_all" :key="index" :label="item.title"
                            :value="item.id" />
                    </el-select>
                </el-form-item>
                <el-form-item label="文章标题" prop="title">
                    <el-input v-model="formData.title" placeholder="请输入文章标题" />
                </el-form-item>
                <el-form-item label="音频地址" prop="src">
                    <material-picker v-model="formData.src" type="audio" />
                </el-form-item>
                <el-form-item label="是否显示" prop="isShow">
                    <el-radio-group v-model="formData.isShow" placeholder="请选择是否显示">
                        <el-radio v-for="(item, index) in dictData.YesOrNo" :key="index" :label="parseInt(item.value)"
                            :disabled="!item.status">
                            {{ item.name }}
                        </el-radio>
                    </el-radio-group>
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
import { AudioEdit, AudioAdd, AudioDetail } from '@/api/Audio'
import Popup from '@/components/popup/index.vue'
import feedback from '@/utils/feedback'
import type { PropType } from 'vue'
import { useDictOptions } from '@/hooks/useDictOptions'
import { AudioCateAll } from '@/api/AudioCate'
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
    return mode.value == 'edit' ? '编辑有声读物' : '新增有声读物'
})
const { optionsData } = useDictOptions<{
    cate_all: any[]
}>({
    cate_all: {
        api: AudioCateAll
    }
})
const formData = reactive({
    id: '',
    cid: '',
    title: '',
    src: '',
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
    cid: [
        {
            required: true,
            message: '请输入有声读物类目',
            trigger: ['blur']
        }
    ],
    title: [
        {
            required: true,
            message: '请输入文章标题',
            trigger: ['blur']
        }
    ],
    src: [
        {
            required: true,
            message: '请输入音频地址',
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
    mode.value == 'edit' ? await AudioEdit(data) : await AudioAdd(data)
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
    const data = await AudioDetail({
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
