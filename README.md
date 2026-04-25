# 🧾 Lost & Found Item Management System

## 📌 Project Description

The **Lost & Found Item Management System** is a desktop-based application developed using **Java Swing, JDBC, and MySQL**.
It helps users manage lost and found items efficiently by allowing them to add, update, view, and delete records.

---

## 🎯 Features

* ➕ Add new lost/found items
* 📋 View all items in a table
* ✏️ Update item details
* ❌ Delete items
* 🔄 Refresh data from database
* 🖱️ Click row to auto-fill form

---

## 🛠️ Technologies Used

* **Java (Swing)** – GUI development
* **JDBC** – Database connectivity
* **MySQL** – Database management
* **IntelliJ IDEA** – Development environment

---

## 🗂️ Project Structure

```
LostFoundSystem/
│── src/
│   └── LostFoundSystem.java
│── lib/
│   └── mysql-connector-j.jar
│── README.md
```

---

## ⚙️ Setup Instructions

### 1️⃣ Install Requirements

* Install Java (JDK 8 or above)
* Install MySQL Server
* Download MySQL Connector JAR

---

### 2️⃣ Create Database

```sql
CREATE DATABASE lost_found_system;
USE lost_found_system;

CREATE TABLE items (
    item_id INT PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(50),
    description VARCHAR(100),
    location VARCHAR(50),
    reporter_name VARCHAR(50),
    status VARCHAR(20)
);
```

---

### 3️⃣ Configure Database in Code

Update these values in your Java file:

```java
static final String URL = "jdbc:mysql://localhost:3306/lost_found_system";
static final String USER = "root";
static final String PASSWORD = "your_password";
```

---

### 4️⃣ Add MySQL Connector

* Download `mysql-connector-j`
* Add it to project libraries in IntelliJ

---

### 5️⃣ Run the Project

* Open project in IntelliJ IDEA
* Run `LostFoundSystem.java`
* GUI will launch

---

## 🧪 Sample Data

```sql
INSERT INTO items (item_name, description, location, reporter_name, status) VALUES
('Laptop', 'Dell Inspiron', 'Lab', 'Nisha', 'LOST'),
('Wallet', 'Brown leather', 'Cafeteria', 'Rahul', 'FOUND');
```

---

## 📸 Output

* User-friendly GUI with form inputs
* Table displaying all items
* Buttons for Add, Update, Delete, Refresh

---

## 🚀 Future Enhancements

* 🔍 Search functionality
* 🔐 User login system
* 🌐 Web-based version
* 📊 Reports & analytics

---

## 👩‍💻 Author

**Nisha**

---

## 📎 GitHub Repository

Add your repository link here:

```
https://github.com/nisha-07-coder/LostFoundSystem
```

---

## 📢 Conclusion

This project demonstrates how **Java Swing and JDBC** can be used to build a complete CRUD-based desktop application integrated with a database.

---
