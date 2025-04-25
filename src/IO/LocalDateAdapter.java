package IO;


import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/**
 * Адаптер для преобразования {@link Temporal} объектов (LocalDate и ZonedDateTime)
 * в строковый формат XML и обратно.
 */
public class LocalDateAdapter extends XmlAdapter<String, Temporal> {
    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter ZONED_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    /**
     * Преобразует строковое представление даты в объект {@link Temporal}.
     *
     * @param v строковое представление даты
     * @return объект {@link ZonedDateTime}, если строка содержит 'T', иначе {@link LocalDate}
     */
    @Override
    public Temporal unmarshal(String v) {
        if (v == null) {
            return null;
        }

        if (v.contains("T")) {
            return ZonedDateTime.parse(v, ZONED_DATE_TIME_FORMATTER);
        } else {
            return LocalDate.parse(v, LOCAL_DATE_FORMATTER);
        }
    }

    /**
     * Преобразует объект {@link Temporal} в строковое представление.
     *
     * @param v объект {@link Temporal} для преобразования
     * @return строковое представление даты
     */
    @Override
    public String marshal(Temporal v) {
        if (v == null) {
            return null;
        }

        if (v instanceof ZonedDateTime) {
            return ((ZonedDateTime) v).format(ZONED_DATE_TIME_FORMATTER);
        } else {
            return ((LocalDate) v).format(LOCAL_DATE_FORMATTER);
        }
    }
}
