package kmg.core.domain.model.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import kmg.core.domain.model.SqlPathModel;
import kmg.core.infrastructure.exception.KmgDomainException;
import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.LogMessageTypes;
import kmg.core.infrastructure.utils.PathUtils;

/**
 * ＳＱＬパスモデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class SqlPathModelImpl implements SqlPathModel {

    /** クラス */
    private final Class<?> zlass;

    /** ＳＱＬファイル名パス */
    private final Path sqlFileNamePath;

    /** ＳＱＬファイルパス */
    private Path sqlFilePath;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param object
     *                        オブジェクト
     * @param sqlFileNamePath
     *                        ＳＱＬファイル名パス
     */
    public SqlPathModelImpl(final Object object, final Path sqlFileNamePath) {
        this.zlass = object.getClass();
        this.sqlFileNamePath = sqlFileNamePath;
        this.setSqlFilePath();
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param zlass
     *                        クラス
     * @param sqlFileNamePath
     *                        ＳＱＬファイル名パス
     */
    public SqlPathModelImpl(final Class<?> zlass, final Path sqlFileNamePath) {
        this.zlass = zlass;
        this.sqlFileNamePath = sqlFileNamePath;
        this.setSqlFilePath();
    }

    /**
     * ＳＱＬファイルパスを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    private void setSqlFilePath() {
        this.sqlFilePath = PathUtils.getClassFullPath(this.zlass, this.sqlFileNamePath);
    }

    /**
     * ＳＱＬにして返す<br>
     * <p>
     * ＳＱＬパスで受け取ったファイルの中身をＳＱＬにして返す。<br>
     * 読み込んだファイルの末尾の改行は全て削除して返す。<br>
     * コメントに埋め込まれたパラメータをパラメータに変換する。<br>
     * コメントの後にあるサンプル値を削除する。<br>
     * 例：&#047;*:sampleId*&#047;'サンプル'→:sampleId<br>
     * 例：&#047;*:sampleId*&#047;123→:sampleId<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return SQLにして返す
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     */
    @Override
    public String toSql() throws KmgDomainException {
        String result = null;

        final StringBuilder sqlTmp = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(this.sqlFilePath)) {
            String line = null;
            while ((line = br.readLine()) != null) {
                final String tmp = SqlPathModelImpl.convertParameters(line);
                sqlTmp.append(tmp);
                sqlTmp.append(System.lineSeparator());
            }
        } catch (final IOException e) {
            // TODO KenichiroArai 2021/06/08 メッセージ
            final String errMsg = String.format("%sがありません。", this.sqlFileNamePath.toAbsolutePath());
            final LogMessageTypes logMsgTypes = LogMessageTypes.I00001;
            throw new KmgDomainException(errMsg, logMsgTypes, e);
        }

        result = sqlTmp.toString().replaceAll("\\R+$", KmgString.EMPTY);

        return result;
    }

    /**
     * パラメータをは変換する<br>
     * <p>
     * 変換する文字列が空の場合は、変換する文字列をそのまま返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param str
     *            変換する文字列
     * @return 返還後の文字列
     */
    @SuppressWarnings("nls")
    private static String convertParameters(final String str) {

        String result = null;
        if (KmgString.isEmpty(str)) {
            result = str;
            return result;
        }

        result = str.replaceAll("/\\*(.+)\\*/.*", "$1");

        return result;

    }

}
