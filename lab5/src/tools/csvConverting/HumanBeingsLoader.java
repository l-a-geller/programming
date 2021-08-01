package tools.csvConverting;

import data.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Vector;

/**
 * Класс-загрузчик коллекции HumanBeing из файла
 */
public class HumanBeingsLoader {

    /**
     * метод, озвращающий коллекцию типа Vector с объектами типа HumanBeing, полученными из файла
     */
    public static Vector<HumanBeing> loadHumanBeings() {
        Vector<HumanBeing> humans = new Vector<HumanBeing>();
        try {
            Reader inputStreamReader = new InputStreamReader(new FileInputStream("humanBeings.csv"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null) {
                humans.add(scanCSVStringForHumanBeing(line));
            }
            bufferedReader.close();
        }catch (NullPointerException e){
            System.out.println("Переменная окружения (название файла, хранящего данные о содержимом коллекции) не указана, укажите значение переменной и запустите программу заново");
            System.exit(1);
        }catch (DateTimeParseException | IllegalArgumentException | ArrayIndexOutOfBoundsException e){
            System.out.println("Ошибка при загрузке коллекции из файла. Неверный формат документа");
            System.out.println("Убедитесь что каждая строка, отображающая элемент коллекции приведена к виду:");
            System.out.println("\"ID\", \"NAME\", \"X\", \"Y\", \"LOCAL_DATE_TIME\", \"REAL_HERO\", \"HAS_TOOTHPICK\", \"IMPACT_SPEED\", \"WEAPON_TYPE\", \"MOOD\", \"CAR_COOLNESS\"");
            System.out.println("Пример: \n1,\"Rick Sanchez\",\"342\",\"343\",\"2020-09-15T17:27:02.192\",\"true\",\"true\",\"71.0\",\"HAMMER\",\"GLOOM\",\"true\"\n");
            System.exit(1);
        }catch (IOException e){
            System.out.println("Ошибка при загрузке коллекции из файла. Программа не может найти файл. Проверьте, существует ли файл и достаточно ли прав на доступ к нему, после чего запустите программу еще раз");
            System.exit(1);
        }
        return humans;
    }

    /**
     * метод, собирающий объект HumanBeing из строки файла csv
     * @param line
     * @return
     * @throws DateTimeParseException
     * @throws IllegalArgumentException
     */
    private static HumanBeing scanCSVStringForHumanBeing(String line) throws DateTimeParseException, IllegalArgumentException, ArrayIndexOutOfBoundsException {
        String[] csvParts = line.split(",");
        long id = Long.parseLong(String.valueOf(Arrays.copyOfRange(csvParts[0].toCharArray(), 1, csvParts[0].length() - 1)));

        String name = String.valueOf(Arrays.copyOfRange(csvParts[1].toCharArray(), 1, csvParts[1].length() - 1));
        if (name.equals("")){ throw new NumberFormatException(); }
        int x = Integer.parseInt(String.valueOf(Arrays.copyOfRange(csvParts[2].toCharArray(), 1, csvParts[2].length() - 1)));
        long y = Long.parseLong(String.valueOf(Arrays.copyOfRange(csvParts[3].toCharArray(), 1, csvParts[3].length() - 1)));
        Coordinates coordinates = new Coordinates(x, y);

        LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(Arrays.copyOfRange(csvParts[4].toCharArray(), 1, csvParts[4].length() - 1)));

        Boolean realHero = Boolean.parseBoolean(String.valueOf(Arrays.copyOfRange(csvParts[5].toCharArray(), 1, csvParts[5].length() - 1)));

        Boolean hasToothpick = Boolean.parseBoolean(String.valueOf(Arrays.copyOfRange(csvParts[6].toCharArray(), 1, csvParts[6].length() - 1)));

        Double impactSpeed = Double.parseDouble(String.valueOf(Arrays.copyOfRange(csvParts[7].toCharArray(), 1, csvParts[7].length() - 1)));

        WeaponType weaponType = null;
        try {
            weaponType = WeaponType.valueOf(String.valueOf(Arrays.copyOfRange(csvParts[8].toCharArray(), 1, csvParts[8].length() - 1)));
        }catch (IllegalArgumentException e){

        }

        Mood mood = Mood.valueOf(String.valueOf(Arrays.copyOfRange(csvParts[9].toCharArray(), 1, csvParts[9].length() - 1)));

        Boolean carCoolness = Boolean.valueOf(String.valueOf(Arrays.copyOfRange(csvParts[10].toCharArray(), 1, csvParts[10].length() - 1)));
        Car car = new Car(carCoolness);

        HumanBeing humanBeing = new HumanBeing(name, coordinates, localDateTime, realHero, hasToothpick, impactSpeed, weaponType, mood, car);
        humanBeing.setId(id);
        return humanBeing;
    }
}
