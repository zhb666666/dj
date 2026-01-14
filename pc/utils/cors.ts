/**
 * @description 跨域配置工具
 */

/**
 * 检查是否为跨域请求
 */
export function isCrossOrigin(): boolean {
    const currentOrigin = window.location.origin
    const apiUrl = useRuntimeConfig().public.apiUrl
    
    try {
        const apiOrigin = new URL(apiUrl).origin
        return currentOrigin !== apiOrigin
    } catch {
        return false
    }
}

/**
 * 获取请求配置
 */
export function getRequestConfig() {
    const config = useRuntimeConfig().public
    return {
        apiUrl: config.apiUrl,
        apiPrefix: config.apiPrefix,
        isCrossOrigin: isCrossOrigin(),
        currentOrigin: window.location.origin,
        tokenHeader: 'like-token'
    }
}

/**
 * 验证token配置
 */
export function validateTokenConfig(): boolean {
    const userStore = useUserStore()
    const config = getRequestConfig()
    
    console.log('=== Token配置验证 ===')
    console.log('当前域名:', config.currentOrigin)
    console.log('API域名:', config.apiUrl)
    console.log('是否跨域:', config.isCrossOrigin)
    console.log('Token值:', userStore.token)
    console.log('Token长度:', userStore.token?.length || 0)
    console.log('Token类型:', typeof userStore.token)
    
    // 检查token是否存在
    if (!userStore.token) {
        console.warn('⚠️ Token不存在！')
        return false
    }
    
    // 检查token格式
    if (typeof userStore.token !== 'string' || userStore.token.length === 0) {
        console.warn('⚠️ Token格式不正确！')
        return false
    }
    
    console.log('✅ Token配置验证通过')
    return true
}

/**
 * 强制设置请求头
 */
export function forceSetHeaders(headers: Record<string, any>) {
    const userStore = useUserStore()
    
    // 确保token头存在
    if (userStore.token) {
        headers['like-token'] = userStore.token
    }
    
    // 添加其他必要的头
    headers['terminal'] = useRuntimeConfig().public.client
    headers['version'] = useRuntimeConfig().public.version
    
    console.log('强制设置的头信息:', headers)
    return headers
}