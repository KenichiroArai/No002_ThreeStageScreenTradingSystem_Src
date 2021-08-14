package kmg.im.stock.tssts.infrastructure.exception;

import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;

/**
 * 三段階スクリーン・トレーディング・システムドメイン例外<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class TsstsDomainException extends TsstsException {

    /** デフォルトシリアルバージョンＵＩＤ */
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param errMsg
     *                    エラーメッセージ
     * @param logMsgTypes
     *                    ログメッセージの種類
     * @param logMsgArgs
     *                    ログメッセージの引数
     */
    public TsstsDomainException(final String errMsg, final LogMessageTypes logMsgTypes, final Object[] logMsgArgs) {
        super(errMsg, logMsgTypes, logMsgArgs);
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param errMsg
     *                    エラーメッセージ
     * @param logMsgTypes
     *                    ログメッセージの種類
     */
    public TsstsDomainException(final String errMsg, final LogMessageTypes logMsgTypes) {
        super(errMsg, logMsgTypes);
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param errMsg
     *                    エラーメッセージ
     * @param logMsgTypes
     *                    ログメッセージの種類
     * @param cause
     *                    原因
     */
    public TsstsDomainException(final String errMsg, final LogMessageTypes logMsgTypes, final Throwable cause) {
        super(errMsg, logMsgTypes, cause);
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param errMsg
     *                    エラーメッセージ
     * @param logMsgTypes
     *                    ログメッセージの種類
     * @param logMsgArgs
     *                    ログメッセージの引数
     * @param cause
     *                    原因
     */
    public TsstsDomainException(final String errMsg, final LogMessageTypes logMsgTypes, final Object[] logMsgArgs,
        final Throwable cause) {
        super(errMsg, logMsgTypes, logMsgArgs, cause);
    }

}
