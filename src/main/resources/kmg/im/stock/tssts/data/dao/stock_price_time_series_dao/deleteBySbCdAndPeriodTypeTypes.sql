DELETE FROM
    stock_price_time_series     AS spts         -- 株価時系列
WHERE
    EXISTS (
        SELECT
            1
        FROM
            sptspt                              -- 株価時系列期間の種類
        WHERE
            sptspt.id = spts.sptspt_id
            AND EXISTS (
                SELECT
                    1
                FROM
                    code_value                  -- コード値
                WHERE
                    code_value.code_kind_id = /*periodTypeId*/1
            )
            AND EXISTS (
                SELECT
                    1
                FROM
                    stock_brand         AS sb           -- 株銘柄
                WHERE
                    sb.code = /*stockBrandCode*/1
                    AND sb.id = sptspt.stock_brand_id
            )
    )
