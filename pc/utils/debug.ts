/**
 * @description 登录错误调试和修复工具
 */

import { testRequestConfig } from './test'

/**
 * 登录错误诊断
 */
export function diagnoseLoginError() {
    console.log('🔍 登录错误诊断开始...')
    console.log('='.repeat(50))
    
    // 1. 检查环境配置
    console.log('=== 环境配置检查 ===')
    const runtimeConfig = useRuntimeConfig().public
    console.log('当前环境配置:', {
        apiUrl: runtimeConfig.apiUrl,
        apiPrefix: runtimeConfig.apiPrefix,
        baseUrl: runtimeConfig.baseUrl,
        client: runtimeConfig.client,
        version: runtimeConfig.version
    })
    
    // 2. 测试请求配置
    const config = testRequestConfig()
    
    // 3. 检查当前URL
    console.log('=== 当前页面信息 ===')
    console.log('当前URL:', window.location.href)
    console.log('当前Origin:', window.location.origin)
    console.log('Search参数:', window.location.search)
    
    // 4. 检查Node环境
    console.log('=== Node环境信息 ===')
    console.log('NODE_ENV:', process.env.NODE_ENV)
    
    // 5. 检查可能的问题
    const issues = []
    
    // 检查API URL配置
    if (!runtimeConfig.apiUrl || runtimeConfig.apiUrl.includes('localhost')) {
        issues.push('❌ API URL配置可能不正确，指向了localhost')
    }
    
    // 检查baseURL是否与apiUrl一致
    if (runtimeConfig.baseUrl && runtimeConfig.apiUrl && !runtimeConfig.apiUrl.includes(runtimeConfig.baseUrl)) {
        issues.push('⚠️ baseURL与apiUrl可能不一致')
    }
    
    // 生成修复建议
    console.log('='.repeat(50))
    console.log('📋 问题诊断:')
    
    if (issues.length === 0) {
        console.log('✅ 配置看起来正常，问题可能在其他方面')
    } else {
        issues.forEach(issue => console.log(issue))
    }
    
    console.log('='.repeat(50))
    console.log('🔧 修复建议:')
    console.log('1. 检查 .env 文件配置')
    console.log('2. 确保 NUXT_API_URL 指向正确的后端地址')
    console.log('3. 重新构建和启动应用')
    
    return {
        config,
        issues,
        runtimeConfig
    }
}

/**
 * 临时修复请求URL
 */
export function fixRequestUrl() {
    const config = useRuntimeConfig()
    const currentApiUrl = config.public.apiUrl
    
    console.log('🔧 尝试修复API URL...')
    console.log('当前API URL:', currentApiUrl)
    
    // 根据错误信息显示的地址，推测后端可能运行在8889端口
    const serverAddress = 'http://47.99.138.193:8889'
    const devApiPrefix = '/dev-api'
    
    console.log('建议的API配置:')
    console.log(`NUXT_API_URL=${serverAddress}`)
    console.log(`NUXT_API_PREFIX=${devApiPrefix}`)
    
    // 返回修复建议
    return {
        suggestedApiUrl: serverAddress,
        suggestedApiPrefix: devApiPrefix,
        fixMethod: 'updateEnvFile'
    }
}

/**
 * 创建正确的环境配置文件
 */
export function createCorrectEnvFile() {
    const serverAddress = 'http://47.99.138.193:8889'
    const envContent = `# PC端环境配置
# 基于错误诊断生成

# 基础URL配置
NUXT_BASE_URL=/

# API配置 - 根据错误信息中的地址
NUXT_API_URL=${serverAddress}
NUXT_API_PREFIX=/dev-api

# 客户端标识
NUXT_CLIENT=pc

# 版本信息
NUXT_VERSION=1.0.0

# 是否启用SSR
NUXT_SSR=false

# 调试模式
NUXT_DEBUG=true`
    
    console.log('📝 建议的环境配置内容:')
    console.log(envContent)
    
    return envContent
}

/**
 * 快速测试登录接口
 */
export async function quickTestLogin() {
    console.log('🧪 快速测试登录接口...')
    
    try {
        // 测试API连接
        const testUrl = '/dev-api/login/test' // 假设的测试接口
        console.log('测试URL:', testUrl)
        
        // 尝试一个简单的请求
        const response = await $fetch(testUrl, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        
        console.log('✅ API连接正常:', response)
    } catch (error) {
        console.log('❌ API连接失败:', error)
        console.log('这可能是因为:')
        console.log('1. 后端服务未启动')
        console.log('2. API地址不正确')
        console.log('3. 跨域配置问题')
    }
}

/**
 * 完整的登录错误修复流程
 */
export function fixLoginError() {
    console.log('🚀 开始完整修复流程...')
    
    // 1. 诊断问题
    const diagnosis = diagnoseLoginError()
    
    // 2. 提供修复建议
    const fixSuggestion = fixRequestUrl()
    
    // 3. 提供配置文件
    const envConfig = createCorrectEnvFile()
    
    console.log('='.repeat(50))
    console.log('📋 修复步骤:')
    console.log('1. 停止当前应用')
    console.log('2. 更新 .env 文件内容:')
    console.log(envConfig)
    console.log('3. 重新启动应用')
    console.log('4. 再次测试登录')
    
    return {
        diagnosis,
        fixSuggestion,
        envConfig,
        steps: [
            '停止应用',
            '更新.env文件',
            '重启应用',
            '测试登录'
        ]
    }
}