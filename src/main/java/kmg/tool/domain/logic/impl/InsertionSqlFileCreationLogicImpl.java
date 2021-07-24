package kmg.tool.domain.logic.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import kmg.tool.domain.logic.InsertionSqlFileCreationLogic;
import kmg.tool.infrastructure.utils.PoiUtils;

/**
 * 挿入ＳＱＬファイル作成ロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class InsertionSqlFileCreationLogicImpl implements InsertionSqlFileCreationLogic {

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
    @Override
    public String getDbSetting(final Workbook wk) {
        String result = null;

        final Sheet wkSheet = wk.getSheet(InsertionSqlFileCreationLogic.SETTING_SHEET_NAME);
        final Cell wkCell = PoiUtils.getCell(wkSheet, 0, 1);

        result = PoiUtils.getStringValue(wkCell);

        return result;
    }

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
    @Override
    public Map<String, String> getSqlIdMap(final Workbook wk) {
        final Map<String, String> result = new HashMap<>();

        final Sheet wkSheet = wk.getSheet(InsertionSqlFileCreationLogic.LIST_NAME);
        for (int rowIdx = 1; rowIdx <= wkSheet.getLastRowNum(); rowIdx++) {

            // テーブル物理名を取得
            final Cell tablePhysicsCell = PoiUtils.getCell(wkSheet, rowIdx, 2);
            final String tablePhysicsStr = PoiUtils.getStringValue(tablePhysicsCell);

            // SQLIDを取得
            final Cell sqlIdCell = PoiUtils.getCell(wkSheet, rowIdx, 3);
            final String sqlIdStr = PoiUtils.getStringValue(sqlIdCell);

            // マップに追加
            result.put(tablePhysicsStr, sqlIdStr);
        }

        return result;
    }

}
