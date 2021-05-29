package kmg.im.stock.tssts.data.dto.impl;

import java.util.ArrayList;
import java.util.List;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.data.dto.StockPriceDataDto;
import kmg.im.stock.tssts.data.dto.StockPriceDataMgtDto;

/**
 * 株価データ管理ＤＴＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class StockPriceDataMgtDtoImpl implements StockPriceDataMgtDto {

    /** 株価データリスト */
    private final List<StockPriceDataDto> dataList;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public StockPriceDataMgtDtoImpl() {
        this.dataList = new ArrayList<>();
    }

    /**
     * 株価データリストをクリアする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void clearDataList() {
        this.dataList.clear();
    }

    /**
     * 株価データリストが空か<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空、false：空ではない
     */
    @Override
    public boolean isDataListEmpty() {
        boolean result = true;

        if (ListUtils.isEmpty(this.dataList)) {
            return result;
        }

        result = false;
        return result;
    }

    /**
     * 株価データリストが空ではないか<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空ではない、false：空
     */
    @Override
    public boolean isDataListNotEmpty() {
        final boolean result = !this.isDataListEmpty();
        return result;
    }

    /**
     * 株価データを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param data
     *             株価データ
     */
    @Override
    public void addData(final StockPriceDataDto data) {
        this.dataList.add(data);
    }

    /**
     * 株価データリストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param addData
     *                    追加株価データリスト
     */
    @Override
    public void addAllData(final List<StockPriceDataDto> addData) {
        if (ListUtils.isEmpty(addData)) {
            return;
        }

        this.dataList.addAll(addData);
    }

    /**
     * 株価データリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価データリスト
     */
    @Override
    public List<StockPriceDataDto> getDataList() {
        final List<StockPriceDataDto> result = this.dataList;
        return result;
    }
}
