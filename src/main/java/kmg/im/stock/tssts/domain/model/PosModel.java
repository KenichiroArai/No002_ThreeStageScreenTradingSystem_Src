package kmg.im.stock.tssts.domain.model;

import java.time.LocalDate;

import kmg.im.stock.tssts.infrastructure.types.DirectionTypes;

/**
 * ポジションモデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class PosModel {

    /** 日付 */
    private LocalDate date;

    /** 方向 */
    private DirectionTypes direction;

    /**
     * 日付を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param date
     *             日付
     */
    public void setDate(final LocalDate date) {
        this.date = date;
    }

    /**
     * 日付を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 日付
     */
    public LocalDate getDate() {
        final LocalDate result = this.date;
        return result;
    }

    /**
     * 方向を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param direction
     *                  方向
     */
    public void setDirection(final DirectionTypes direction) {
        this.direction = direction;
    }

    /**
     * 方向を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 方向
     */
    public DirectionTypes getDirection() {
        final DirectionTypes result = this.direction;
        return result;
    }

}
