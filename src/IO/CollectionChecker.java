package IO;

import collectionObject.Coordinates;
import collectionObject.Movie;
import collectionObject.Movies;

import java.util.Objects;
import java.util.function.DoublePredicate;


public class CollectionChecker {
    Movies movies;
    public CollectionChecker(Movies movies){
        this.movies=movies;
    }
    public boolean checkCollection(){
        for (Movie movie: movies.getMovies()){
            if (!checkMovie(movie)){
                return false;
            }
        }
        return true;
    }
    public boolean checkMovie(Movie movie){
        return checkId(movie) & checkName(movie) & checkCoordinates(movie) & checkCreationDate(movie)
                & checkOscarsCount(movie) & checkGenre(movie) & checkMpaaRating(movie)
                & checkOperatorName(movie) & checkOperatorBirthday(movie)
                & checkOperatorPassportID(movie);
    }
    public boolean checkName(Movie movie){
        if (movie.getName()==null | movie.getName().isEmpty()){
            System.out.println("Название фильма не может быть пустым");
            return false;
        } else {
            return true;
        }
    }

    public boolean checkCoordinates(Movie movie){
        Coordinates coordinates=movie.getCoordinates();
        if (coordinates==null){
            System.out.println("Координаты фильма не могут быть пустыми");
            return false;
        } else if (coordinates.getY() == null){
            System.out.println("Координата Y фильма записана неверно: она должна быть числом типа Long");
            return false;
        } else if (coordinates.getX() == null) {
            System.out.println("Координата X фильма записана неверно: она должна быть числом типа double");
            return false;
        } else if (coordinates.getX()>160){
            System.out.println("Координата X фильма не может быть больше 160");
            return false;
        } else if (coordinates.getY()>170){
            System.out.println("Координата Y фильма не может быть больше 170");
            return false;
        } else {
            return true;

        }
    }
    public boolean checkValueX(Movie movie){
        try{
            Double.parseDouble(String.valueOf(movie.getCoordinates().getX()));
            return true;
        } catch (NumberFormatException e){
            System.out.println("Координата X должна быть числом");
            return false;
        }
    }

    public boolean checkId(Movie movie){
        if (movie.getId()==null){
            System.out.println("Id фильма не может быть пустым");
            return false;
        } else {
            return true;
        }
    }

    public boolean checkCreationDate(Movie movie) {
        if (movie.getCreationDate() == null) {
            System.out.println("Дата создания фильма должна быть в формате yyyy-MM-dd (например, 2020-12-30)");
            return false;
        } else {
            return true;
        }
    }
    public boolean checkOscarsCount(Movie movie){
        if (movie.getOscarsCount()<=0){
            System.out.println("Количество оскаров должно быть больше 0");
            return false;
        } else {
            return true;
        }
    }
    public boolean checkGenre(Movie movie){
        if (movie.getGenre()==null){
            System.out.println("Неверно указан жанр фильма");
            return false;
        } else {
            return true;
        }
    }
    public boolean checkMpaaRating(Movie movie){
        if (movie.getMpaaRating()==null){
            System.out.println("Неверно указан MpaaRating фильма");
            return false;
        } else {
            return true;
        }
    }
    public boolean checkOperatorName(Movie movie){
        if (movie.getOperator().getName()==null | Objects.equals(movie.getOperator().getName(), "")){
            System.out.println("Имя режиссёра фильма не может быть пустым");
            return false;
        } else {
            return true;
        }
    }
    public boolean checkOperatorBirthday(Movie movie){
        if (movie.getOperator().getBirthday()==null){
            System.out.println("Дата создания фильма должна быть в формате yyyy-MM-dd (например, 2022-10-30)");
            return false;
        } else {
            return true;
        }
    }
    public boolean checkOperatorPassportID(Movie movie){
        if (movie.getOperator().getPassportID()==null | Objects.equals(movie.getOperator().getPassportID(), "")){
            System.out.println("Номер паспорта режиссёра фильма не может быть пустым");
            return false;
        } else if (movie.getOperator().getPassportID().length()<6){
            System.out.println("Номер паспорта режиссёра фильма должен быть длиной не меньше 6 символов");
            return false;
        } else {
            return true;

        }
    }
}
