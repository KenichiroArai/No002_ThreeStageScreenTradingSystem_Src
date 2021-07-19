package kmg.tool.domain.service;

import java.nio.file.Path;

/**
 * 挿入ＳＱＬ作成サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface InsertSqlCreationService {

    /**
     * 挿入ＳＱＬを出力する<br>
     *
     * @param inputPath
     *                   入力パス
     * @param outputPath
     *                   出力パス
     */
    void outputInsertSql(final Path inputPath, final Path outputPath);
}
