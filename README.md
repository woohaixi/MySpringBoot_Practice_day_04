# 学习积分管理系统

基于Spring Boot + JPA的学习积分管理系统，支持积分规则管理、积分记录跟踪、特殊活动管理等功能。

## 功能特性

### 1. 积分规则管理
- **基础积分设定**：针对不同类型的学习活动设定积分获取标准
- **进阶积分规则**：根据学习成果质量或难度提升设置额外积分
- **积分规则CRUD**：支持积分规则的创建、读取、更新、删除操作

### 2. 积分记录管理
- **积分获得记录**：记录用户每次积分获得的详细信息
- **积分使用跟踪**：跟踪积分的使用情况
- **积分有效期管理**：自动处理积分过期

### 3. 特殊活动管理
- **特殊活动创建**：支持创建编程竞赛、特别培训等特殊活动
- **参与人数限制**：支持设置活动最大参与人数
- **时间范围控制**：支持活动开始和结束时间管理

### 4. 积分有效期设置
- **自动过期处理**：定时任务每日检查并标记过期积分
- **有效期规则**：默认积分有效期为1年，可自定义

## 技术栈

- **后端框架**：Spring Boot 3.5.3
- **数据访问**：Spring Data JPA + MyBatis（混合使用）
- **数据库**：MySQL
- **定时任务**：Spring Scheduling
- **前端展示**：原生HTML/CSS/JavaScript

## 数据库设计

### 积分规则表 (point_rules)
```sql
CREATE TABLE point_rules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rule_name VARCHAR(255) NOT NULL,
    activity_type VARCHAR(100) NOT NULL,
    base_points INT NOT NULL,
    bonus_points INT,
    difficulty_level VARCHAR(50),
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME,
    updated_at DATETIME
);
```

### 积分记录表 (point_records)
```sql
CREATE TABLE point_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    rule_id BIGINT NOT NULL,
    activity_name VARCHAR(255) NOT NULL,
    points_earned INT NOT NULL,
    points_type VARCHAR(50),
    earned_at DATETIME,
    expiry_date DATETIME,
    is_expired BOOLEAN DEFAULT FALSE,
    is_used BOOLEAN DEFAULT FALSE,
    used_at DATETIME,
    description TEXT,
    created_at DATETIME
);
```

### 特殊活动表 (special_events)
```sql
CREATE TABLE special_events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(255) NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    special_points INT NOT NULL,
    start_date DATETIME,
    end_date DATETIME,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    max_participants INT,
    current_participants INT DEFAULT 0,
    created_at DATETIME,
    updated_at DATETIME
);
```

## API 接口

### 积分规则管理 API
- `GET /api/point-rules` - 获取所有积分规则
- `GET /api/point-rules/active` - 获取活跃的积分规则
- `GET /api/point-rules/activity/{activityType}` - 根据活动类型获取积分规则
- `POST /api/point-rules` - 创建新的积分规则
- `PUT /api/point-rules/{id}` - 更新积分规则
- `DELETE /api/point-rules/{id}` - 删除积分规则

### 积分记录管理 API
- `GET /api/point-records/user/{userId}` - 获取用户积分记录
- `GET /api/point-records/user/{userId}/total` - 获取用户可用积分总数
- `POST /api/point-records/award` - 给用户颁发积分
- `POST /api/point-records/award-special` - 给用户颁发特殊积分
- `POST /api/point-records/use` - 使用积分

### 特殊活动管理 API
- `GET /api/special-events/active` - 获取活跃的特殊活动
- `GET /api/special-events/current` - 获取当前进行中的活动
- `POST /api/special-events` - 创建新的特殊活动
- `POST /api/special-events/{id}/participate` - 参与特殊活动

### 综合管理 API
- `GET /api/learning-points/dashboard/{userId}` - 获取用户积分仪表盘
- `POST /api/learning-points/initialize-demo-data` - 初始化演示数据

## 运行说明

### 环境要求
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### 配置数据库
1. 创建数据库：`CREATE DATABASE mydatabase2;`
2. 修改 `application.properties` 中的数据库连接信息
3. 系统启动时会自动创建数据表（JPA自动建表）

### 运行应用
```bash
# 编译项目
./mvnw clean compile

# 运行应用
./mvnw spring-boot:run
```

### 访问应用
- 前端页面：http://localhost:8080
- API文档：http://localhost:8080/api/

## 使用示例

### 1. 初始化演示数据
访问前端页面，点击"初始化演示数据"按钮，系统会自动创建一些示例积分规则和特殊活动。

### 2. 给用户颁发积分
```json
POST /api/point-records/award
{
    "userId": 1,
    "ruleId": 1,
    "activityName": "完成Java基础课程",
    "description": "学习Java基础知识并通过测试"
}
```

### 3. 查看用户积分总数
```
GET /api/point-records/user/1/total
```

### 4. 参与特殊活动
```
POST /api/special-events/1/participate
```

## 系统特点

1. **灵活的积分规则**：支持基础积分和奖励积分的组合
2. **自动过期处理**：定时任务自动处理过期积分
3. **特殊活动支持**：支持临时活动的积分规则
4. **完整的积分生命周期**：从获得到使用到过期的完整跟踪
5. **混合数据访问**：JPA与MyBatis共存，保持向后兼容性

## 扩展功能

系统设计支持以下扩展：
- 积分商城功能
- 积分等级系统
- 积分排行榜
- 积分统计分析
- 移动端支持
- 多租户支持