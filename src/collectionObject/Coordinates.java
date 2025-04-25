package collectionObject;

/**
 * Класс, представляющий координаты (X, Y) для фильма.
 */
public class Coordinates {

    private Double x; // Максимальное значение поля: 160
    private Long y; // Максимальное значение поля: 170, Поле не может быть null

    /**
     * Устанавливает значение координаты X.
     * Значение X должно быть не больше 160.
     *
     * @param x координата X
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     * Устанавливает значение координаты Y.
     * Значение Y должно быть не больше 170.
     *
     * @param y координата Y
     */
    public void setY(Long y) {
        this.y = y;
    }

    /**
     * Получает значение координаты X.
     *
     * @return координата X
     */
    public Double getX() {
        return x;
    }

    /**
     * Получает значение координаты Y.
     *
     * @return координата Y
     */
    public Long getY() {
        return y;
    }
}
