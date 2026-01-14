import { getClient } from '@/utils/env'
import { FetchOptions } from 'ohmyfetch'
import { RequestCodeEnum, RequestMethodsEnum } from '@/enums/requestEnums'
import feedback from '@/utils/feedback'
import { merge } from 'lodash-es'
import { Request } from './request'
import { getApiPrefix, getApiUrl, getVersion } from '../env'
import { useUserStore } from '@/stores/user'
import {
    PopupTypeEnum,
    useAccount
} from '~~/layouts/components/account/useAccount'

export function createRequest(opt?: Partial<FetchOptions>) {
    const userStore = useUserStore()
    const { setPopupType, toggleShowPopup } = useAccount()
    const defaultOptions: FetchOptions = {
        // 基础接口地址
        baseURL: getApiUrl(),
        //请求头
        headers: {
            version: getVersion()
        },
        retry: 2,
        async onRequest({ options }) {
            const { withToken } = options.requestOptions
            const headers = options.headers || {}
            
            // 确保设置Content-Type
            if (!headers['Content-Type'] && !options.body) {
                headers['Content-Type'] = 'application/json'
            }
            
            // 添加token
            if (withToken) {
                const token = userStore.token
                console.log('添加token:', token ? 'Bearer ' + token : '无token')
                if (token) {
                    headers['like-token'] = token
                }
            }
            
            // 添加终端和版本信息
            options.headers['terminal'] = getClient()
            options.headers['version'] = getVersion()
            
            // 输出请求信息用于调试
            console.log('请求配置:', {
                url: options.url,
                method: options.method,
                headers: options.headers,
                withToken: withToken
            })
            
            options.headers = headers
        },
        requestOptions: {
            apiPrefix: getApiPrefix(),
            isTransformResponse: true,
            isReturnDefaultResponse: false,
            withToken: true, // 默认所有请求都需要token
            isParamsToData: true,
            forceToken: true, // 新增：强制token验证
            requestInterceptorsHook(options) {
                console.log('请求拦截器:', options)
                
                const { apiPrefix, isParamsToData, forceToken } = options.requestOptions
                
                // 拼接请求前缀
                if (apiPrefix) {
                    options.url = `${apiPrefix}${options.url}`
                }
                
                const params = options.params || {}
                // POST请求下如果无data，则将params视为data
                if (
                    isParamsToData &&
                    !Reflect.has(options, 'body') &&
                    options.method?.toUpperCase() === RequestMethodsEnum.POST
                ) {
                    options.body = params
                    options.params = {}
                }
                
                // 强制验证token
                if (forceToken && !userStore.token) {
                    console.error('❌ 缺少必需的token！')
                    throw new Error('请先登录')
                }
                
                return options
            },
            async responseInterceptorsHook(response, options) {
                const { isTransformResponse, isReturnDefaultResponse } =
                    options.requestOptions
                //返回默认响应，当需要获取响应头及其他数据时可使用
                if (isReturnDefaultResponse) {
                    return response
                }
                // 是否需要对数据进行处理
                if (!isTransformResponse) {
                    return response._data
                }
                
                // 检查响应状态
                if (!response.ok) {
                    console.error('❌ HTTP错误:', response.status, response.statusText)
                    throw new Error(`请求失败: ${response.status} ${response.statusText}`)
                }
                
                const { code, data, msg } = response._data
                
                // 记录响应信息用于调试
                console.log('📡 响应信息:', { code, msg, data })
                
                switch (code) {
                    case RequestCodeEnum.SUCCESS:
                        return data
                    case RequestCodeEnum.PARAMS_TYPE_ERROR:
                    case RequestCodeEnum.PARAMS_VALID_ERROR:
                    case RequestCodeEnum.REQUEST_METHOD_ERROR:
                    case RequestCodeEnum.ASSERT_ARGUMENT_ERROR:
                    case RequestCodeEnum.ASSERT_MYBATIS_ERROR:
                    case RequestCodeEnum.LOGIN_ACCOUNT_ERROR:
                    case RequestCodeEnum.LOGIN_DISABLE_ERROR:
                    case RequestCodeEnum.NO_PERMISSTION:
                    case RequestCodeEnum.FAILED:
                    case RequestCodeEnum.SYSTEM_ERROR:
                        if (msg) {
                            msg && feedback.msgError(msg)
                        }
                        return Promise.reject(new Error(msg || '请求失败'))

                    case RequestCodeEnum.TOKEN_INVALID:
                    case RequestCodeEnum.TOKEN_EMPTY:
                        console.log('🔑 Token无效，需要重新登录')
                        userStore.logout()
                        setPopupType(PopupTypeEnum.LOGIN)
                        toggleShowPopup(true)
                        return Promise.reject(new Error('请重新登录'))

                    default:
                        return data
                }
            },
            responseInterceptorsCatchHook(err) {
                console.error('❌ 请求捕获到错误:', err)
                
                // 获取当前请求配置
                const config = useRuntimeConfig().public
                const userStore = useUserStore()
                
                // 处理不同类型的错误
                if (err.name === 'TypeError' && err.message.includes('fetch')) {
                    console.error('❌ 网络连接失败:', err.message)
                    console.log('请检查:')
                    console.log('1. 后端服务是否启动')
                    console.log('2. API地址是否正确:', config.apiUrl)
                    console.log('3. 网络连接是否正常')
                    
                    // 显示网络诊断信息
                    console.log('🔍 当前配置:', {
                        apiUrl: config.apiUrl,
                        baseURL: config.apiUrl,
                        tokenExists: !!userStore.token,
                        currentUrl: window.location.href
                    })
                    
                    feedback.msgError('网络连接失败，请检查后端服务是否正常启动')
                    return new Error('网络连接失败，请稍后重试')
                }
                
                if (err.response) {
                    console.error('❌ HTTP响应错误:', err.response.status, err.response.statusText)
                    feedback.msgError(`请求失败: ${err.response.status} ${err.response.statusText}`)
                    return err
                }
                
                // 默认错误处理
                console.error('❌ 未知错误:', err)
                feedback.msgError('请求失败，请稍后重试')
                return err
            }
        }
    }
    return new Request(
        // 深度合并
        merge(defaultOptions, opt || {})
    )
}
