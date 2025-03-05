# order_management
This  application will simulate a basic order management system where clients can perform CRUD  operations on orders. 

## 📌 Project Overview
The **Order Management System** is a web-based application designed to handle order creation, tracking, and management. It follows a microservices architecture and provides a RESTful API for seamless integration.

## 🚀 Features
- Create, update, and delete orders.
- Track order status (`NEW`, `PROCESSING`, `COMPLETED`, `CANCELED`).
- Secure authentication and role-based access.
- API-based order management with PostgreSQL.
- JUnit test cases for API testing.
- Error handling and logging.

## 🛠️ Technologies Used
| Technology  | Version |
|-------------|---------|
| Spring Boot | 3.x     |
| Java        | 17      |
| PostgreSQL  | 14.x    |
| Postman     | Latest  |

## 📂 Project Structure
order-management/
│── src/
│   ├── main/java/com/krenai/order_management/
│   │   ├── controllers/ (API Controllers)
│   │   ├── services/ (Business Logic)
│   │   ├── repositories/ (Database Access)
│   │   ├── dto/ (Entity Models)
│   │   ├── security/ (Security Config)
│   │   ├── entity/ (Table data)
│── resources/
│   ├── application.yml (Spring Boot Config)
│   ├── application-dev.yml (Spring Boot Config)
│── test/
│   ├── main/test/com/krenai/order_management/
│   │   ├── controller/
│   │   ├── service/
│── pom.xml


## 🗄️ Database Schema
### **Orders Table**
```sql
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    order_date DATE NOT NULL DEFAULT CURRENT_DATE,
    total_amount DOUBLE PRECISION CHECK (total_amount >= 0),
    status VARCHAR(50) CHECK (status IN ('NEW', 'PROCESSING', 'COMPLETED', 'CANCELED'))
);

INSERT INTO orders (customer_name, total_amount, status) VALUES
('Ram', 100.50, 'NEW'),
('Sai', 350.65, 'COMPLETED'),
('Shiva', 200.75, 'PROCESSING');

CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    quantity INT CHECK (quantity >= 1),
    price DOUBLE PRECISION CHECK (price >= 0),
    order_id INT REFERENCES orders(id) ON DELETE CASCADE
);

INSERT INTO order_items (product_name, quantity, price, order_id) VALUES
('Laptop', 1, 100.50, 1),
('Mouse', 3, 150.40, 3),
('Keyboard', 2, 50.00, 2);

# Clone the repository
git clone https://github.com/godinikamakshi/order_management.git

# Navigate to the project folder
cd order-management

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run

# Access API at
http://localhost:8096/

