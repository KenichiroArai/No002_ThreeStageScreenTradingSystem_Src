package kmg.tool.domain.logic.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.DbDataTypeTypes;
import kmg.core.infrastructure.types.DelimiterTypes;
import kmg.core.infrastructure.utils.LocalDateTimeUtils;
import kmg.core.infrastructure.utils.LocalDateUtils;
import kmg.tool.domain.logic.InsertionSqlDataSheetCreationLogic;
import kmg.tool.infrastructure.utils.PoiUtils;

/**
 * 挿入ＳＱＬデータシート作成ロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class InsertionSqlDataSheetCreationLogicImpl implements InsertionSqlDataSheetCreationLogic {

    /** 削除ＳＱＬテンプレート */
    private static final String DELETE_SQL_TEMPLATE = "DELETE FROM %s;";

    /** 挿入ＳＱＬテンプレート */
    private static final String INSERT_SQL_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s);";

    /**
     * テーブル論理名を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param inputSheet
     *                   入力シート
     * @return テーブル論理名
     */
    @Override
    public String getTableLogicNamee(final Sheet inputSheet) {
        final String result = inputSheet.getSheetName();
        return result;
    }

    /**
     * テーブル物理名を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param inputSheet
     *                   入力シート
     * @return テーブル物理名
     */
    @Override
    public String getTablePhysicsName(final Sheet inputSheet) {
        String result = null;
        final Cell wkCell = PoiUtils.getCell(inputSheet, 0, 0);
        result = PoiUtils.getStringValue(wkCell);
        return result;
    }

    /**
     * ＳＱＬＩＤを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sqlIdMap
     *                         ＳＱＬＩＤマップ
     * @param tablePhysicsName
     *                         テーブル物理名
     * @return ＳＱＬＩＤ
     */
    @Override
    public String getSqlId(final Map<String, String> sqlIdMap, final String tablePhysicsName) {
        final String result = sqlIdMap.get(tablePhysicsName);
        return result;
    }

    /**
     * 出力ファイルのディレクトリを作成する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param path
     *             パス
     * @throws IOException
     *                     入出力例外
     */
    @Override
    public void createOutputFileDirectories(final Path path) throws IOException {
        Files.createDirectories(path);
    }

    /**
     * 出力ファイルパスを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param path
     *                         パス
     * @param sqlId
     *                         ＳＱＬＩＤ
     * @param tablePhysicsName
     *                         テーブル物理名
     * @return 出力ファイルパス
     */
    @Override
    public Path getOutputFilePath(final Path path, final String sqlId, final String tablePhysicsName) {
        final Path result = Paths.get(path.toAbsolutePath().toString(),
            String.format("%s_insert_%s.sql", sqlId, tablePhysicsName));

        return result;
    }

    /**
     * 削除コメントを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param tableLogicName
     *                       テーブル論理名
     * @return 削除コメント
     */
    @Override
    public String getDeleteComment(final String tableLogicName) {
        final String result = String.format("-- %sのレコード削除", tableLogicName);
        return result;
    }

    /**
     * 削除ＳＱＬを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param tablePhysicsName
     *                         テーブル物理名
     * @return 削除ＳＱＬ
     */
    @Override
    public String getDeleteSql(final String tablePhysicsName) {
        final String result = String.format(InsertionSqlDataSheetCreationLogicImpl.DELETE_SQL_TEMPLATE,
            tablePhysicsName);
        return result;
    }

    /**
     * 物理名リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param inputSheet
     *                   入力シート
     * @return 物理名リスト
     */
    @Override
    public List<String> getPhysicsNameList(final Sheet inputSheet) {
        final List<String> result = new ArrayList<>();

        final Row physicsNameRow = inputSheet.getRow(2);
        for (short j = physicsNameRow.getFirstCellNum(); j <= physicsNameRow.getLastCellNum(); j++) {
            final Cell physicsNameCell = physicsNameRow.getCell(j);
            if (PoiUtils.isEmptyCell(physicsNameCell)) {
                break;
            }
            final String physicsName = PoiUtils.getStringValue(physicsNameCell);
            result.add(physicsName);
        }

        return result;
    }

    /**
     * 型リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param inputSheet
     *                   入力シート
     * @param columnNum
     *                   カラム数
     * @return 型リスト
     */
    @Override
    public List<DbDataTypeTypes> getTypeList(final Sheet inputSheet, final short columnNum) {
        final List<DbDataTypeTypes> result = new ArrayList<>();

        final Row typeRow = inputSheet.getRow(3);
        for (short j = typeRow.getFirstCellNum(); j < columnNum; j++) {
            final Cell typeCell = typeRow.getCell(j);

            final DbDataTypeTypes type = DbDataTypeTypes.getEnum(PoiUtils.getStringValue(typeCell));
            result.add(type);
        }

        return result;
    }

    /**
     * 挿入コメントを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param tableLogicName
     *                       テーブル論理名
     * @return 挿入コメント
     */
    @Override
    public String getInsertComment(final String tableLogicName) {
        final String result = String.format("-- %sのレコード挿入", tableLogicName);
        return result;
    }

    /**
     * 挿入ＳＱＬを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param tablePhysicsName
     *                              テーブル物理名
     * @param columnPhysicsNameList
     *                              カラム物理名リスト
     * @param datasRow
     *                              データ行
     * @param typeList
     *                              型リスト
     * @return 挿入ＳＱＬ
     */
    @Override
    public String getInsertSql(final String tablePhysicsName, final List<String> columnPhysicsNameList,
        final Row datasRow, final List<DbDataTypeTypes> typeList) {

        String result = null;

        final List<String> dataList = new ArrayList<>();

        for (short j = datasRow.getFirstCellNum(); j < columnPhysicsNameList.size(); j++) {
            final Cell dataCell = datasRow.getCell(j);
            final DbDataTypeTypes type = typeList.get(j);

            String outputData = null;
            final String dataStr = PoiUtils.getStringValue(dataCell);
            if (KmgString.isEmpty(dataStr)) {
                dataList.add(outputData);
                continue;
            }

            switch (type) {
            case NONE:
                // 指定無し
                outputData = PoiUtils.getStringValue(dataCell);
                outputData = String.format("'%s'", outputData);
                break;
            case INTEGER:
                // 4バイト整数
            case LONG:
                // 8バイト整数
            case SMALLSERIAL:
                // 自動4バイト
            case SERIAL:
                // 自動8バイト
                outputData = String.valueOf((int) dataCell.getNumericCellValue());
                break;
            case FLOAT:
                // 4バイト実数
            case DOUBLE:
                // 8バイト実数
            case BIG_DECIMAL:
                // 8バイト実数
                outputData = String.valueOf(dataCell.getNumericCellValue());
                break;
            case DATE:
                // 日付型
                final String dateStrTmp = PoiUtils.getStringValue(dataCell);
                // TODO KenichiroArai 2021/07/15 列挙型
                if (KmgString.equals("-infinity", dateStrTmp)) {
                    outputData = dateStrTmp;
                } else if (KmgString.equals("infinity", dateStrTmp)) {
                    outputData = dateStrTmp;
                } else {
                    final Date date = dataCell.getDateCellValue();
                    outputData = LocalDateUtils.formatYyyyMmDd(date);
                }
                outputData = String.format("'%s'", outputData);
                break;
            case TIME:
                // 日時型
                final String dateTimeStrTmp = PoiUtils.getStringValue(dataCell);
                // TODO KenichiroArai 2021/07/15 列挙型
                if (KmgString.equals("-infinity", dateTimeStrTmp)) {
                    outputData = dateTimeStrTmp;
                } else if (KmgString.equals("infinity", dateTimeStrTmp)) {
                    outputData = dateTimeStrTmp;
                } else {
                    final Date date = dataCell.getDateCellValue();
                    outputData = LocalDateTimeUtils.formatYyyyMmDdHhMmSsSss(date);
                }
                outputData = String.format("'%s'", outputData);
                break;
            case STRING:
                // 文字列型
                outputData = PoiUtils.getStringValue(dataCell);
                outputData = String.format("'%s'", outputData);
                break;
            }
            dataList.add(outputData);
        }

        result = String.format(InsertionSqlDataSheetCreationLogicImpl.INSERT_SQL_TEMPLATE, tablePhysicsName,
            DelimiterTypes.COMMA.joinAll(columnPhysicsNameList), DelimiterTypes.COMMA.joinAll(dataList));

        return result;
    }
}
