package kmg.im.stock.tssts.data.dto.impl;

import java.util.ArrayList;
import java.util.List;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.data.dto.SpRawDataAcqDto;
import kmg.im.stock.tssts.data.dto.SpRawDataAcqMgtDto;

/**
 * 株価生データ取得管理ＤＴＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class SpRawDataAcqMgtDtoImpl implements SpRawDataAcqMgtDto {

    /** 株価生データ取得リスト */
    private final List<SpRawDataAcqDto> dataList;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public SpRawDataAcqMgtDtoImpl() {
        this.dataList = new ArrayList<>();
    }

    /**
     * 株価生データ取得リストをクリアする<br>
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
     * 株価生データ取得リストが空か<br>
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
     * 株価生データ取得リストが空ではないか<br>
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
     * 株価生データ取得を追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param data
     *             株価生データ取得
     */
    @Override
    public void addData(final SpRawDataAcqDto data) {
        this.dataList.add(data);
    }

    /**
     * 株価生データ取得リストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param addData
     *                追加株価生データ取得リスト
     */
    @Override
    public void addAllData(final List<SpRawDataAcqDto> addData) {
        if (ListUtils.isEmpty(addData)) {
            return;
        }

        this.dataList.addAll(addData);
    }

    /**
     * 株価生データ取得リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価生データ取得リスト
     */
    @Override
    public List<SpRawDataAcqDto> getDataList() {
        final List<SpRawDataAcqDto> result = this.dataList;
        return result;
    }
}