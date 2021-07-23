package kmg.tool.domain.service;

import java.nio.file.Path;

/**
 * 挿入ＳＱＬ作成サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class InsertionSqlCreationServiceImpl implements InsertionSqlCreationService {

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

        final InsertionSqlFileCreationService insertionSqlFileCreationService = new InsertionSqlFileCreationServiceImpl();
        insertionSqlFileCreationService.initialize(this.inputPath, this.outputPath);
        insertionSqlFileCreationService.outputInsertionSql();

    }

}
