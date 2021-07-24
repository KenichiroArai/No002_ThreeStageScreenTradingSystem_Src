package kmg.tool.domain.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.DbTypes;
import kmg.tool.domain.service.InsertionSqlDataSheetCreationService;
import kmg.tool.domain.service.InsertionSqlFileCreationService;
import kmg.tool.infrastructure.utils.PoiUtils;

/**
 * 挿入ＳＱＬファイル作成サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class InsertionSqlFileCreationServiceImpl implements InsertionSqlFileCreationService {

    /** 設定シート名 */
    private static final String SETTING_SHEET_NAME = "設定情報";

    /** 一覧シート名 */
    private static final String LIST_NAME = "一覧";

    /** 入力パス */
    private Path inputPath;

    /** 出力パス */
    private Path outputPath;

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param inputPath
     *                   入力パス
     * @param outputPath
     *                   出力パス
     */
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final Path inputPath, final Path outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    /**
     * 挿入ＳＱＬを出力する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void outputInsertionSql() {

        /* ワークブック読み込み */
        try (final FileInputStream is = new FileInputStream(this.inputPath.toFile());
            final Workbook inputWb = WorkbookFactory.create(is);) {

            /* ＤＢの種類を取得 */
            final String dbTypesStr = InsertionSqlFileCreationServiceImpl.getDbSetting(inputWb);
            final DbTypes dbTypes = DbTypes.getEnumByTarget(dbTypesStr);

            /* ＳＱＬＩＤマップ */
            final Map<String, String> sqlIdMap = InsertionSqlFileCreationServiceImpl.getSqlIdMap(inputWb);

            for (int i = 0; i < inputWb.getNumberOfSheets(); i++) {
                final Sheet wkSheet = inputWb.getSheetAt(i);

                if (KmgString.equals(wkSheet.getSheetName(), InsertionSqlFileCreationServiceImpl.SETTING_SHEET_NAME)) {
                    continue;
                }
                if (KmgString.equals(wkSheet.getSheetName(), InsertionSqlFileCreationServiceImpl.LIST_NAME)) {
                    continue;
                }

                final InsertionSqlDataSheetCreationService insertionSqlDataSheetCreationService = new InsertionSqlDataSheetCreationServiceImpl();
                insertionSqlDataSheetCreationService.initialize(dbTypes, wkSheet, sqlIdMap, this.outputPath);
                insertionSqlDataSheetCreationService.outputInsertionSql();

            }

        } catch (final EncryptedDocumentException e) {
            e.printStackTrace();
            return;
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (final IOException e) {
            e.printStackTrace();
            return;
        }
    }

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
    private static String getDbSetting(final Workbook wk) {
        String result = null;

        final Sheet wkSheet = wk.getSheet(InsertionSqlFileCreationServiceImpl.SETTING_SHEET_NAME);
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
    private static Map<String, String> getSqlIdMap(final Workbook wk) {
        final Map<String, String> result = new HashMap<>();

        final Sheet wkSheet = wk.getSheet(InsertionSqlFileCreationServiceImpl.LIST_NAME);
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
