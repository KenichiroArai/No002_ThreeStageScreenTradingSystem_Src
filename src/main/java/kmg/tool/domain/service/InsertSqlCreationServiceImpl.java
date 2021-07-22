package kmg.tool.domain.service;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.DbDataTypeTypes;
import kmg.core.infrastructure.types.DbTypes;
import kmg.core.infrastructure.types.DelimiterTypes;
import kmg.core.infrastructure.utils.LocalDateTimeUtils;
import kmg.core.infrastructure.utils.LocalDateUtils;
import kmg.tool.infrastructure.utils.PoiUtils;

/**
 * 挿入ＳＱＬ作成サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class InsertSqlCreationServiceImpl implements InsertSqlCreationService {

    /** 設定シート名 */
    private static final String SETTING_SHEET_NAME = "設定情報";

    /** 一覧シート名 */
    private static final String LIST_NAME = "一覧";

    /** 削除ＳＱＬテンプレート */
    private static final String DELETE_SQL_TEMPLATE = "DELETE FROM %s;";

    /** 挿入ＳＱＬテンプレート */
    private static final String INSERT_SQL_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s);";

    /**
     * 挿入ＳＱＬを出力する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param inputPath
     *                   入力パス
     * @param outputPath
     *                   出力パス
     */
    @Override
    public void outputInsertSql(final Path inputPath, final Path outputPath) {

        /* ワークブック読み込み */
        try (final FileInputStream is = new FileInputStream(inputPath.toFile());
            final Workbook inputWb = WorkbookFactory.create(is);) {

            /* ＤＢの種類を取得 */
            final String dbTypesStr = InsertSqlCreationServiceImpl.getDbSetting(inputWb);
            final DbTypes dbTypes = DbTypes.getEnumByTarget(dbTypesStr);
            System.out.println(String.format("ＤＢの種類 = %s", dbTypes.getValue()));

            /* ＳＱＬＩＤマップ */
            final Map<String, String> sqlIdMap = InsertSqlCreationServiceImpl.getSqlIdMap(inputWb);

            for (int i = 0; i < inputWb.getNumberOfSheets(); i++) {
                final Sheet wkSheet = inputWb.getSheetAt(i);

                if (KmgString.equals(wkSheet.getSheetName(), InsertSqlCreationServiceImpl.SETTING_SHEET_NAME)) {
                    continue;
                }
                if (KmgString.equals(wkSheet.getSheetName(), InsertSqlCreationServiceImpl.LIST_NAME)) {
                    continue;
                }

                /* テーブル論理名の取得 */
                final String tableLogicName = wkSheet.getSheetName();

                /* テーブル物理名の取得 */
                final Cell tableNamePhysicsCell = PoiUtils.getCell(wkSheet, 0, 0);
                final String tablePhysicsName = PoiUtils.getStringValue(tableNamePhysicsCell);

                /* ＳＱＬＩＤの取得 */
                final String sqlId = sqlIdMap.get(tablePhysicsName);

                /* 出力ファイルパスの作成 */
                final Path outputFilePath = Paths.get(outputPath.toAbsolutePath().toString(),
                    String.format("%s_insert_%s.sql", sqlId, tablePhysicsName));

                try (BufferedWriter bw = Files.newBufferedWriter(outputFilePath, Charset.forName("MS932"))) {

                    /* 削除ＳＱＬの出力 */
                    final String deleteComment = String.format("-- %sのレコード削除", tableLogicName);
                    bw.write(deleteComment);
                    bw.newLine();
                    final String deleteSql = String.format(InsertSqlCreationServiceImpl.DELETE_SQL_TEMPLATE,
                        tablePhysicsName);
                    bw.write(deleteSql);
                    bw.newLine();
                    bw.newLine();

                    /* カラム数 */
                    short columnNum = 0;

                    /* 物理名マップの取得 */
                    final Row physicsNameRow = wkSheet.getRow(2);
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
                    final Row typeRow = wkSheet.getRow(3);
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
                    for (int rowIdx = 4; rowIdx <= wkSheet.getLastRowNum(); rowIdx++) {
                        final Row datasRow = wkSheet.getRow(rowIdx);
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

                        final String datas = String.format(InsertSqlCreationServiceImpl.INSERT_SQL_TEMPLATE,
                            tablePhysicsName, DelimiterTypes.COMMA.joinAll(physicsNameList),
                            DelimiterTypes.COMMA.joinAll(dataList));
                        bw.write(datas);
                        bw.write(System.lineSeparator());
                    }
                }

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

        final Sheet wkSheet = wk.getSheet(InsertSqlCreationServiceImpl.SETTING_SHEET_NAME);
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

        final Sheet wkSheet = wk.getSheet(InsertSqlCreationServiceImpl.LIST_NAME);
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
