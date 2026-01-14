# PC端登录Token问题及CORS配置修复说明

## 问题分析

### 1. Token名称不匹配
**问题根源：**
- 后端Sa-Token配置的token名称：`open_party_java` (在 `like-front/src/main/resources/application.yml`)
- PC前端原本发送的请求头名称：`like-token`
- Sa-Token通过 `StpUtil.getTokenValue()` 从请求头或Cookie中读取名为 `open_party_java` 的值

**影响：**
后端拦截器无法获取到token，导致所有需要认证的接口返回 TOKEN_EMPTY (332) 错误。

### 2. CORS跨域配置不完整
**问题根源：**
- 使用 `allowedOrigins("*")` 不支持携带credentials（cookies）
- 缺少 `allowCredentials(true)` 配置
- 缺少自定义响应头暴露配置 `exposedHeaders`
- 缺少 OPTIONS 方法支持

**影响：**
- 跨域请求时Cookie无法被设置和发送
- 自定义请求头可能被浏览器拦截
- 预检请求(OPTIONS)可能失败

### 3. Promise错误处理不完整
**问题根源：**
在 `pc/utils/http/index.ts` 中，TOKEN失效时返回 `Promise.reject()` 没有传递错误信息。

**影响：**
控制台出现 "Uncaught (in promise) undefined" 错误。

## 修复方案

### 1. 修改后端CORS配置

#### like-front (PC端API服务)
文件：`server/like-front/src/main/java/com/mdd/front/config/WebMvcConfig.java`

```java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOriginPatterns("*")  // 改为 allowedOriginPatterns 支持 credentials
            .allowedHeaders("*")
            .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")  // 添加 OPTIONS
            .allowCredentials(true)  // 允许携带凭证
            .exposedHeaders("open_party_java", "like-token", "Authorization", "Content-Type")  // 暴露自定义头
            .maxAge(3600);
}
```

#### like-admin (管理后台API服务)
文件：`server/like-admin/src/main/java/com/mdd/admin/config/WebMvcConfig.java`

```java
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedHeaders("*")
            .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
            .allowCredentials(true)
            .exposedHeaders("open_party_java_admin", "Authorization", "Content-Type")
            .maxAge(3600);
}
```

### 2. 修改PC前端Token请求头

文件：`pc/utils/http/index.ts`

```typescript
// 添加token
if (withToken) {
    const token = userStore.token
    if (token) {
        // Sa-Token token-name: open_party_java
        headers['open_party_java'] = token
    }
}
```

### 3. 修复Promise错误处理

文件：`pc/utils/http/index.ts`

```typescript
case RequestCodeEnum.TOKEN_INVALID:
case RequestCodeEnum.TOKEN_EMPTY:
    userStore.logout()
    setPopupType(PopupTypeEnum.LOGIN)
    toggleShowPopup(true)
    return Promise.reject(msg || '登录已过期，请重新登录')  // 添加错误信息
```

## 技术要点说明

### Sa-Token配置
- **like-front**: `token-name: open_party_java`
- **like-admin**: `token-name: open_party_java_admin`
- Token可以通过请求头、Cookie、或URL参数传递
- 后端通过 `StpUtil.getTokenValue()` 自动获取token

### CORS关键配置
1. **allowedOriginPatterns vs allowedOrigins**
   - `allowedOrigins("*")` + `allowCredentials(true)` 会报错
   - 必须使用 `allowedOriginPatterns("*")` 或指定具体域名

2. **allowCredentials(true)**
   - 允许浏览器发送Cookie和自定义认证头
   - 前端请求需要设置 `credentials: 'include'` (Nuxt3的$fetch默认配置)

3. **exposedHeaders**
   - 自定义响应头需要显式暴露才能被前端JavaScript访问
   - 包括Sa-Token的token名称

4. **OPTIONS方法**
   - 浏览器跨域请求会先发送预检请求(preflight)
   - 必须在allowedMethods中包含OPTIONS

## 验证步骤

1. **重启后端服务**
   ```bash
   # 重新编译和启动 like-front 服务
   ```

2. **清除浏览器缓存和Cookie**
   - 清除旧的token cookie
   - 清除localStorage

3. **测试登录流程**
   - 访问PC端登录页面
   - 输入账号密码登录
   - 检查Network面板：
     - 登录接口返回token
     - 后续请求携带 `open_party_java` 请求头
     - 响应头包含 `Access-Control-Allow-Credentials: true`

4. **测试已登录状态**
   - 刷新页面验证token持久化
   - 访问需要认证的页面
   - 确认不再出现TOKEN_EMPTY错误

## 注意事项

1. **不同终端使用不同的token名称**
   - PC端/移动端: `open_party_java`
   - 管理后台: `open_party_java_admin`

2. **生产环境建议**
   - 将 `allowedOriginPatterns("*")` 改为具体域名列表
   - 示例: `.allowedOriginPatterns("https://yourdomain.com", "https://www.yourdomain.com")`

3. **Cookie跨域注意**
   - Cookie的Domain和Path需要正确配置
   - 如果前后端不同域，建议使用请求头传递token而非Cookie

## 相关文件清单

### 后端
- `server/like-front/src/main/java/com/mdd/front/config/WebMvcConfig.java`
- `server/like-admin/src/main/java/com/mdd/admin/config/WebMvcConfig.java`
- `server/like-front/src/main/resources/application.yml` (查看token-name配置)
- `server/like-front/src/main/java/com/mdd/front/LikeFrontInterceptor.java` (token验证逻辑)

### 前端
- `pc/utils/http/index.ts` (HTTP请求封装和拦截器)
- `pc/stores/user.ts` (用户状态和token管理)
- `pc/enums/cacheEnums.ts` (缓存key定义)
- `pc/layouts/components/account/login.vue` (登录组件)
