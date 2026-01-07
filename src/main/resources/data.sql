
INSERT INTO member
(user_name, email, phone_number, password, role, created_at, updated_at,address,address_detail,postcode)
VALUES
    ('seller', 's1@naver.com', '01012345678', '$2a$10$5Pbwz0HW72R.xmgBrg5MLOSIm5qMwbFKGo.2J0DdiZWRjfKU/wvzW
', 'ROLE_SELLER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,null,null,null),
    ('buyer', 'b1@naver.com', '01022222222','$2a$10$f2CVGEbuqRo4s3yV.tEuxuYKwL9moW6QRrIrtaSZ94QlvObPhg.3.',
     'ROLE_BUYER',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,null, null, null);


-- 1) product 10,000건
INSERT INTO product (name, price, cover_image_url, category, is_saled, created_at)
SELECT
    '상품' || x,
    5000 + (MOD(x, 200) * 100),
    'img/product_' || x || '.jpg',
    CASE MOD(x, 4) WHEN 0 THEN '상의' WHEN 1 THEN '하의' WHEN 2 THEN '신발' ELSE '잡화' END,
    false,
    CURRENT_TIMESTAMP
FROM SYSTEM_RANGE(1, 10000);

-- 2) 최근 1만 개 product에 대해 SKU 10개씩
INSERT INTO product_sku (color, size, quantity, state, created_at, updated_at, product_id)
SELECT
    CASE WHEN r.x = 1 THEN 'black' ELSE 'blue' END AS color,
    CASE WHEN r.x = 1 THEN 'M' ELSE 'L' END        AS size,
    100                                            AS quantity,
    '재고있음'                                      AS state,
    CURRENT_TIMESTAMP                               AS created_at,
    CURRENT_TIMESTAMP                               AS updated_at,
    p.product_id                                    AS product_id         -- ★ 여기!
FROM (
    SELECT product_id
    FROM product
    ORDER BY product_id DESC
    LIMIT 10000
    ) p
    JOIN SYSTEM_RANGE(1, 5) r ON TRUE;


-- insert into product
-- (name, price, COVER_IMAGE_URL, CATEGORY, IS_SALED, CREATED_AT)
-- values
--     ('후드티', 10000, '후드티_image_url', '상의', true, CURRENT_TIMESTAMP),
--     ('청바지',10000, '청바지_image_url','하의', false, current_timestamp);
--
--
-- insert into PRODUCT_SKU
-- (color, size, QUANTITY, STATE, CREATED_AT, UPDATED_AT, PRODUCT_ID)
-- values
--     ('black', 'XL', 1000, '재고있음', current_timestamp, current_timestamp, 1),
--     ('red', 'XL', 100, '재고있음', current_timestamp, current_timestamp, 1),
--     ('blue', 'XL', 100, '재고있음', current_timestamp, current_timestamp, 1);
-- insert into PRODUCT_SKU
--     (color, size, QUANTITY, STATE, CREATED_AT, UPDATED_AT, PRODUCT_ID)
-- values
--     ('black', '24', 100, '재고있음', current_timestamp, current_timestamp, 2),
--     ('red', '28', 100, '재고있음', current_timestamp, current_timestamp, 2),
--     ('blue', '30', 100, '재고있음', current_timestamp, current_timestamp, 2);

INSERT INTO "EVENT" (name, START_AT, END_AT, STATE, COVER_IMAGE_URL)
VALUES
    ('가을 특가 이벤트',
     TIMESTAMP WITH TIME ZONE '2025-09-01 09:00:00+09:00',
     TIMESTAMP WITH TIME ZONE '2025-10-01 18:00:00+09:00',
     '진행중',
     '이벤트_cover_image_url');

-- insert into  EVENT_ITEM
-- (SALE_PRICE, QUOTA_PER_USER, CREATED_AT, EVENT_ID, PRODUCT_ID)
-- values
--     (5000,3,current_timestamp, 1, 1);

-- 3) '가을 특가 이벤트' (EVENT_ID = 1)에 대해 100개의 EVENT_ITEM 삽입
-- 상품 ID (PRODUCT_ID) 1번부터 100번까지의 상품을 이벤트에 등록합니다.
INSERT INTO EVENT_ITEM
(SALE_PRICE, QUOTA_PER_USER, CREATED_AT, EVENT_ID, PRODUCT_ID)
SELECT
    -- SALE_PRICE: 원래 가격(product.price)의 80%로 설정 (예시)
    ROUND(p.price * 0.8) AS SALE_PRICE,
    -- QUOTA_PER_USER: 사용자당 구매 한도를 5개로 설정
    5 AS QUOTA_PER_USER,
    CURRENT_TIMESTAMP AS CREATED_AT,
    -- EVENT_ID: 위에서 삽입된 '가을 특가 이벤트' (ID=1)
    1 AS EVENT_ID,
    -- PRODUCT_ID: 상품 ID 1번부터 100번까지 선택
    p.product_id AS PRODUCT_ID
FROM
    product p
WHERE
    p.product_id BETWEEN 1 AND 10000;


insert into CART
(QUANTITY, MEMBER_ID, PRODUCT_SKU_ID)
values
    (2,2,1),

    ( 2,2,2);




INSERT INTO order_entity
(TOTAL_PRICE, DEAR_NAME, PHONE_NUMBER, ADDRESS, ADDRESS_DETAIL, POST_CODE,
 CREATED_AT, STATE, MEMBER_ID)
SELECT
    20000,
    'buyer',
    '01022222222',
    '대전',
    '유성구',
    '34112',
    DATEADD(
        DAY,
            CAST(RAND() * 152 AS INT),
            TIMESTAMP WITH TIME ZONE '2025-01-01 09:00:00+09:00'
    ) AS CREATED_AT,
    '결제완료',
    2
FROM SYSTEM_RANGE(1, 500);





INSERT INTO ORDER_DETAIL
(price, name, PRODUCT_COVER_IMAGE_URL, QUANTITY, SIZE, COLOR, state,MEMBER_ID, PRODUCT_SKU_ID, ORDER_ID)
SELECT
    5000,
    '후드티',
    'hoodie_image_url',
    2,
    CASE MOD(d.detail_no, 3)
        WHEN 0 THEN 'M'
        WHEN 1 THEN 'L'
        ELSE 'XL'
        END AS SIZE,
    CASE MOD(d.detail_no, 2)
        WHEN 0 THEN 'black'
        ELSE 'red'
END AS COLOR,
    '결제완료',
    2 AS MEMBER_ID,
    MOD(d.detail_no, 10) + 1 AS PRODUCT_SKU_ID,
    o.order_id AS ORDER_ID
FROM
    order_entity o
CROSS JOIN SYSTEM_RANGE(1, 1000) d(detail_no);



-- insert into order_entity
-- (TOTAL_PRICE, DEAR_NAME, PHONE_NUMBER, ADDRESS, ADDRESS_DETAIL, POST_CODE, CREATED_AT, STATE, MEMBER_ID)
-- values
--     (20000, 'buyer', '01022222222', '대전', '유성구', '34112',
--      current_timestamp, '결제완료', 2);
-- from SYSTEM_RANGE(1,1000)
--
-- insert into ORDER_DETAIL
-- (price, name, PRODUCT_COVER_IMAGE_URL, QUANTITY, SIZE, COLOR, MEMBER_ID, PRODUCT_SKU_ID, ORDER_ID)
-- values
--     (5000, '후드티', '후드티_image_url', 2, 'XL','black',2,1, 1),
--     (5000, '후드티', '후드티_image_url', 2, 'XL','red',2,2, 1);




-- 캐시 테이블에 데이터 넣기
INSERT INTO event_item_cache(event_item_id, event_id, product_id, product_name, product_price, product_cover_image_url, sale_price, product_sku_id, size, color, quantity)
SELECT
    ei.event_item_id,
    ei.event_id,
    ei.product_id,
    p.name,
    p.price,
    p.cover_image_url,
    ei.sale_price,
    ps.product_sku_id,
    ps.size,
    ps.color,
    ps.quantity
FROM event_item ei
         JOIN product p ON p.product_id = ei.product_id
         JOIN product_sku ps ON ps.product_id = p.product_id
WHERE ei.event_id = 1;