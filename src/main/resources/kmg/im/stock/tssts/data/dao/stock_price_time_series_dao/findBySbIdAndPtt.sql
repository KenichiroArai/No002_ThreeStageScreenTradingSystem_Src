SELECT
    *
FROM
    stock_price_time_series main
WHERE
    EXISTS (
        SELECT
            1
        FROM
            sptspt
        WHERE
           sptspt.id =  main.sptspt_id
	       AND sptspt.stock_brand_id = /*:stockBrandId*/1
	       AND sptspt.period_type_id = /*:periodTypeId*/1
    )
;
