package common.collectionObject;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Класс, представляющий коллекцию фильмов.
 * Содержит список объектов {@link Movie}, которые хранятся в коллекции.
 */
@XmlRootElement()
public class Movies implements Serializable {

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
//    @XmlElementWrapper(name = "moviesList")
    @XmlElement(name = "movie")
    public LinkedList<Movie> getMovies() {
        return collection;
    }
}
