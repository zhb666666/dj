/**
 * @description 登录错误修复工具
 */

// 创建一键诊断和修复函数
export function loginErrorFix() {
    console.log('🚀 开始登录错误诊断和修复...')
    console.log('='.repeat(60))
    
    // 1. 诊断当前配置
    console.log('📋 步骤1: 诊断当前配置')
    diagnoseCurrentConfig()
    
    // 2. 检测后端连接
    console.log('\n🔍 步骤2: 检测后端连接')
    checkBackendConnections()
    
    // 3. 生成修复方案
    console.log('\n🛠️ 步骤3: 生成修复方案')
    generateFixSolution()
    
    console.log('\n' + '='.repeat(60))
    console.log('✅ 诊断完成，请根据建议进行修复')
}

function diagnoseCurrentConfig() {
    const config = useRuntimeConfig().public
    
    console.log('当前配置:')
    console.log('- API地址:', config.apiUrl)
    console.log('- API前缀:', config.apiPrefix)
    console.log('- 客户端:', config.client)
    console.log('- 当前URL:', window.location.href)
    console.log('- 当前Origin:', window.location.origin)
    
    // 检测问题
    const issues = []
    
    if (config.apiUrl && config.apiUrl.includes('8889')) {
        issues.push('❌ 检测到可能的端口错误: 8889')
    }
    
    if (config.apiUrl && config.apiUrl.includes('8088')) {
        console.log('✅ API地址配置正确')
    } else if (config.apiUrl) {
        console.log('⚠️ API地址需要验证:', config.apiUrl)
    }
    
    if (issues.length > 0) {
        console.log('\n发现的问题:')
        issues.forEach(issue => console.log(issue))
    }
}

async function checkBackendConnections() {
    const testAddresses = [
        'http://47.99.138.193:8088',
        'http://47.99.138.193:8889',
        'http://localhost:8088'
    ]
    
    console.log('测试后端连接...')
    
    for (const address of testAddresses) {
        try {
            console.log(`测试 ${address}...`)
            const controller = new AbortController()
            const timeoutId = setTimeout(() => controller.abort(), 3000)
            
            const response = await fetch(`${address}/dev-api/test`, {
                method: 'GET',
                signal: controller.signal
            })
            
            clearTimeout(timeoutId)
            
            if (response.ok || response.status === 404) {
                console.log(`✅ ${address} - 连接正常 (状态: ${response.status})`)
            } else {
                console.log(`⚠️ ${address} - 响应异常 (状态: ${response.status})`)
            }
        } catch (error) {
            console.log(`❌ ${address} - 连接失败`)
        }
    }
}

function generateFixSolution() {
    console.log('🛠️ 修复方案:')
    
    console.log('\n方案1: 使用正确的后端地址')
    console.log('1. 编辑 .env.development 文件')
    console.log('2. 设置正确的API地址:')
    console.log('   NUXT_API_URL=http://47.99.138.193:8088')
    console.log('   NUXT_API_PREFIX=/dev-api')
    
    console.log('\n方案2: 如果使用本地开发')
    console.log('   NUXT_API_URL=http://localhost:8088')
    console.log('   NUXT_API_PREFIX=/dev-api')
    
    console.log('\n方案3: 检查后端服务状态')
    console.log('1. 确认后端服务是否启动')
    console.log('2. 确认端口配置是否正确')
    console.log('3. 检查防火墙设置')
    
    console.log('\n操作步骤:')
    console.log('1. 停止前端开发服务器')
    console.log('2. 更新 .env.development 文件')
    console.log('3. 重新启动: npm run dev')
    console.log('4. 测试登录功能')
}

// 导出修复后的环境配置
export function getCorrectEnvConfig() {
    return {
        development: `# PC端开发环境配置
NUXT_BASE_URL=/
NUXT_API_URL=http://47.99.138.193:8088
NUXT_API_PREFIX=/dev-api
NUXT_CLIENT=pc
NUXT_VERSION=1.0.0
NUXT_SSR=false
NUXT_DEBUG=true`,
        
        local: `# PC端本地开发配置
NUXT_BASE_URL=/
NUXT_API_URL=http://localhost:8088
NUXT_API_PREFIX=/dev-api
NUXT_CLIENT=pc
NUXT_VERSION=1.0.0
NUXT_SSR=false
NUXT_DEBUG=true`
    }
}

// 快速测试函数
export async function quickBackendTest() {
    const addresses = [
        'http://47.99.138.193:8088',
        'http://47.99.138.193:8889'
    ]
    
    console.log('🧪 快速后端测试...')
    
    for (const address of addresses) {
        try {
            console.log(`测试连接: ${address}`)
            const start = Date.now()
            
            const response = await fetch(`${address}/dev-api/login/test`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            
            const time = Date.now() - start
            console.log(`✅ ${address} - 响应时间: ${time}ms (状态: ${response.status})`)
            
            if (response.ok) {
                console.log('🎉 这个地址可以正常连接后端!')
                return address
            }
        } catch (error) {
            console.log(`❌ ${address} - 连接失败:`, error.message)
        }
    }
    
    console.log('⚠️ 所有地址都无法连接，请检查后端服务')
    return null
}

// 在浏览器控制台中运行这些函数
if (typeof window !== 'undefined') {
    (window as any).loginErrorFix = loginErrorFix
    (window as any).quickBackendTest = quickBackendTest
    (window as any).getCorrectEnvConfig = getCorrectEnvConfig
    
    console.log('🔧 登录修复工具已加载:')
    console.log('- loginErrorFix() - 完整诊断')
    console.log('- quickBackendTest() - 快速后端测试')
    console.log('- getCorrectEnvConfig() - 获取正确配置')
}