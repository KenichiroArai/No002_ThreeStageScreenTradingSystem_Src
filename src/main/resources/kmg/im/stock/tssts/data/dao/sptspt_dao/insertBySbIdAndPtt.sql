INSERT INTO sptspt (
    start_date
,   end_date
,   locale_id
,   creator
,   created_date
,   updater
,   update_date
,   note
,   name
,   stock_brand_id
,   period_type_id
)
SELECT
    /*:startDate*/'-infinity'
,   /*:endDate*/'infinity'
,   /*:localeId*/'ja'
,   /*:creator*/'user'
,   /*:createdDate*/'2021/06/05'
,   /*:updater*/'user'
,   /*:updateDate*/'2021/06/05'
,   /*:note*/''
,   (
	    SELECT
	        name
	    FROM
	        stock_brand
	    WHERE
	        stock_brand.id = /*:stockBrandId*/897
    )
    || (
        SELECT
            name
        FROM
            code_value
        WHERE
            id = /*:periodTypeId*/1
    )
,   /*:stockBrandId*/897
,   /*:periodTypeId*/1
