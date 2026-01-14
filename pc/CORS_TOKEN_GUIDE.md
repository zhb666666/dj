# PC端登录Token跨域问题解决方案

## 问题描述
PC端登录后请求没有携带token，可能的原因包括：
1. 跨域配置问题
2. 请求拦截器配置错误
3. Token存储或读取问题
4. 环境配置不正确

## 解决方案

### 1. 后端CORS配置
已在 `/server/like-front/src/main/java/com/mdd/front/config/WebMvcConfig.java` 中优化：
- 支持OPTIONS预检请求
- 明确指定允许的请求头
- 启用凭据传递
- 缓存预检请求

### 2. 前端请求拦截器优化
已在 `/pc/utils/http/index.ts` 中增强：
- 添加详细调试日志
- 强制Token验证
- 改进请求头设置
- Content-Type自动设置

### 3. 跨域检测工具
创建了 `/pc/utils/cors.ts` 提供：
- 跨域状态检测
- Token配置验证
- 请求头强制设置

## 排查步骤

### 步骤1：检查环境配置
确保 `.env` 文件配置正确：
```bash
NUXT_API_URL=http://localhost:8088  # 你的后端API地址
NUXT_API_PREFIX=/dev-api            # API前缀
NUXT_CLIENT=pc
NUXT_VERSION=1.0.0
```

### 步骤2：验证Token存储
在浏览器控制台运行：
```javascript
// 检查token是否存在
console.log('Token:', useUserStore().token)

// 检查跨域配置
console.log('跨域配置:', getRequestConfig())
```

### 步骤3：验证请求头
打开Network面板，观察请求：
1. 检查请求头是否包含 `like-token`
2. 查看Console中的调试信息
3. 验证预检请求(OPTIONS)是否成功

### 步骤4：强制Token验证
如果仍然有问题，可以在关键API调用前添加：
```javascript
import { validateTokenConfig, forceSetHeaders } from '@/utils/cors'

// 验证token配置
if (!validateTokenConfig()) {
    console.error('Token配置有问题！')
}

// 强制设置请求头
const headers = forceSetHeaders({})
```

## 常见问题解决

### 问题1：跨域预检失败
**症状**：OPTIONS请求返回错误
**解决**：
- 检查后端CORS配置中的allowedMethods
- 确保包含所有需要的HTTP方法

### 问题2：自定义请求头被阻止
**症状**：like-token头不存在
**解决**：
- 检查后端CORS配置中的allowedHeaders
- 确保包含'like-token'头

### 问题3：Cookie无法跨域传递
**症状**：token存在但请求中无token
**解决**：
- 检查是否使用Cookie存储token
- 如果是跨域，考虑使用LocalStorage
- 或者配置CORS支持凭据

## 调试建议

1. **启用详细日志**：控制台会输出请求详细信息
2. **使用跨域工具**：调用`validateTokenConfig()`进行验证
3. **检查Network面板**：确认请求头设置
4. **验证响应头**：检查后端是否正确处理token

## 部署建议

1. **生产环境**：确保CORS配置针对特定域名
2. **安全考虑**：不要在生产环境使用通配符Origin
3. **性能优化**：适当设置maxAge缓存预检请求
4. **监控**：添加token验证失败的监控告警