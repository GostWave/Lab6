package common.collectionObject;

import jakarta.xml.bind.annotation.XmlEnum;

import java.io.Serializable;

/**
 * Перечисление, представляющее жанры фильмов.
 */
@XmlEnum
public enum MovieGenre implements Serializable {
    ACTION,
    WESTERN,
    MUSICAL,
    ADVENTURE,
    SCIENCE_FICTION;
}
