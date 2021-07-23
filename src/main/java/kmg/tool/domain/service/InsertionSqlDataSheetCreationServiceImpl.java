package kmg.tool.domain.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
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
import kmg.tool.infrastructure.utils.PoiUtils;

/**
 * 挿入ＳＱＬデータシート作成サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class InsertionSqlDataSheetCreationServiceImpl implements InsertionSqlDataSheetCreationService {

    /** 削除ＳＱＬテンプレート */
    private static final String DELETE_SQL_TEMPLATE = "DELETE FROM %s;";

    /** 挿入ＳＱＬテンプレート */
    private static final String INSERT_SQL_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s);";

    /** 入力シート */
    private Sheet inputSheet;

    /** ＳＱＬＩＤマップ */
    private Map<String, String> sqlIdMap;

    /** 出力パス */
    private Path outputPath;

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param inputSheet
     *                   入力シート
     * @param sqlIdMap
     *                   ＳＱＬＩＤマップ
     * @param outputPath
     *                   出力パス
     */
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final Sheet inputSheet, final Map<String, String> sqlIdMap, final Path outputPath) {
        this.inputSheet = inputSheet;
        this.sqlIdMap = sqlIdMap;
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

        /* テーブル論理名の取得 */
        final String tableLogicName = this.inputSheet.getSheetName();

        /* テーブル物理名の取得 */
        final Cell tableNamePhysicsCell = PoiUtils.getCell(this.inputSheet, 0, 0);
        final String tablePhysicsName = PoiUtils.getStringValue(tableNamePhysicsCell);

        /* ＳＱＬＩＤの取得 */
        final String sqlId = this.sqlIdMap.get(tablePhysicsName);

        /* 出力ファイルパスの作成 */
        try {
            Files.createDirectories(this.outputPath);
        } catch (final IOException e) {
            e.printStackTrace();
            return;
        }
        final Path outputFilePath = Paths.get(this.outputPath.toAbsolutePath().toString(),
            String.format("%s_insert_%s.sql", sqlId, tablePhysicsName));

        try (BufferedWriter bw = Files.newBufferedWriter(outputFilePath, Charset.forName("MS932"))) {

            /* 削除ＳＱＬの出力 */
            final String deleteComment = String.format("-- %sのレコード削除", tableLogicName);
            bw.write(deleteComment);
            bw.newLine();
            final String deleteSql = String.format(InsertionSqlDataSheetCreationServiceImpl.DELETE_SQL_TEMPLATE,
                tablePhysicsName);
            bw.write(deleteSql);
            bw.newLine();
            bw.newLine();

            /* カラム数 */
            short columnNum = 0;

            /* 物理名マップの取得 */
            final Row physicsNameRow = this.inputSheet.getRow(2);
            final List<String> physicsNameList = new ArrayList<>();
            for (short j = physicsNameRow.getFirstCellNum(); j <= physicsNameRow.getLastCellNum(); j++) {
                final Cell physicsNameCell = physicsNameRow.getCell(j);
                if (PoiUtils.isEmptyCell(physicsNameCell)) {
                    break;
                }
                columnNum++;

                final String physicsName = PoiUtils.getStringValue(physicsNameCell);
                physicsNameList.add(physicsName);
            }

            /* 型マップの取得 */
            final Row typeRow = this.inputSheet.getRow(3);
            final List<DbDataTypeTypes> typeList = new ArrayList<>();
            for (short j = typeRow.getFirstCellNum(); j < columnNum; j++) {
                final Cell typeCell = typeRow.getCell(j);

                final DbDataTypeTypes type = DbDataTypeTypes.getEnum(PoiUtils.getStringValue(typeCell));
                typeList.add(type);
            }

            /* 挿入ＳＱＬの出力 */
            final String insertComment = String.format("-- %sのレコード挿入", tableLogicName);
            bw.write(insertComment);
            bw.newLine();
            for (int rowIdx = 4; rowIdx <= this.inputSheet.getLastRowNum(); rowIdx++) {
                final Row datasRow = this.inputSheet.getRow(rowIdx);
                if (datasRow == null) {
                    break;
                }

                final List<String> dataList = new ArrayList<>();
                for (short j = datasRow.getFirstCellNum(); j < columnNum; j++) {
                    final Cell dataCell = datasRow.getCell(j);

                    String outputData = null;
                    final String dataStr = PoiUtils.getStringValue(dataCell);
                    if (KmgString.isEmpty(dataStr)) {
                        dataList.add(outputData);
                        continue;
                    }

                    final DbDataTypeTypes type = typeList.get(j);
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

                final String datas = String.format(InsertionSqlDataSheetCreationServiceImpl.INSERT_SQL_TEMPLATE,
                    tablePhysicsName, DelimiterTypes.COMMA.joinAll(physicsNameList),
                    DelimiterTypes.COMMA.joinAll(dataList));
                bw.write(datas);
                bw.write(System.lineSeparator());
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return;
        }

    }

}
