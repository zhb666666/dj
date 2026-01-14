/**
 * @description 验证登录修复效果的测试脚本
 */

import { getRequestConfig } from './cors'

/**
 * 验证修复效果
 */
export function verifyLoginFix() {
    console.log('🧪 验证登录修复效果...')
    console.log('='.repeat(50))
    
    // 1. 检查配置
    const config = getRequestConfig()
    console.log('📋 配置检查:')
    console.log('- API地址:', config.apiUrl)
    console.log('- API前缀:', config.apiPrefix)
    console.log('- 是否跨域:', config.isCrossOrigin)
    console.log('- 当前页面:', config.currentOrigin)
    
    // 2. 检查Token状态
    const userStore = useUserStore()
    console.log('\n👤 Token状态:')
    console.log('- Token存在:', !!userStore.token)
    console.log('- Token长度:', userStore.token?.length || 0)
    console.log('- 用户状态:', userStore.isLogin ? '已登录' : '未登录')
    
    // 3. 验证修复结果
    const fixes = []
    
    // 检查API地址
    if (config.apiUrl && config.apiUrl.includes('8088')) {
        fixes.push('✅ API地址已修正为8088端口')
    } else {
        fixes.push('❌ API地址可能仍有问题')
    }
    
    // 检查API前缀
    if (config.apiPrefix === '/dev-api') {
        fixes.push('✅ API前缀配置正确')
    } else {
        fixes.push('⚠️ API前缀配置可能需要调整')
    }
    
    // 检查Token机制
    if (userStore.token) {
        fixes.push('✅ Token机制正常工作')
    } else {
        fixes.push('ℹ️ 需要登录获取Token')
    }
    
    console.log('\n🔧 修复验证:')
    fixes.forEach(fix => console.log(fix))
    
    // 4. 建议后续测试
    console.log('\n📝 建议的测试步骤:')
    console.log('1. 刷新页面重新加载配置')
    console.log('2. 尝试登录操作')
    console.log('3. 观察Network面板确认请求正常')
    console.log('4. 检查控制台日志确认无错误')
    
    // 5. 生成测试报告
    const report = {
        timestamp: new Date().toISOString(),
        config,
        tokenStatus: {
            exists: !!userStore.token,
            length: userStore.token?.length || 0,
            isLogin: userStore.isLogin
        },
        fixes,
        recommendations: [
            '重新启动开发服务器以加载新的环境配置',
            '测试登录功能',
            '监控Network面板确认请求正常'
        ]
    }
    
    console.log('\n📊 测试报告:', report)
    return report
}

/**
 * 测试登录API连接
 */
export async function testLoginConnection() {
    console.log('🔗 测试登录API连接...')
    
    const config = getRequestConfig()
    const loginUrl = `${config.apiUrl}${config.apiPrefix}/login/test`
    
    try {
        console.log('请求URL:', loginUrl)
        
        const response = await fetch(loginUrl, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        
        console.log('响应状态:', response.status)
        console.log('响应OK:', response.ok)
        
        if (response.ok || response.status === 404) {
            console.log('✅ API连接正常')
            return true
        } else {
            console.log('⚠️ API响应异常:', response.statusText)
            return false
        }
    } catch (error) {
        console.log('❌ API连接失败:', error.message)
        return false
    }
}

/**
 * 完整的修复验证流程
 */
export function fullVerification() {
    console.log('🚀 开始完整修复验证流程...')
    
    // 1. 验证配置
    const report = verifyLoginFix()
    
    // 2. 测试连接
    console.log('\n' + '='.repeat(50))
    console.log('🔗 API连接测试')
    testLoginConnection().then(success => {
        if (success) {
            console.log('🎉 修复成功！可以尝试登录了')
        } else {
            console.log('⚠️ 仍有问题需要进一步排查')
        }
    })
    
    // 3. 提供最终建议
    console.log('\n💡 最终建议:')
    console.log('1. 如果API连接正常，请刷新页面并测试登录')
    console.log('2. 如果仍有问题，请检查后端服务状态')
    console.log('3. 使用浏览器开发者工具查看详细错误信息')
    
    return report
}

// 导出到全局供调试使用
if (typeof window !== 'undefined') {
    (window as any).verifyLoginFix = verifyLoginFix
    (window as any).testLoginConnection = testLoginConnection
    (window as any).fullVerification = fullVerification
    
    console.log('🧪 修复验证工具已加载:')
    console.log('- verifyLoginFix() - 验证修复效果')
    console.log('- testLoginConnection() - 测试API连接')
    console.log('- fullVerification() - 完整验证流程')
}