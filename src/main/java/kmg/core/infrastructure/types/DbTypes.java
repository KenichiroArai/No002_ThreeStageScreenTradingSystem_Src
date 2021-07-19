package kmg.core.infrastructure.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.utils.ArrayUtils;

/**
 * ＤＢの種類<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls")
public enum DbTypes implements Supplier<String> {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", null, null),

    /** PostgreSQL */
    POSTGRE_SQL("PostgreSQL", "PostgreSQL", new String[] {
        "Postgres"
    }),

    /** MySQL */
    MYSQL("MySQL", "MySQL", null),

    /** Oracle */
    ORACLE("Oracle", "Oracle", null),

    /** SQL Server */
    SQL_SERVER("SQL Server", "SQL Server", new String[] {
        "MS SQL"
    }),

    /* 定義：終了 */
    ;

    /** 名称 */
    private String name;

    /** 値 */
    private String value;

    /** 別名の配列 */
    private String[] aliasArray;

    /** 種類のマップ */
    private static final Map<String, DbTypes> valuesMap = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final DbTypes type : DbTypes.values()) {
            DbTypes.valuesMap.put(type.get(), type);

        }
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @since 1.0.0
     * @version 1.0.0
     * @param name
     *                   名称
     * @param value
     *                   値
     * @param aliasArray
     *                   別名の配列
     */
    DbTypes(final String name, final String value, final String[] aliasArray) {
        this.name = name;
        this.value = value;
        this.aliasArray = aliasArray;
    }

    /**
     * 値に該当する種類を返す<br>
     * <p>
     * 但し、値が存在しない場合は、指定無し（NONE）を返す
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param value
     *              値
     * @return 種類。指定無し（NONE）：値が存在しない場合
     */
    public static DbTypes getEnum(final String value) {

        DbTypes result = DbTypes.valuesMap.get(value);
        if (result == null) {

            result = NONE;
            return result;
        }

        return result;
    }

    /**
     * 対象に該当する種類を返す<br>
     * <p>
     * 対象は値または別名で大文字小文字を区別しないで検索する。<br>
     * 但し、対象が存在しない場合は、指定無し（NONE）を返す。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param target
     *               対象
     * @return 種類。指定無し（NONE）：対象が存在しない場合
     */
    public static DbTypes getEnumByTarget(final String target) {

        DbTypes result = DbTypes.valuesMap.get(target);
        if (result != null) {
            return result;
        }

        for (final DbTypes type : DbTypes.values()) {
            if (KmgString.equalsIgnoreCase(type.value, target)) {
                result = type;
                return result;
            }
            if (ArrayUtils.isEmpty(type.aliasArray)) {
                result = NONE;
                return result;
            }
            for (final String alias : type.aliasArray) {
                if (!KmgString.equalsIgnoreCase(alias, target)) {
                    continue;
                }
                result = type;
                return result;
            }
        }

        result = NONE;
        return result;
    }

    /**
     * 初期値の種類を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 初期値
     */
    public static DbTypes getInitValue() {

        final DbTypes result = NONE;
        return result;

    }

    /**
     * デフォルトの種類を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 初期値
     */
    public static DbTypes getDefault() {

        final DbTypes result = NONE;
        return result;

    }

    /**
     * 名称を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 名称
     */
    public String getName() {
        final String result = this.name;
        return result;
    }

    /**
     * 値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 値
     */
    public String getValue() {
        final String result = this.value;
        return result;
    }

    /**
     * 値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 値
     */
    @Override
    public String toString() {
        final String result = this.value;
        return result;
    }

    /**
     * 結合する文字列リストにデリミタを付加して文字列を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param <T>
     *                   結合する文字列の型
     * @param targetList
     *                   結合する文字列リスト
     * @return 結合する文字列リストにデリミタを付加した文字列
     */
    public <T> String join(final List<T> targetList) {
        final String result = this.join(targetList.toArray(new Object[0]));
        return result;
    }

    /**
     * 結合する文字列にデリミタを付加して文字列を返す<br>
     * <p>
     * 但し、結合する文字列がnullまたは空文字の場合は結合しない
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param targets
     *                結合する文字列
     * @return 結合する文字列にデリミタを付加した文字列
     */
    public String join(final Object... targets) {

        // TODO KenichiroArai 2021/05/01 ストリーム形式を検討する。

        String result = null;

        final StringBuilder sb = new StringBuilder();
        for (final Object target : targets) {
            if (target == null) {
                continue;
            }
            if (KmgString.isEmpty(target.toString())) {
                continue;
            }

            sb.append(target.toString());
            sb.append(this.value);
        }

        if (sb.length() > 0) {
            result = sb.substring(0, sb.length() - 1).toString();
        }

        return result;
    }

    /**
     * 分割する文字列を分割し、文字列の配列にして返す。<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param target
     *               分割する文字列
     * @return 分割した文字列の配列
     */
    public String[] split(final String target) {

        String[] result = null;
        if (KmgString.isEmpty(target)) {
            return result;
        }

        result = target.split(this.value);

        return result;
    }

    /**
     * 種類の値<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 種類の値
     */
    @Override
    public String get() {
        final String result = this.value;
        return result;
    }

    /**
     * 別名の配列を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 別名の配列
     */
    public String[] getAliasArray() {
        final String[] result = this.aliasArray;
        return result;
    }

}
