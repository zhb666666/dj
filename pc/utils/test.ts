/**
 * @description 跨域和Token配置测试工具
 */

import { validateTokenConfig, getRequestConfig, isCrossOrigin } from './cors'

/**
 * 测试请求配置
 */
export function testRequestConfig() {
    console.log('=== 请求配置测试 ===')
    
    const config = getRequestConfig()
    console.log('当前配置:', config)
    
    // 测试跨域状态
    console.log('跨域状态:', {
        isCrossOrigin: isCrossOrigin(),
        currentOrigin: window.location.origin,
        apiUrl: config.apiUrl
    })
    
    return config
}

/**
 * 测试Token配置
 */
export function testTokenConfig() {
    console.log('=== Token配置测试 ===')
    
    const isValid = validateTokenConfig()
    console.log('Token验证结果:', isValid)
    
    return isValid
}

/**
 * 测试API请求
 */
export async function testApiRequest() {
    console.log('=== API请求测试 ===')
    
    try {
        // 验证配置
        testRequestConfig()
        testTokenConfig()
        
        // 测试一个简单的API调用
        console.log('正在测试API请求...')
        
        // 这里可以调用一个测试API
        // const response = await $request.get({ url: '/test' })
        // console.log('API响应:', response)
        
        console.log('✅ API请求测试完成')
    } catch (error) {
        console.error('❌ API请求测试失败:', error)
    }
}

/**
 * 综合诊断
 */
export function runDiagnostics() {
    console.log('🔍 开始运行综合诊断...')
    console.log('='.repeat(50))
    
    // 1. 测试基础配置
    const config = testRequestConfig()
    
    // 2. 测试Token
    const tokenValid = testTokenConfig()
    
    // 3. 检查关键环境变量
    console.log('=== 环境变量检查 ===')
    const runtimeConfig = useRuntimeConfig().public
    console.log('API URL:', runtimeConfig.apiUrl)
    console.log('API Prefix:', runtimeConfig.apiPrefix)
    console.log('Client:', runtimeConfig.client)
    console.log('Version:', runtimeConfig.version)
    
    // 4. 检查用户状态
    console.log('=== 用户状态检查 ===')
    const userStore = useUserStore()
    console.log('用户登录状态:', userStore.isLogin)
    console.log('Token存在:', !!userStore.token)
    console.log('Token长度:', userStore.token?.length || 0)
    
    // 5. 生成诊断报告
    console.log('='.repeat(50))
    console.log('📊 诊断报告:')
    console.log(`- 跨域状态: ${config.isCrossOrigin ? '是' : '否'}`)
    console.log(`- Token有效: ${tokenValid ? '是' : '否'}`)
    console.log(`- 用户登录: ${userStore.isLogin ? '是' : '否'}`)
    
    if (config.isCrossOrigin && !tokenValid) {
        console.log('⚠️ 检测到跨域问题且Token无效，请检查:')
        console.log('  1. 后端CORS配置')
        console.log('  2. 前端Token存储')
        console.log('  3. 登录流程')
    }
    
    console.log('='.repeat(50))
    console.log('🔍 诊断完成')
    
    return {
        config,
        tokenValid,
        userLoggedIn: userStore.isLogin,
        issues: []
    }
}

/**
 * 修复建议
 */
export function getFixSuggestions() {
    console.log('💡 修复建议:')
    
    const suggestions = []
    
    // 检查环境配置
    const runtimeConfig = useRuntimeConfig().public
    if (!runtimeConfig.apiUrl) {
        suggestions.push('❌ 请检查NUXT_API_URL环境变量配置')
    }
    
    // 检查Token
    const userStore = useUserStore()
    if (!userStore.token) {
        suggestions.push('❌ 请先登录获取Token')
    }
    
    // 检查跨域
    if (isCrossOrigin()) {
        suggestions.push('⚠️ 检测到跨域，请确保:')
        suggestions.push('   1. 后端CORS配置正确')
        suggestions.push('   2. 请求头包含like-token')
        suggestions.push('   3. Cookie配置支持跨域')
    }
    
    suggestions.forEach(suggestion => console.log(suggestion))
    
    return suggestions
}