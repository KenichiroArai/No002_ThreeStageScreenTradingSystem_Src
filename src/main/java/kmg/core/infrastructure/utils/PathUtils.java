package kmg.core.infrastructure.utils;

import java.nio.file.Path;

import org.springframework.lang.NonNull;

/**
 * パスユーティリティ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public final class PathUtils {

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    private PathUtils() {
        // 処理無し
    }

    /**
     * ファイル名のみを返す<br>
     * <p>
     * 拡張子のないファイル名のみを返す。<br>
     * ファイルパスがディレクトリの場合はファイルパスをそのまま返す。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return true：ファイル名のみ
     */
    public static String getFileNameOnly(@NonNull final Path filePath) {
        String result = filePath.getFileName().toString();
        result = result.substring(0, result.lastIndexOf('.'));
        return result;
    }
}
