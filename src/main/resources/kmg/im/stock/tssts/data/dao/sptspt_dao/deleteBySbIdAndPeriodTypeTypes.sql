DELETE FROM
    sptspt                              -- 株価時系列期間の種類
WHERE
    sptspt.stock_brand_id = /*:stockBrandId*/1
    AND period_type_id = /*:periodTypeId*/1
