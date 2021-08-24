SELECT
    sptspt.stock_brand_id   AS stock_brand_id   -- 株銘柄ID
,   stock_brand.name        AS stock_brand_name -- 株銘柄名称
,   stock_brand.code        AS stock_brand_code -- 株価銘柄コード
,   sptspt.id               AS sptspt_id        -- 株価時系列期間の種類ID
,   sptspt.name             AS sptspt_name      -- 株価時系列期間の種類名称
,   sptspt.period_type_id   AS period_type_id   -- 期間の種類ID
,   cd_period_type.name     AS period_type_name -- 期間の種類名称
,   spts.id                 AS spts_id          -- 株価時系列ID
,   spts.name               AS spts_name        -- 株価時系列名称
,   spts.no                                     -- 番号
,   spts.period_start_date                      -- 期間開始日
,   spts.period_end_date                        -- 期間終了日
,   spts.op                                     -- 始値
,   spts.hp                                     -- 高値
,   spts.lp                                     -- 安値
,   spts.cp                                     -- 終値
,   spts.volume                                 -- 出来高
,   spcv.spcvt_id                               -- 株価計算値の種類ID
,   spcvt.name              AS spcvt_name       -- 株価計算値の種類名称
,   spcv.calc_value                             -- 計算値
FROM
    sptspt                                                  -- 株価時系列期間の種類
  INNER JOIN stock_brand                                    -- 株銘柄
    ON  stock_brand.id = sptspt.stock_brand_id
  INNER JOIN code_value                 AS cd_period_type   -- コードマスタ期間の種類
    ON  cd_period_type.id = sptspt.period_type_id
  INNER JOIN stock_price_time_series    AS spts             -- 株価時系列
    ON  spts.sptspt_id = sptspt.id
  INNER JOIN stock_price_calc_value     AS spcv             -- 株価計算値
    ON  spcv.spts_id = spts.id
  INNER JOIN stock_price_calc_value_type    AS spcvt        -- 株価計算値の種類
    ON  spcvt.id = spcv.spcvt_id
WHERE
    stock_brand.code = /*code*/3053
ORDER BY
    sptspt.stock_brand_id                       -- 株銘柄ID
,   sptspt.id                                   -- 株価時系列期間の種類ID
,   sptspt.period_type_id                       -- 期間の種類ID
,   spts.id                                     -- 株価時系列ID
,   no                                          -- 番号
,   spcv.spcvt_id                               -- 株価計算値の種類ID
;
