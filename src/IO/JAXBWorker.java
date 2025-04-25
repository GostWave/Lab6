package IO;

import collectionObject.Movies;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.*;


/**
 * Класс JAXBWorker предоставляет методы для конвертации объектов в XML формат и обратно,
 * используя Java Architecture for XML Binding (JAXB).
 * Этот класс используется для сериализации и десериализации коллекции фильмов в/из XML.
 */
public class JAXBWorker {

    /**
     * Название файла для сохранения XML-данных.
     * Значение этого параметра извлекается из переменной окружения {@code COLLECTION_FILE}.
     */
    private final String fileName = System.getenv("COLLECTION_FILE");

    /**
     * Преобразует объект коллекции фильмов в строку XML.
     *
     * @param collection Коллекция фильмов, которую нужно преобразовать в XML.
     * @return Строка, представляющая сериализованный XML.
     * @throws JAXBException Если произошла ошибка при создании маршаллера или сериализации.
     */
    public String convertObjectToXml(Movies collection) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(Movies.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);


        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(collection, os);

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8")) {
            writer.write(os.toString());
            System.out.println("Коллекция успешно записана в файл");
        } catch (IOException e) {
            System.out.println("Не удалось записать коллекцию в файл");
        }


        return os.toString();
    }

    /**
     * Преобразует строку XML в объект коллекции фильмов.
     *
     * @param text Строка XML, которая будет десериализована в объект.
     * @return Объект коллекции фильмов, полученный из XML.
     */
    public Movies convertXmlToObject(String text) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Movies.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(text);
            return (Movies) un.unmarshal(reader);
        } catch (JAXBException e) {
            System.out.println("Не удалось загрузить коллекцию из файла");
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Неверный id фильма в коллекции");
            return null;
        }
    }

}