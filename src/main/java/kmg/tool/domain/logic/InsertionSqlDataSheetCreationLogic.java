package kmg.tool.domain.logic;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import kmg.core.infrastructure.types.DbDataTypeTypes;
import kmg.core.infrastructure.types.DbTypes;

/**
 * 挿入ＳＱＬデータシート作成ロジックインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface InsertionSqlDataSheetCreationLogic {

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
    String getTableLogicNamee(Sheet inputSheet);

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
    String getTablePhysicsName(Sheet inputSheet);

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
    String getSqlId(final Map<String, String> sqlIdMap, final String tablePhysicsName);

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
    void createOutputFileDirectories(Path path) throws IOException;

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
    Path getOutputFilePath(Path path, String sqlId, String tablePhysicsName);

    /**
     * 文字セットを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dbTypes
     *                ＤＢの種類
     * @return 文字セット
     */
    Charset getCharset(DbTypes dbTypes);

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
    String getDeleteComment(String tableLogicName);

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
    String getDeleteSql(String tablePhysicsName);

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
    List<String> getPhysicsNameList(Sheet inputSheet);

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
    List<DbDataTypeTypes> getTypeList(final Sheet inputSheet, final short columnNum);

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
    String getInsertComment(String tableLogicName);

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
    String getInsertSql(final String tablePhysicsName, final List<String> columnPhysicsNameList, final Row datasRow,
        final List<DbDataTypeTypes> typeList);
}
