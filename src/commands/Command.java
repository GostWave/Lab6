package commands;

/**
 * Интерфейс Command, представляющий команду, которую можно выполнить.
 */
public interface Command {

    /**
     * Выполняет команду.
     *
     * @param argument аргумент команды
     */
    void execute(String argument);

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды
     */
    String getDescription();
}
