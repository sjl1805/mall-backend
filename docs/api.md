# 用户和认证服务 API 文档

本文档列出了用户和认证服务的所有API路径。

## 认证相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/auth/login` | 用户登录 |
| POST | `/auth/register` | 用户注册 |
| POST | `/auth/logout` | 用户登出 |
| GET | `/auth/captcha` | 获取验证码 |

## 用户基本信息相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/user/info` | 获取当前登录用户信息 |
| PUT | `/user/info` | 更新用户个人信息 |
| PUT | `/user/password` | 修改密码 |
| POST | `/user/avatar` | 上传头像 |
| GET | `/user/check-username` | 检查用户名是否存在 |

## 地址管理相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/user/address/list` | 获取用户地址列表 |
| GET | `/user/address/default` | 获取默认地址 |
| POST | `/user/address` | 添加地址 |
| PUT | `/user/address` | 更新地址 |
| DELETE | `/user/address/{addressId}` | 删除地址 |
| PUT | `/user/address/default/{addressId}` | 设置默认地址 |
| GET | `/user/address/{addressId}` | 获取地址详情 |

## 购物车相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/user/cart` | 获取购物车信息 |
| GET | `/user/cart/count` | 获取购物车商品数量 |
| POST | `/user/cart/add` | 添加商品到购物车 |
| PUT | `/user/cart/update` | 更新购物车商品数量 |
| DELETE | `/user/cart/delete` | 删除购物车商品 |
| DELETE | `/user/cart/delete/batch` | 批量删除购物车商品 |
| DELETE | `/user/cart/clear` | 清空购物车 |
| PUT | `/user/cart/checked` | 选中/取消选中购物车商品 |
| PUT | `/user/cart/checked/batch` | 批量选中/取消选中购物车商品 |
| PUT | `/user/cart/checked/all` | 全选/取消全选购物车商品 |
| GET | `/user/cart/exists` | 检查商品是否在购物车中 |

## 收藏相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/user/favorite/add` | 添加收藏 |
| DELETE | `/user/favorite/remove` | 取消收藏 |
| GET | `/user/favorite/check` | 检查商品是否已收藏 |
| GET | `/user/favorite/list` | 获取用户收藏的商品列表 |
| GET | `/user/favorite/count` | 获取用户收藏数量 |
| GET | `/user/favorite/recent` | 获取用户最近收藏的商品 |

## 用户行为相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/user/behavior/history` | 获取用户行为历史 |
| GET | `/user/behavior/page` | 分页获取用户行为历史 |
| POST | `/user/behavior/record` | 记录用户行为 |
| GET | `/user/behavior/recent/view` | 获取用户最近浏览的商品 |
| GET | `/user/behavior/stats` | 获取用户行为统计数据 |
| POST | `/user/behavior/record/batch` | 批量记录用户行为 |
| DELETE | `/user/behavior/clear` | 清除用户行为记录 |
| DELETE | `/user/behavior/cancel` | 取消某个行为记录 |
| GET | `/user/behavior/type-desc` | 获取行为类型描述 |

## 评价相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/review/add` | 添加商品评价 |
| DELETE | `/review/{reviewId}` | 删除评价 |
| GET | `/review/product/{productId}` | 获取商品评价列表 |
| GET | `/review/user` | 获取用户评价列表 |
| GET | `/review/stats/{productId}` | 获取商品评价统计 |
| GET | `/review/check` | 检查用户是否已评价商品 |
| GET | `/review/latest` | 获取最新评价列表 |

## 推荐相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/recommend` | 获取推荐商品列表（混合推荐） |
| GET | `/recommend/all-types` | 获取所有推荐类型的商品 |
| GET | `/recommend/user-cf` | 获取猜你喜欢推荐（基于用户的协同过滤） |
| GET | `/recommend/item-cf` | 获取相似推荐（基于物品的协同过滤） |
| GET | `/recommend/popular` | 获取热门推荐 |
| GET | `/recommend/content-based` | 基于内容的推荐 |
| GET | `/recommend/similar/{productId}` | 获取与商品相似的商品 |

## 商品相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/product/page` | 分页查询商品列表 |
| GET | `/product/{id}` | 获取商品详情 |
| GET | `/product/hot` | 获取热门商品 |
| GET | `/product/new` | 获取最新商品 |
| GET | `/product/category/{categoryId}` | 获取分类商品 |
| GET | `/product/{productId}/similar` | 获取相似商品 |

## 分类相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/category/list` | 获取启用的分类列表（平铺） |
| GET | `/category/tree` | 获取启用的分类树结构 |
| GET | `/category/{categoryId}` | 根据ID获取分类详情 |
| GET | `/category/children/{parentId}` | 根据父ID获取子分类 |

## 订单相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/order/create` | 创建订单 |
| GET | `/order/detail` | 获取订单详情 |
| POST | `/order/cancel` | 取消订单 |
| POST | `/order/pay` | 支付订单 |
| POST | `/order/confirm` | 确认收货 |
| DELETE | `/order/delete` | 删除订单 |
| GET | `/order/list` | 获取用户订单列表 |
| POST | `/order/pay/callback` | 订单支付回调（模拟） |

## 文件相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/file/upload` | 通用文件上传接口 |
| POST | `/file/admin/upload` | 管理员文件上传接口 |
| DELETE | `/file` | 删除文件（管理员） |

## 管理员接口

### 用户管理接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/admin/users` | 获取用户列表（分页） |
| GET | `/admin/users/{userId}` | 获取用户详情 |
| POST | `/admin/users` | 添加用户 |
| PUT | `/admin/users` | 更新用户 |
| DELETE | `/admin/users/{userId}` | 删除用户 |
| PUT | `/admin/users/{userId}/status` | 启用/禁用用户 |
| PUT | `/admin/users/{userId}/password` | 重置用户密码 |
| PUT | `/admin/users/{userId}/role` | 修改用户角色 |
| GET | `/admin/users/statistics` | 获取用户统计信息 |

### 分类管理接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/admin/categories/tree` | 获取分类树形列表 |
| GET | `/admin/categories` | 获取所有分类的平铺列表 |
| GET | `/admin/categories/{categoryId}` | 获取分类详情 |
| POST | `/admin/categories` | 添加分类 |
| PUT | `/admin/categories` | 更新分类 |
| DELETE | `/admin/categories/{categoryId}` | 删除分类 |
| PUT | `/admin/categories/{categoryId}/status` | 启用/禁用分类 |
| POST | `/admin/categories/icon` | 上传分类图标 |
| PUT | `/admin/categories/{categoryId}/sort` | 修改分类排序 |
| PUT | `/admin/categories/{categoryId}/move` | 移动分类 |
| POST | `/admin/categories/batch` | 批量添加分类 |

### 订单管理接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/admin/orders` | 获取订单列表（分页） |
| GET | `/admin/orders/{orderNo}` | 获取订单详情 |
| POST | `/admin/orders/{orderNo}/ship` | 订单发货 |
| PUT | `/admin/orders/{orderNo}/cancel` | 取消订单 |
| GET | `/admin/orders/statistics` | 获取订单统计信息 |
| GET | `/admin/orders/status-distribution` | 获取订单状态分布 |
| PUT | `/admin/orders/{orderNo}/note` | 修改订单备注 |
| GET | `/admin/orders/export` | 导出订单数据 |

### 商品管理接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/admin/products` | 获取商品列表（分页） |
| GET | `/admin/products/{productId}` | 获取商品详情 |
| POST | `/admin/products` | 添加商品 |
| PUT | `/admin/products` | 更新商品 |
| DELETE | `/admin/products/{productId}` | 删除商品 |
| PUT | `/admin/products/{productId}/status` | 商品上下架 |
| PUT | `/admin/products/{productId}/stock` | 更新商品库存 |
| PUT | `/admin/products/{productId}/price` | 更新商品价格 |
| POST | `/admin/products/image` | 上传商品主图 |
| POST | `/admin/products/images` | 上传商品图片（添加到图片集） |
| DELETE | `/admin/products/{productId}/images` | 删除商品图片 |
| PUT | `/admin/products/batch/status` | 批量上下架商品 |

### 推荐系统管理接口

| 方法 | 路径 | 描述 |
|------|------|------|
| POST | `/admin/products/similarity/calculate/all` | 计算所有商品间的相似度 |
| POST | `/admin/products/similarity/calculate/{productId}` | 计算指定商品与其他所有商品的相似度 |
| POST | `/admin/recommendation/calculate/preference` | 计算用户偏好 |
| POST | `/admin/recommendation/calculate/recommendation` | 生成用户推荐结果 |
| GET | `/admin/recommendation/similar-users` | 获取用户与其他用户的相似度 | 