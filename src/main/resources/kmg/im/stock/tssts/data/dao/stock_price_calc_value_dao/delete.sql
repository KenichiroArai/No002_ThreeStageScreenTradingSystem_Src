DELETE FROM
    stock_price_calc_value
WHERE
    EXISTS (
        SELECT
            1
        FROM
            stock_price_time_series spts
        WHERE
            spts.id = stock_price_calc_value.id
            AND spts.sptspt_id = /*:sptsptId*/1
    )
