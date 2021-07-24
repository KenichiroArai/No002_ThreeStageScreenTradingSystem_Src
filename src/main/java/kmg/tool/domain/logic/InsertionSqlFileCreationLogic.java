package kmg.tool.domain.logic;

import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * 挿入ＳＱＬファイル作成ロジックインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface InsertionSqlFileCreationLogic {

    /** 設定シート名 */
    String SETTING_SHEET_NAME = "設定情報";

    /** 一覧シート名 */
    String LIST_NAME = "一覧";

    /**
     * ＤＢ設定を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param wk
     *           ワークブック
     * @return ＤＢ設定
     */
    String getDbSetting(final Workbook wk);

    /**
     * SQLIDマップ返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param wk
     *           ワークブック
     * @return SQLIdマップ
     */
    Map<String, String> getSqlIdMap(final Workbook wk);
}
