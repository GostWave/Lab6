package app;

import collectionObject.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Scanner;

/**
 * Класс, заполняющий объект {@link Movie} данными, введёнными пользователем.
 */
public class MovieFiller {
    private final Scanner scanner = new Scanner(System.in);
    private String text;

    /**
     * Заполняет объект {@link Movie} данными и возвращает его.
     *
     * @param movie объект фильма, который нужно заполнить
     * @return заполненный объект {@link Movie}
     */
    public Movie fill(Movie movie) {
        movie.setCreationDate(LocalDate.now());
        Coordinates coordinates = new Coordinates();
        fillName(movie);
        fillCoordinates(movie, coordinates);
        fillGenre(movie);
        fillOscarsCount(movie);
        fillMpaaRating(movie);
        fillPerson(movie);
        return movie;
    }

    /**
     * Запрашивает у пользователя название фильма.
     *
     * @param movie объект фильма, в который будет установлено название
     */
    public void fillName(Movie movie) {
        System.out.println("Введите название фильма:");
        while (true) {
            text = scanner.nextLine();
            if (text != null && !text.trim().isEmpty()) {
                movie.setName(text);
                break;
            } else {
                System.out.println("Неверный формат ввода, попробуйте ещё раз:");
            }
        }
    }

    /**
     * Запрашивает у пользователя жанр фильма и устанавливает его.
     *
     * @param movie объект фильма, в который будет установлен жанр
     */
    public void fillGenre(Movie movie) {
        System.out.println("Выберите жанр фильма из следующих вариантов:\n" +
                MovieGenre.ACTION + "\n" +
                MovieGenre.WESTERN + "\n" +
                MovieGenre.MUSICAL + "\n" +
                MovieGenre.ADVENTURE + "\n" +
                MovieGenre.SCIENCE_FICTION);
        while (true) {
            text = scanner.nextLine().toUpperCase();
            try {
                movie.setGenre(MovieGenre.valueOf(text));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Неверное значение. Попробуйте ещё раз.");
            }
        }
    }

    /**
     * Запрашивает у пользователя координаты и устанавливает их.
     *
     * @param movie       объект фильма, в который будут установлены координаты
     * @param coordinates объект координат
     */
    public void fillCoordinates(Movie movie, Coordinates coordinates) {
        System.out.println("Введите значение координаты X:");
        double doubleValue;
        while (true) {
            doubleValue = fillDouble();
            if (doubleValue > 160) {
                System.out.println("Значение должно быть не больше 160. Попробуйте ещё раз.");
            } else {
                break;
            }
        }
        coordinates.setX(doubleValue);

        System.out.println("Введите значение координаты Y:");
        Long longValue;
        while (true) {
            longValue = fillLong();
            if (longValue > 170) {
                System.out.println("Значение должно быть не больше 170. Попробуйте ещё раз.");
            } else {
                break;
            }
        }
        coordinates.setY(longValue);
        movie.setCoordinates(coordinates);
    }

    /**
     * Запрашивает у пользователя количество премий Оскара и устанавливает его.
     *
     * @param movie объект фильма, в который будет установлено количество Оскаров
     */
    public void fillOscarsCount(Movie movie) {
        System.out.println("Введите количество премий Оскара:");
        int intValue = fillInt();
        movie.setOscarsCount(intValue);
    }

    /**
     * Запрашивает у пользователя рейтинг MPAA и устанавливает его.
     *
     * @param movie объект фильма, в который будет установлен MPAA рейтинг
     */
    public void fillMpaaRating(Movie movie) {
        System.out.println("Выберите значение MPAA Rating из следующих вариантов:\n" +
                MpaaRating.G + "\n" +
                MpaaRating.PG + "\n" +
                MpaaRating.PG_13 + "\n" +
                MpaaRating.R + "\n" +
                MpaaRating.NC_17);
        while (true) {
            text = scanner.nextLine();
            try {
                movie.setMpaaRating(MpaaRating.valueOf(text));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Неверное значение. Попробуйте ещё раз.");
            }
        }
    }

    /**
     * Запрашивает у пользователя данные о режиссёре фильма и устанавливает их.
     *
     * @param movie объект фильма, в который будут установлены данные о режиссёре
     * @throws DateTimeException если введена некорректная дата
     */
    public void fillPerson(Movie movie) throws DateTimeException {
        Person person = new Person();
        System.out.println("Введите имя режиссёра:");
        while (true) {
            text = scanner.nextLine();
            if (isOnlyLetters(text)) {
                person.setName(text.trim());
                break;
            } else {
                System.out.println("Неверный формат ввода: строка должна содержать только буквы. Попробуйте ещё раз.");
            }
        }

        System.out.println("Введите дату рождения режиссёра в формате dd.MM.yyyy:");
        while (true) {
            try {
                String text = scanner.nextLine();
                String[] content = text.split("\\.");
                if (content.length != 3) {
                    throw new IllegalArgumentException("Ошибка: формат должен быть dd.MM.yyyy.");
                }
                ZonedDateTime date = ZonedDateTime.of(Integer.parseInt(content[2]), Integer.parseInt(content[1]), Integer.parseInt(content[0]), 0, 0, 0, 0, ZoneId.of("Europe/Moscow"));
                if (date.isAfter(ZonedDateTime.now())) {
                    throw new DateTimeException("Ошибка: дата не может быть в будущем.");
                }
                person.setBirthday(date);
                movie.setOperator(person);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: дата должна содержать только числа. Попробуйте ещё раз.");
            } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                System.out.println("Ошибка: некорректный формат даты. Используйте dd.MM.yyyy. Попробуйте ещё раз.");
            } catch (DateTimeException e) {
                System.out.println("Ошибка: дата рождения не может быть позже текущей даты. Попробуйте ещё раз.");
            }
        }
        System.out.println("Введите номер паспорта режиссёра");
        while (true) {
            text = scanner.nextLine();
            if (text != null && text.length() >= 6 ) {
                person.setPassportID(text);
                break;
            } else {
                System.out.println("Неверный формат ввода: строка должна быть длиной не менее 6 символов. Попробуйте ещё раз");
            }
        }
    }

    /**
     * @return введённое пользователем число типа double
     */
    public double fillDouble() {
        while (true) {
            try {
                text = scanner.nextLine().replace(",", ".");
                return Double.parseDouble(text);
            } catch (NumberFormatException e) {
                System.out.println("Нужно вводить число.");
            }
        }
    }

    /**
     * @return введённое пользователем число типа Long
     */
    public Long fillLong() {
        while (true) {
            try {
                text = scanner.nextLine();
                return Long.parseLong(text);
            } catch (NumberFormatException e) {
                System.out.println("Нужно вводить число типа Long.");
            }
        }
    }

    /** @return введённое пользователем число типа int */
    public int fillInt() {
        while (true) {
            try {
                text = scanner.nextLine();
                int value = Integer.parseInt(text);
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Число должно быть больше 0. Попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Нужно вводить число типа int и больше 0.");
            }
        }
    }

    /** @return {@code true}, если строка содержит только буквы */
    public static boolean isOnlyLetters(String text) {
        if (text.isEmpty()) return false;
        for (char ch : text.toCharArray()) {
            if (!Character.isLetter(ch)) {
                return false;
            }
        }
        return true;
    }
}
