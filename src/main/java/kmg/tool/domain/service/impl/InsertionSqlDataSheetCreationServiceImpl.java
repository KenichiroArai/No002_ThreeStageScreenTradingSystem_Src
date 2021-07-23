package kmg.tool.domain.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import kmg.core.infrastructure.types.DbDataTypeTypes;
import kmg.tool.domain.logic.InsertionSqlDataSheetCreationLogic;
import kmg.tool.domain.logic.impl.InsertionSqlDataSheetCreationLogicImpl;
import kmg.tool.domain.service.InsertionSqlDataSheetCreationService;

/**
 * 挿入ＳＱＬデータシート作成サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class InsertionSqlDataSheetCreationServiceImpl implements InsertionSqlDataSheetCreationService {

    /** 入力シート */
    private Sheet inputSheet;

    /** ＳＱＬＩＤマップ */
    private Map<String, String> sqlIdMap;

    /** 出力パス */
    private Path outputPath;

    /** 挿入ＳＱＬデータシート作成ロジック */
    private final InsertionSqlDataSheetCreationLogic insertionSqlDataSheetCreationLogic;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public InsertionSqlDataSheetCreationServiceImpl() {
        this.insertionSqlDataSheetCreationLogic = new InsertionSqlDataSheetCreationLogicImpl();
    }

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
        final String tableLogicName = this.insertionSqlDataSheetCreationLogic.getTableLogicNamee(this.inputSheet);

        /* テーブル物理名の取得 */
        final String tablePhysicsName = this.insertionSqlDataSheetCreationLogic.getTablePhysicsName(this.inputSheet);

        /* ＳＱＬＩＤの取得 */
        final String sqlId = this.insertionSqlDataSheetCreationLogic.getSqlId(this.sqlIdMap, tablePhysicsName);

        /* 出力ファイルのディレクトリの作成 */
        try {
            this.insertionSqlDataSheetCreationLogic.createOutputFileDirectories(this.outputPath);
        } catch (final IOException e) {
            e.printStackTrace();
            return;
        }

        /* 出力ファイルパスの取得 */
        final Path outputFilePath = this.insertionSqlDataSheetCreationLogic.getOutputFilePath(this.outputPath, sqlId,
            tablePhysicsName);

        try (BufferedWriter bw = Files.newBufferedWriter(outputFilePath, Charset.forName("MS932"))) {

            /* 削除ＳＱＬの出力 */
            final String deleteComment = this.insertionSqlDataSheetCreationLogic.getDeleteComment(tableLogicName);
            bw.write(deleteComment);
            bw.newLine();
            final String deleteSql = this.insertionSqlDataSheetCreationLogic.getDeleteSql(tablePhysicsName);
            bw.write(deleteSql);
            bw.newLine();
            bw.newLine();

            /* カラム物理名リストの取得 */
            final List<String> columnPhysicsNameList = this.insertionSqlDataSheetCreationLogic
                .getPhysicsNameList(this.inputSheet);

            /* 型リストの取得 */
            final List<DbDataTypeTypes> typeList = this.insertionSqlDataSheetCreationLogic.getTypeList(this.inputSheet,
                (short) columnPhysicsNameList.size());

            /* 挿入ＳＱＬの出力 */
            final String insertComment = this.insertionSqlDataSheetCreationLogic.getInsertComment(tableLogicName);
            bw.write(insertComment);
            bw.newLine();
            for (int rowIdx = 4; rowIdx <= this.inputSheet.getLastRowNum(); rowIdx++) {
                final Row datasRow = this.inputSheet.getRow(rowIdx);
                if (datasRow == null) {
                    break;
                }

                final String datas = this.insertionSqlDataSheetCreationLogic.getInsertSql(tablePhysicsName,
                    columnPhysicsNameList, datasRow, typeList);
                bw.write(datas);
                bw.write(System.lineSeparator());
            }
        } catch (final IOException e) {
            e.printStackTrace();
            return;
        }

    }

}
