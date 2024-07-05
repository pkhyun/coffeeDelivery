-- Create users table
CREATE TABLE IF NOT EXISTS users (
                                     user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     user_name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nick_name VARCHAR(255) NOT NULL
    );

-- Create cafe table
CREATE TABLE IF NOT EXISTS cafe (
                                    cafe_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    cafe_name VARCHAR(255) NOT NULL,
    cafe_info VARCHAR(255) NOT NULL,
    cafe_address VARCHAR(255) NOT NULL
    );

-- Create menu table
CREATE TABLE IF NOT EXISTS menu (
                                    menu_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    menu_name VARCHAR(255) NOT NULL,
    menu_price INT NOT NULL,
    cafe_id BIGINT,
    menu_type VARCHAR(255) NOT NULL,
    FOREIGN KEY (cafe_id) REFERENCES cafe(cafe_id)
    );

-- Create cafe_like table
CREATE TABLE IF NOT EXISTS cafe_like (
                                         cafe_like_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         user_id BIGINT,
                                         cafe_id BIGINT,
                                         FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (cafe_id) REFERENCES cafe(cafe_id)
    );
