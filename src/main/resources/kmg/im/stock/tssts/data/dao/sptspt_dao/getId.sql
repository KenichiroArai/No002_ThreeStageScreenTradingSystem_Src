SELECT
    id
FROM
    sptspt
WHERE
    start_date <= /*:startDate*/'2021/06/11'
    AND end_date > /*:startDate*/'2021/06/11'
    AND stock_brand_id = /*:stockBrandId*/1
    AND period_type_id = /*:periodTypeId*/1
