/**
 * константы с xPath
 */
public class Property {

    public static final String ERRORxPATH_1 = "//div[@class='bloko-form-error bloko-form-error_entered']";  // сообщение ошибки ввода при вводе неправильного ФИО

    public static final String EMPTY_CLICK = "//div[@class='bloko-form-legend'][contains(.,'Фамилия')]"; // подразумевает пустую область для имитации клика

    public static final String ERROR_DATE = "//div[@class='bloko-form-error bloko-form-error_entered'][contains(.,'Некорректная дата')]"; // не верный формат даты
}
