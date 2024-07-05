-- Insert user
INSERT INTO users (user_name, password, nick_name)
VALUES ('John', 'password123', 'JohnDoe');

-- Insert cafes
INSERT INTO cafe (cafe_name, cafe_info, cafe_address)
VALUES ('카페1', '카페1입니다.', '서울 강남구'),
       ('카페2', '카페2입니다.', '서울 강서구'),
       ('카페3', '카페3입니다.', '서울 송파구');

-- Insert menus for cafe 1
INSERT INTO menu (menu_name, menu_price, cafe_id, menu_type)
VALUES ('아메리카노', 2500, 1, 'COFFEE'),
       ('카페라떼', 3000, 1, 'COFFEE'),
       ('카푸치노', 3200, 1, 'COFFEE'),
       ('치즈케이크', 4000, 1, 'DESSERT');

-- Insert menus for cafe 2
INSERT INTO menu (menu_name, menu_price, cafe_id, menu_type)
VALUES ('녹차라떼', 3500, 2, 'COFFEE'),
       ('아이스티', 2800, 2, 'COFFEE'),
       ('자몽에이드', 3800, 2, 'ADE'),
       ('파인애플주스', 3500, 2, 'JUICE');

-- Insert menus for cafe 3
INSERT INTO menu (menu_name, menu_price, cafe_id, menu_type)
VALUES ('초코라떼', 3800, 3, 'COFFEE'),
       ('레몬에이드', 4200, 3, 'ADE'),
       ('망고스무디', 4500, 3, 'JUICE'),
       ('블루베리치즈케이크', 4200, 3, 'DESSERT');

-- Insert cafe likes
INSERT INTO cafe_like (user_id, cafe_id)
VALUES (1, 1), -- John이 카페1을 좋아요
       (1, 2), -- John이 카페2을 좋아요
       (1, 3); -- John이 카페3을 좋아요
