package collectionObject;

import IO.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Класс, представляющий фильм.
 * Нелбходим для хранения информации о фильме, такой как название, координаты, жанр, рейтинг, количество Оскаров и т. д.
 */
// Lombok
@XmlRootElement()
@XmlType(propOrder = {"name", "coordinates", "genre", "mpaaRating", "oscarsCount", "operator", "creationDate"})
public class Movie implements Comparable<Movie> {

    private Long id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private LocalDate creationDate; // Поле не может быть null, Значение этого поля должно быть генерироваться автоматически
    private int oscarsCount; // Значение поля должно быть больше 0
    private MovieGenre genre; // Поле может быть null
    private MpaaRating mpaaRating; // Поле не может быть null
    private Person operator; // Поле не может быть null


    @Override
    public int compareTo(Movie movie) {
        return Long.compare(this.id, movie.getId());
    }

    /**
     * Устанавливает id фильма.
     *
     * @param id идентификатор фильма
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Получает id фильма.
     *
     * @return id фильма
     */
    @XmlAttribute
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает название фильма.
     *
     * @param name название фильма
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Получает название фильма.
     *
     * @return название фильма
     */
    @XmlElement
    public String getName() {
        return name;
    }

    /**
     * Устанавливает координаты фильма.
     *
     * @param coordinates координаты фильма
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Получает координаты фильма.
     *
     * @return координаты фильма
     */
    @XmlElement
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Устанавливает дату создания фильма.
     *
     * @param localDate дата создания фильма
     */
    public void setCreationDate(LocalDate localDate) {
        this.creationDate = localDate;
    }

    /**
     * Получает дату создания фильма.
     *
     * @return дата создания фильма
     */
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Устанавливает количество премий Оскара.
     *
     * @param oscarsCount количество премий Оскара
     */
    public void setOscarsCount(int oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    /**
     * Получает количество премий Оскара.
     *
     * @return количество премий Оскара
     */
    @XmlElement
    public int getOscarsCount() {
        return oscarsCount;
    }

    /**
     * Устанавливает жанр фильма.
     *
     * @param genre жанр фильма
     */
    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    /**
     * Получает жанр фильма.
     *
     * @return жанр фильма
     */
    public MovieGenre getGenre() {
        return genre;
    }

    /**
     * Устанавливает рейтинг фильма по системе MPAA.
     *
     * @param mpaaRating рейтинг фильма
     */
    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    /**
     * Получает рейтинг фильма по системе MPAA.
     *
     * @return рейтинг фильма
     */
    @XmlElement
    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    /**
     * Устанавливает режиссера фильма.
     *
     * @param operator режиссер фильма
     */
    public void setOperator(Person operator) {
        this.operator = operator;
    }

    /**
     * Получает режиссера фильма.
     *
     * @return оператор фильма
     */
    @XmlElement
    public Person getOperator() {
        return operator;
    }

    /**
     * Возвращает строковое представление фильма.
     * Строка содержит информацию о названии, координатах, жанре, количестве Оскаров,
     * рейтинге MPAA и режиссере.
     *
     * @return строковое представление фильма
     */
    @Override
    public String toString() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "Movie{" +
                "creationDate: " + creationDate +
                ", id = " + id +
                ", name: " + name +
                ", Coordinate X: " + coordinates.getX() +
                ", Coordinate Y: " + coordinates.getY() +
                ", genre: " + genre +
                ", oscarsCount: " + oscarsCount +
                ", mpaaRating: " + mpaaRating +
                ", Operator{name: " + operator.getName() +
                ", birthday: " + operator.getBirthday().format(dateTimeFormatter) +
                ", passportID: " + operator.getPassportID() +
                "}}";
    }
}
