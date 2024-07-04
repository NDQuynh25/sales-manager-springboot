DROP DATABASE IF EXISTS shop;
CREATE DATABASE shop;
USE shop;

-- Create table roles--
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL COMMENT 'Tên quyền vd: admin, user',
    is_active TINYINT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

);

-- Create table users--
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role_id INT NOT NULL COMMENT 'ID quyền',
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    address VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    is_active TINYINT DEFAULT 1,
    facebook_account_id VARCHAR(255) DEFAULT '',
    google_account_id VARCHAR(255) DEFAULT '',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(id)

);

-- Create table delivery_address--
CREATE TABLE delivery_address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT 'ID người dùng',
    fullname VARCHAR(255) NOT NULL COMMENT 'Tên người nhận hàng',
    address VARCHAR(255) NOT NULL COMMENT 'Địa chỉ giao hàng',
    phone_number VARCHAR(15) NOT NULL COMMENT 'Số điện thoại giao hàng',
    is_active TINYINT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
); 

-- Create table vendors--
CREATE TABLE vendors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT 'ID người dùng',
    name VARCHAR(255) NOT NULL COMMENT 'Tên gian hàng',
    address VARCHAR(255) NOT NULL COMMENT 'Địa chỉ nhà cung cấp',
    phone_number VARCHAR(15) NOT NULL COMMENT 'Số điện thoại nhà cung cấp',
    email VARCHAR(255) NOT NULL COMMENT 'Email nhà cung cấp',
    is_active TINYINT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create table categories--
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL COMMENT 'Tên danh mục vd: Thời trang, điện thoại, máy tính,...',
    is_active TINYINT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create table products--
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vendor_id INT NOT NULL COMMENT 'ID nhà cung cấp',
    name VARCHAR(255) NOT NULL COMMENT 'Tên sản phẩm',
    price FLOAT NOT NULL CHECK (price >= 0) COMMENT 'Giá sản phẩm',
    discount FLOAT NOT NULL CHECK (discount >= 0) COMMENT 'Giảm giá sản phẩm',
    product_image TEXT COMMENT 'Ảnh sản phẩm',
    description TEXT COMMENT 'Mô tả sản phẩm',
    category_id INT NOT NULL COMMENT 'ID danh mục',
    is_active TINYINT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (vendor_id) REFERENCES vendors(id)
);

-- Create table carts--
CREATE TABLE carts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT 'ID người dùng',
    product_id INT NOT NULL COMMENT 'ID sản phẩm',
    quantity INT NOT NULL COMMENT 'Số lượng sản phẩm',
    is_active TINYINT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
-- Create table payments_methods--
CREATE TABLE payments_methods (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL COMMENT 'Tên phương thức thanh toán vd: Thanh toán khi nhận hàng, thanh toán qua thẻ ngân hàng, thanh toán qua ví điện tử,...',
    is_active TINYINT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create table orders --
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT 'ID người dùng',
    total_price FLOAT NOT NULL CHECK (total_price >= 0) COMMENT 'Tổng giá đơn hàng',
    status ENUM('Chờ xác nhận', 'Đã xác nhận', 'Đang giao hàng', 'Đã giao hàng', 'Đã hủy') NOT NULL COMMENT 'Trạng thái đơn hàng',
    payments_methods_id INT NOT NULL COMMENT 'ID phương thức thanh toán',
    order_date DATETIME NOT NULL COMMENT 'Ngày đặt hàng',
    is_active TINYINT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (payments_methods_id) REFERENCES payments_methods(id)
    
);


-- Create table order_details --
CREATE TABLE order_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL COMMENT 'ID đơn hàng',
    product_id INT NOT NULL COMMENT 'ID sản phẩm',
    quantity INT NOT NULL COMMENT 'Số lượng sản phẩm',
    price FLOAT NOT NULL CHECK (price >= 0) COMMENT 'Giá sản phẩm',
    discount FLOAT NOT NULL CHECK (discount >= 0) COMMENT 'Giảm giá sản phẩm',
    is_active TINYINT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Create table reviews--
CREATE TABLE reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL COMMENT 'ID người dùng',
    product_id INT NOT NULL COMMENT 'ID sản phẩm',
    content TEXT COMMENT 'Nội dung đánh giá',
    image TEXT COMMENT 'Ảnh sản phẩm',
    rating FLOAT NOT NULL CHECK (rating >= 0) COMMENT 'Đánh giá sản phẩm',
    is_active TINYINT DEFAULT 1,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);