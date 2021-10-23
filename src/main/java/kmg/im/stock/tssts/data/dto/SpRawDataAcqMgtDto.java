package kmg.im.stock.tssts.data.dto;

import java.util.List;

/**
 * 株価生データ取得管理ＤＴＯインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface SpRawDataAcqMgtDto {

    /**
     * 株価生データ取得リストをクリアする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    void clearDataList();

    /**
     * 株価生データ取得リストが空か<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空、false：空ではない
     */
    boolean isDataListEmpty();

    /**
     * 株価生データ取得リストが空ではないか<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空ではない、false：空
     */
    boolean isDataListNotEmpty();

    /**
     * 株価生データ取得を追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param data
     *             株価生データ取得
     */
    void addData(SpRawDataAcqDto data);

    /**
     * 株価生データ取得リストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param addData
     *                追加株価生データ取得リスト
     */
    void addAllData(List<SpRawDataAcqDto> addData);

    /**
     * 株価生データ取得リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価生データ取得リスト
     */
    List<SpRawDataAcqDto> getDataList();
}
