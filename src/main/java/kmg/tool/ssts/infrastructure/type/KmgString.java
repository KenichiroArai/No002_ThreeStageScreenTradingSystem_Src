package kmg.tool.ssts.infrastructure.type;

import kmg.tool.ssts.infrastructure.types.DelimiterTypes;

/**
 * ＫＭＧ文字列
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class KmgString {

    /** 空文字列 */
    public static final String EMPTY = ""; //$NON-NLS-1$

    /** 半角スペース */
    public static final String HALF_SPACE = " "; //$NON-NLS-1$

    /** 全角スペース */
    public static final String ALL_SPACE = "　"; //$NON-NLS-1$

    /** 改行 */
    public static final String LINE_SEPARATOR = System.lineSeparator();

    /** 値 */
    private String value;

    /**
     * コンストラクタ
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param value
     *              値
     */
    public KmgString(final String value) {
        this.value = value;
    }

    /**
     * 値を返す。
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
     * 対象文字列が空文字かどうかを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param target
     *               対象文字列
     * @return true：空文字列、false：空文字ではない
     */
    public static boolean isEmpty(final String target) {
        boolean result = true;

        final KmgString kmgString = new KmgString(target);
        result = kmgString.isEmpty();
        return result;
    }

    /**
     * 対象文字列が空文字ではないかどうかを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param target
     *               対象文字列
     * @return true：空文字ではない、false：空文字列
     */
    public static boolean isNotEmpty(final String target) {
        final boolean result = !KmgString.isEmpty(target);
        return result;
    }

    /**
     * キャピタライズを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param target
     *               対象文字列
     * @return キャピタライズ
     */
    public static String capitalize(final String target) {

        String result = null;

        /* 事前チェック */
        // 対象が空か
        if (KmgString.isEmpty(target)) {
            // 空の場合

            result = target;
            return result;
        }

        /* 先頭の文字だけ大文字にする */
        final char[] chars = target.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);

        /* 文字列にして返す */
        result = new String(chars);
        return result;
    }

    /**
     * 空文字かどうかを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空文字である、false：空文字ではない
     */
    public boolean isEmpty() {

        boolean result = true;

        if (this.value == null) {
            return result;
        }
        if (this.value.length() == 0) {
            return result;
        }

        result = false;
        return result;

    }

    /**
     * 空文字ではないかどうかを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空文字である、false：空文字ではない
     */
    public boolean isNotEmpty() {

        boolean result = !this.isEmpty();
        result = false;
        return result;

    }

    /**
     * スネークケースで返す。<br>
     * <p>
     * 例：aaaBbbCcc→aaa_bbb_ccc
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return スネークケースの文字列
     */
    public String toSnakeCase() {

        String result = null;

        /* 事前チェック */
        // 値が空か
        if (KmgString.isEmpty(this.value)) {
            // 空の場合

            return result;
        }

        /* 一文字の処理 */
        // 一文字か
        if (this.value.length() == 1) {
            // 一文字の場合

            return result;
        }

        /* スネークケースの文字列を作成 */
        final StringBuilder snakeCaseSb = new StringBuilder();
        int pos = 0;
        for (int i = 1; i < this.value.length(); ++i) {

            // ローケースか
            final boolean isLowerCase = Character.isLowerCase(this.value.charAt(i));
            if (isLowerCase) {
                // ローケースの場合

                continue;
            }

            // スネークケースの文字列があるか
            if (snakeCaseSb.length() != 0) {
                // ある場合

                // 区切りを入れる
                snakeCaseSb.append('_');
            }

            snakeCaseSb.append(this.value.substring(pos, i));
            pos = i;
        }
        if (snakeCaseSb.length() != 0) {
            snakeCaseSb.append('_');
        }
        snakeCaseSb.append(this.value.substring(pos, this.value.length()));

        result = snakeCaseSb.toString();

        return result;
    }

    /**
     * スネークケースに変換する。<br>
     * <p>
     * 例：aaaBbbCcc→aaa_bbb_ccc
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public void fromSnakeCase() {

        this.value = this.toSnakeCase();
    }

    /**
     * キャメルケースで返す。<br>
     * <p>
     * 例：aaa_bbb_ccc→aaaBbbCcc
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return キャメルケースの文字列
     */
    public String toCamelCase() {

        String result = null;

        /* 事前チェック */
        // 値が空か
        if (KmgString.isEmpty(this.value)) {
            // 空の場合

            return result;
        }

        /* 事前処理 */
        // ローケースに変換
        final String lowerCase = this.value.toLowerCase();

        // 単語の配列を取得
        final String[] words = DelimiterTypes.UNDERSCORE.split(lowerCase);

        // 一つ目の単語
        final String firstWord = KmgString.capitalize(words[0]);

        /* 一つの単語の処理 */
        // 単語が一つだけか
        if (words.length == 1) {
            // 一つだけの場合

            result = firstWord.substring(0, 1).toLowerCase().concat(firstWord.substring(1));
            return result;
        }

        /* キャメルケースの文字列を作成 */
        final StringBuffer camelCaseSb = new StringBuffer();

        // 一つ目の単語
        camelCaseSb.append(firstWord);

        // 単語の数分、キャメライズする
        for (int i = 1; i < words.length; i++) {
            camelCaseSb.append(KmgString.capitalize(words[i]));
        }
        result = camelCaseSb.substring(0, 1).toLowerCase().concat(camelCaseSb.substring(1));

        return result;
    }

    /**
     * スネークケースに変換する。<br>
     * <p>
     * 例：aaa_bbb_ccc→aaaBbbCcc
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public void fromCamelCase() {

        this.value = this.toCamelCase();
    }

    /**
     * 置換対象文字列を置換文字列に置換する。
     *
     * @param target
     *                   置換対象文字列
     * @param replacemen
     *                   置換文字列
     */
    public void replace(final CharSequence target, final CharSequence replacemen) {

        this.value = this.value.replace(target, replacemen);
    }

    /**
     * 文字列を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public String toString() {

        final String result = this.value;
        return result;
    }

}
