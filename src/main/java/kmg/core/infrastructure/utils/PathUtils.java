package kmg.core.infrastructure.utils;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.util.ObjectUtils;

import kmg.core.infrastructure.type.KmgString;

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
     * ファイルパスがnullの場合は、nullを返す。<br>
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
    public static String getFileNameOnly(final Path filePath) {
        String result = null;

        if (filePath == null) {
            return result;
        }

        final String fileNameTmp = filePath.getFileName().toString();
        result = fileNameTmp.substring(0, fileNameTmp.lastIndexOf('.'));
        return result;
    }

    /**
     * ビルドパスを返す<br>
     * <p>
     * クラスのビルドパスを返す。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param zlass
     *              クラス
     * @return ビルドパス
     */
    public static Path getBinPath(final Class<?> zlass) {
        Path result = null;

        if (zlass == null) {
            return result;
        }

        try {
            result = Paths.get(zlass.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (final URISyntaxException e) {
            // TODO KenichiroArai 2021/06/08 KMGの例外処でスローする。
            e.printStackTrace();
        }

        return result;
    }

    /**
     * ビルドパスを返す<br>
     * <p>
     * オブジェクトのビルドパスを返す。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param obj
     *            オブジェクト
     * @return ビルドパス
     */
    public static Path getBinPath(final Object obj) {
        Path result = null;

        if (obj == null) {
            return result;
        }

        result = PathUtils.getBinPath(obj.getClass());

        return result;
    }

    /**
     * オブジェクトとファイル名からクラスのフルパスを返す<br>
     * <p>
     * 例：クラスに「com.sample.SampleDao」、ファイル名に「sample.sql」の場合、
     * 「com/sample/sample_dao/sample.sql」を返す。<br>
     * オブジェクトが空の場合は、空を返す。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param object
     *                 オブジェクト
     * @param fileName
     *                 ファイル名
     * @return クラスのフルパス
     */
    public static Path getClassFullPath(final Object object, final String fileName) {

        Path result = null;

        if (ObjectUtils.isEmpty(object)) {
            return result;
        }

        result = PathUtils.getClassFullPath(object.getClass(), fileName);
        return result;
    }

    /**
     * クラスとファイル名からクラスのフルパスを返す<br>
     * <p>
     * 例：クラスに「com.sample.SampleDao」、ファイル名に「sample.sql」の場合、
     * 「com/sample/sample_dao/sample.sql」を返す。<br>
     * クラスが空の場合は、空を返す。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param zlass
     *                 クラス
     * @param fileName
     *                 ファイル名
     * @return クラスのフルパス
     */
    public static Path getClassFullPath(final Class<?> zlass, final Path fileName) {

        Path result = null;

        if (zlass == null) {
            return result;
        }

        final Path binPath = PathUtils.getBinPath(zlass);
        result = PathUtils.getClassFullPath(binPath, zlass.getPackageName(), zlass.getSimpleName(), fileName);
        return result;
    }

    /**
     * ビルドパスとパッケージ名、クラス名、ファイル名からクラスのフルパスを返す<br>
     * <p>
     * 例：パッケージ名に「com.sample」、クラス名に「SampleDao」、ファイル名に「sample.sql」の場合、
     * 「ビルドパス/com/sample/sample_dao/sample.sql」を返す。<br>
     * クラス名が空の場合は、空を返す。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param binPath
     *                    ビルドパス
     * @param packageName
     *                    パッケージ名
     * @param className
     *                    クラス名
     * @param fileName
     *                    ファイル名
     * @return クラスのフルパス
     */
    private static Path getClassFullPath(final Path binPath, final String packageName, final String className,
        final Path fileName) {

        Path result = null;

        if (KmgString.isEmpty(className)) {
            return result;
        }

        String classFullPath = className;
        // $が含まれている場合は$より前のクラスを取得する
        final int dollarIndex = classFullPath.indexOf('$');
        if (dollarIndex > 0) {
            classFullPath = classFullPath.substring(0, dollarIndex);
        }

        classFullPath = KmgString.snakeCase(classFullPath);
        classFullPath = KmgString.concat(packageName.replace('.', '/'), "/", classFullPath); //$NON-NLS-1$

        String binPathStr = KmgString.EMPTY;
        if (binPath != null) {
            binPathStr = binPath.toAbsolutePath().toString();
        }

        result = Paths.get(String.format("%s/%s/%s", binPathStr, classFullPath, fileName)); //$NON-NLS-1$

        return result;
    }
}
