# 2017-11-6
+ 设置权限接口
- JWT 和 Session 同时开启..
+ 增加登陆(/admin/login.html) 和 退出(/admin/logout)

## 问题1:
- 如果单独使用 JWT 如何解决直接请求页面的问题。
    1. 使用携带参数的方式。
    2. 将 Token 放入 Session 中处理。
  > 因此, 我暂时取消了去除 Session 的设定, 但是这样没法防止 csrf(头疼, 求解)...
  > 考虑使用 Spring Security 自带的防止 csrf 但 APP 怎么获取CSRF的值呢？

--------------
# 2017-11-8
- 完善很多页面问题, 配置以下解决方案。
- 优化单位的一些校验 (数据校验未完全完成, 前端+后端) __设想: 前端+后端都需判断数据的完整性(正则/是否必填)__
- 删除单位会影响到
    1. // 删除此单位 及 子类单位
    2. // 删除用户 和 此单位及子单位之间的关联(这些单位的用户所属单位字段设置为null)
    3. // 删除角色 和 此单位及子单位之间的关联(直接删除掉这些角色)
- 配置了 rememberMe 功能。

## 解决方案[#问题1]()
- /api/** 使用 JWT **(未配置)**
- Web 站点, 使用 Spring Security 来防止 csrf。
因此, 具体有以下方案
1. 单纯的<a>标签使用
```html
<!--1. 使用表单方式进行跳转-->
<form th:action="@{/admin/logout}" method="POST">
    <input type="submit" class="btn btn-default btn-flat" value="退出"/>
</form>
<!--2. 或使用 AJAX 进行跳转-->

```
2. 全局的 AJAX Header 设置
```html
<meta name="_csrf" th:content="${_csrf.token}" />
    <!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" th:content="${_csrf.headerName}" />
<!--...-->
    <script>
        $(function () {
            // 添加 CSRF 头
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajaxSetup({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                }
            });
        });
    </script>
```

# 2017-11-9
- 更新`Department`/`Permission`/`Variable`
- 优化权限
    - 删除权限时, 会移除角色 与 此权限 及 子权限的关联
- 去除 修改单位/修改权限 的冗余HTML代码
- 更新所有的校验情况, 进行逻辑整理和代码优化

# 2017-11-12
- 添加 /api/** 使用 JWT

# 2017-11-14
- UI 更新
- 完善用户信息
- 内置初始化数据库(`BuiltInSeeder`)
- 完善权限系统
    - 默认: admin/admin **所有后台权限**
    - 默认: adm1n/adm1n **只有后台浏览权限**