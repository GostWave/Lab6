package common.collectionObject;

import jakarta.xml.bind.annotation.XmlEnum;

import java.io.Serializable;

/**
 * Перечисление, представляющее рейтинги фильмов по версии MPAA.
 */
@XmlEnum
public enum MpaaRating implements Serializable {
    G,
    PG,
    PG_13,
    R,
    NC_17;
}
