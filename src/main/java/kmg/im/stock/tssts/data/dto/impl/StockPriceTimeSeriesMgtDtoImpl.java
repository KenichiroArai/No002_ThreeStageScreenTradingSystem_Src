package kmg.im.stock.tssts.data.dto.impl;

import java.util.ArrayList;
import java.util.List;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesMgtDto;

/**
 * 株価時系列管理ＤＴＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class StockPriceTimeSeriesMgtDtoImpl implements StockPriceTimeSeriesMgtDto {

    /** 株価時系列リスト */
    private final List<StockPriceTimeSeriesDto> dataList;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public StockPriceTimeSeriesMgtDtoImpl() {
        this.dataList = new ArrayList<>();
    }

    /**
     * 株価時系列リストをクリアする<br>
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
     * 株価時系列リストが空か<br>
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
     * 株価時系列リストが空ではないか<br>
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
     * 株価時系列を追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param data
     *             株価時系列
     */
    @Override
    public void addData(final StockPriceTimeSeriesDto data) {
        this.dataList.add(data);
    }

    /**
     * 株価時系列リストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param addData
     *                    追加株価時系列リスト
     */
    @Override
    public void addAllData(final List<StockPriceTimeSeriesDto> addData) {
        if (ListUtils.isEmpty(addData)) {
            return;
        }

        this.dataList.addAll(addData);
    }

    /**
     * 株価時系列リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列リスト
     */
    @Override
    public List<StockPriceTimeSeriesDto> getDataList() {
        final List<StockPriceTimeSeriesDto> result = this.dataList;
        return result;
    }
}
