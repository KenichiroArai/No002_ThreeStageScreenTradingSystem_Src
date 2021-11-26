DELETE FROM
    stock_price_calc_value     -- 株価計算値
WHERE
    EXISTS (
        SELECT
            1
        FROM
            stock_price_time_series     AS spts         -- 株価時系列
        WHERE
            spts.id = stock_price_calc_value.spts_id
            AND EXISTS (
                SELECT
                    1
                FROM
                    sptspt                              -- 株価時系列期間の種類
                WHERE
                    sptspt.id = spts.sptspt_id
                    AND sptspt.period_type_id = /*:periodTypeId*/1
	                AND sptspt.stock_brand_id = /*:stockBrandId*/1
            )
    )
