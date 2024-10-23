
# 如何使用公共IP通过MySQL Workbench或MySQL命令行连接Google Cloud SQL实例

## 目录：
1. 前提条件
2. 获取本机的公共IP地址
3. 使用MySQL Workbench连接到Google Cloud SQL
4. 使用MySQL命令行连接到Google Cloud SQL
5. 故障排查

## 1. 前提条件

在连接到Google Cloud SQL实例之前，需要满足以下条件：

- 已经在Google Cloud Platform (GCP) 上创建了一个Cloud SQL实例，并且该实例运行的是MySQL数据库。
- 你已经向管理员提供了你的公共IP地址，管理员已配置好允许该IP地址访问Cloud SQL实例。
- 在本地计算机上安装了MySQL Workbench或MySQL命令行工具。如果没有安装MySQL命令行，可以使用操作系统自带的终端或命令提示符来执行。

## 2. 获取本机的公共IP地址

要连接到Google Cloud SQL实例，你需要找到自己的公共IP地址，并将其提供给管理员以配置访问权限。找到公共IP的方法如下：

1. 打开你的浏览器，访问[WhatIsMyIP](https://www.whatismyip.com)或[IPinfo](https://ipinfo.io)等网站。
2. 网站会显示你的公共IP地址，复制My Public IPv4，将这个地址发送给管理员进行配置。

## 3. 使用MySQL Workbench连接到Google Cloud SQL

1. **打开MySQL Workbench**。
2. 点击左上角的“+”按钮，创建一个新的连接。
3. **配置连接设置**：
   - **Connection Name**：project-439505:australia-southeast1:aifinance
   - **Connection Method**：选择“Standard (TCP/IP)”。
   - **Hostname**：输入Cloud SQL实例的公共IP地址: 35.189.27.126。
   - **Port**：默认值是3306（MySQL的端口）。
   - **Username**：输入Cloud SQL实例中配置的用户名: admin。
   - **Password**：点击“Store in Vault...”并输入Cloud SQL实例中配置的密码: 123。

4. 点击“Test Connection”进行连接测试。如果测试成功，表示你已经正确配置了连接。
5. **保存并连接**：点击“OK”保存连接设置，然后点击“Connect”连接到你的Cloud SQL实例。

## 4. 使用MySQL命令行连接到Google Cloud SQL

如果没有安装MySQL Workbench，你可以直接使用命令行来连接Google Cloud SQL实例。

### 连接步骤：

1. 打开终端（Linux或Mac）或命令提示符（Windows）。
2. 使用以下命令连接到Cloud SQL实例：

   ```bash
   mysql -u root -p -h YOUR_INSTANCE_PUBLIC_IP
   ```

   - `-u root`：指定要使用的用户名，这里是`admin`。
   - `-p`：提示输入密码，密码是123。
   - `-h YOUR_INSTANCE_PUBLIC_IP`：输入Cloud SQL实例的公共IP地址（公共 IP 地址:35.189.27.126)。

3. 终端会提示输入密码，输入Cloud SQL实例用户的密码后按回车。
4. 如果连接成功，你会进入MySQL命令行模式，在那里你可以运行SQL查询。

## 5. 故障排查

- **连接超时或拒绝连接**：确保管理员已将你的IP地址配置为允许访问Cloud SQL实例。如果仍然出现连接问题，检查你的网络配置，确保没有防火墙阻止外部连接。
  
- **身份验证失败**：确认你使用的用户名和密码正确，并且该用户具有访问权限。

- **权限错误**：如果遇到权限问题，确保Cloud SQL用户具有正确的权限。你可以使用以下命令查看用户权限：
   ```sql
   SHOW GRANTS FOR 'username'666'%';
   ```

