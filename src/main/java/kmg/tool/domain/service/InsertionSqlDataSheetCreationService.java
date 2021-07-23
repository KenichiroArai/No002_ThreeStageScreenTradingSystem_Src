package kmg.tool.domain.service;

import java.nio.file.Path;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * 挿入ＳＱＬデータシート作成サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface InsertionSqlDataSheetCreationService {

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
    void initialize(Sheet inputSheet, Map<String, String> sqlIdMap, final Path outputPath);

    /**
     * 挿入ＳＱＬを出力する<br>
     */
    void outputInsertionSql();
}
