# 简易产品与订单管理系统

这是一个基于 Java 和 MySQL 的简易系统，用于管理产品和订单。该项目展示了如何使用 Java 进行数据库操作（包括增、删、查、改等功能），并实现了事务管理、数据一致性控制等技术。

## 项目功能

本系统支持以下主要功能：

- **产品管理**：包括添加、查看、更新和删除产品。
- **订单管理**：包括创建订单、查看订单、删除订单等操作。
- **事务管理**：在插入和更新数据时使用事务控制，确保数据的一致性。
- **产品与订单关系**：支持一个订单包含多个产品（多对多关系）。

## 项目结构

Shop/
├── com/
│   ├── Interface/           # 接口定义文件
│   │   ├── productDao.java  # 商品相关的数据库操作接口
│   │   ├── productService.java # 商品相关服务接口
│   │   ├── orderDao.java    # 订单相关的数据库操作接口
│   │   └── orderService.java # 订单相关服务接口
│   ├── JdbcUtils.java   # JDBC 工具类，封装数据库连接和事务管理
│   ├── Order.java       # 订单实体类，包含订单信息和订单中的产品
│   ├── Order prodct #订单与产品关系类
│   ├── Product.java     # 产品实体类，包含产品信息
│   ├── tool/                # 具体的服务和 DAO 实现类
│   │   ├── ProductDaoImpl.java # 实现 productDao 接口的类，操作产品数据
│   │   ├── ProductServiceImpl.java # 实现 productService 接口的类，处理商品业务逻辑
│   │   ├── OrderDaoImpl.java  # 实现 orderDao 接口的类，操作订单数据
│   │   └── OrderServiceImpl.java # 实现 orderService 接口的类，处理订单业务逻辑
│   ├── SimpleSystemTest.java # JUnit 测试类，测试功能是否正常
│   └── OrderProduct.java    # 订单与产品关系的实体类，用于多对多关联
└── resources/
    └── database.properties  # 数据库连接配置信息



## 技术栈

- **JDBC**：用于连接 MySQL 数据库。
- **MySQL**：数据库管理系统，用于存储产品和订单信息。
- **JUnit 5**：用于编写和执行单元测试。
- **Maven**：用于构建和管理项目依赖。

## 主要功能

### 1. 产品管理

- **添加产品**：可以向数据库中插入新的产品记录。
- **查看产品**：通过产品 ID 查找产品，并返回产品的详细信息。
- **更新产品**：根据产品 ID 更新已有的产品信息（如价格、名称等）。
- **删除产品**：根据产品 ID 删除指定产品。

### 2. 订单管理

- **创建订单**：创建新的订单，订单中可以包含多个产品。每个订单有一个时间戳和相关的产品信息。
- **查看订单**：通过订单 ID 查找并显示订单的详细信息，包括订单日期和所包含的产品。
- **删除订单**：根据订单 ID 删除指定订单及其关联的订单产品。

### 3. 事务管理

- **事务控制**：在进行产品插入、更新或删除时，使用数据库事务进行控制，保证数据的一致性。如果发生错误，事务将回滚，确保数据不被不完整地提交。

## 实现过程

1. **数据库设计**：
   - 设计了三个主要的表：
     - `products`：用于存储产品信息（包括 ID、名称、价格和创建时间）。
     - `orders`：用于存储订单信息（包括订单 ID 和订单时间）。
     - `order_products`：用于关联订单和产品的多对多关系（每条记录表示某个订单中的某个产品及其数量）。

2. **JDBC 工具类（JdbcUtils）**：
   - 通过 `JdbcUtils` 类提供数据库连接和事务管理。该类封装了数据库连接、事务开始、提交和回滚等操作，确保了代码的简洁性和事务的一致性。

3. **DAO 层**：
   - 为产品和订单分别设计了 `productDao` 和 `orderDao` 接口，定义了对数据库的基本操作（如增、查、改、删）。
   - 实现了这两个接口的具体类（`ProductDaoImpl` 和 `OrderDaoImpl`），通过 JDBC 执行 SQL 语句，与数据库交互。

4. **服务层**：
   - `ProductServiceImpl` 和 `OrderServiceImpl` 类实现了相应的业务逻辑。这些服务类负责处理具体的业务操作，比如事务控制、数据验证等。

5. **订单与产品关系**：
   - 订单与产品之间的关系通过 `order_products` 表来实现多对多关联。每个订单可以包含多个产品，每个产品也可以出现在多个订单中。

6. **单元测试**：
   - 使用 JUnit 编写了简单的单元测试来验证系统的各项功能是否正确。例如，测试添加、删除、查询产品和订单的功能，确保所有操作能够顺利完成。

