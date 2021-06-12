DELETE FROM
    stock_price_time_series     -- 株価時系列
WHERE
    EXISTS (
        SELECT
            1
        FROM
            sptspt              -- 株価時系列期間の種類
        WHERE
            sptspt.period_type_id = /*:periodTypeId*/1
    )
