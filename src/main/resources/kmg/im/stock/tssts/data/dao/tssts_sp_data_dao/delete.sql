DELETE FROM
    stock_price_time_series     -- 株価時系列
WHERE
    EXISTS (
        SELECT
            1
        FROM
            sptspt              -- 株価時系列期間の種類
        WHERE
            sptspt.stock_brand_id = stock_price_time_series.id
            AND EXISTS (
                SELECT
                    1
                FROM
                    stock_brand     sb  -- 株銘柄
                WHERE
                    sb.id = sptspt.stock_brand_id
                    AND sb.code = /*:stockBrandCode*/0          -- 株価銘柄コード
            )
    )
