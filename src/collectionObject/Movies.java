package collectionObject;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.LinkedList;

/**
 * Класс, представляющий коллекцию фильмов.
 * Содержит список объектов {@link Movie}, которые хранятся в коллекции.
 */
@XmlRootElement()
public class Movies {

    private LinkedList<Movie> collection;

    /**
     * Устанавливает коллекцию фильмов.
     *
     * @param collection коллекция фильмов, которую нужно установить
     */
    public void setMovies(LinkedList<Movie> collection) {
        this.collection = collection;
    }

    /**
     * Получает коллекцию фильмов.
     *
     * @return коллекция фильмов в виде {@link LinkedList<Movie>}
     */
    @XmlElement(name = "movie")
    public LinkedList<Movie> getMovies() {
        return collection;
    }
}
