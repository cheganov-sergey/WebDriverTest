import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import java.util.concurrent.TimeUnit;

public class Run {

    static WebDriver driver;    // задем имя драйвера
    static Actions action;      // экшен

    public static void main(String[] args) {

      System.setProperty(
             "webdriver.chrome.driver",
              "C:\\Software\\chrome\\chromedriver.exe");

        driver = new ChromeDriver();    // инициализируем драйвер

        action= new Actions(driver);    // иницилизируем экшен

        driver.manage().window().maximize();

        driver.manage().
                timeouts().
                implicitlyWait(7, TimeUnit.SECONDS);

       /**
         * Авторизируемся на hh.ru и создаем резюме
        * вводим пароль и логин!
          */

     driver.get("https://ufa.hh.ru");

     System.out.println("Мы на странице " + driver.getTitle());

     WebElement login = driver.findElement(By.xpath("(//a[@data-qa='login'])[2]"));    // входим в меню авторизации
     login.click();

     WebElement userName = driver.findElement(By.xpath("//input[(@name='username')]"));    // вводим логи
     userName.sendKeys("89872504965");

     WebElement password = driver.findElement(By.xpath("//input[@data-qa='login-input-password']"));  // вводим пароль
     password.sendKeys("Finland06");

     WebElement sendSubmit = driver.findElement(By.xpath("//input[@data-qa='account-login-submit']"));  //кликаем войти
     sendSubmit.click();

     WebElement myResume = driver.findElement(By.xpath("//a[@data-qa = 'mainmenu_myResumes']"));    // мои резюме
     myResume.click();

     WebElement createNewResume = driver.findElement(By.xpath("//a[@class = 'bloko-button bloko-button_stretched']"));  // создать резюме
     createNewResume.click();

     // авторизация закнчена --


     /**
     * Заполняем резюме
     */
     // Есть / Нет опыта работы (проверяем в начале что бы потом не мешаласть возможная ошибка)
    WebElement element = driver.findElement(By.xpath("//input[@name='firstName[0].string']"));  //переводим фокус в любое поле и жмем Enter
    element.click();
    waitTime(1);
        System.out.print("Есть / Нет опыта работы (ожидаем ошибку)- "); //проверяем на ошибку
        System.out.println(FindElemrnts("(//div[@class='bloko-form-error bloko-form-error_entered'][contains(.,'Обязательное поле')])[1]"));

    element = driver.findElement(By.xpath("//span[@class='bloko-radio__text'][contains(.,'Нет опыта работы')]")); // переводим радиобатн в положение Нет опыта работы
    element.click();
    waitTime(1);
        System.out.print("Есть / Нет опыта работы - ");
        System.out.println(FindElemrnts("(//div[@class='bloko-form-error bloko-form-error_entered'][contains(.,'Обязательное поле')])[1]")); //проверяем на ошибку

        // Имя:
        System.out.println("    Поле Имя: ");
     TryInputTextExpectError("1Text", "//input[@name='firstName[0].string']", Property.ERRORxPATH_1);
     TryInputTextExpectError("Text-", "//input[@name='firstName[0].string']", Property.ERRORxPATH_1);
     TryInputTextPressEnterExpectError("", "//input[@name='firstName[0].string']", Property.ERRORxPATH_1);
     TryInputText("Text", "//input[@name='firstName[0].string']", Property.ERRORxPATH_1);
     TryInputText("Text Text", "//input[@name='firstName[0].string']", Property.ERRORxPATH_1);
     TryInputText("Text-Text", "//input[@name='firstName[0].string']", Property.ERRORxPATH_1);

        // Фамилия:
        System.out.println("    Поле Фамилия: ");
     TryInputTextExpectError("1Text", "//input[@name='lastName[0].string']", Property.ERRORxPATH_1);
     TryInputTextExpectError("Text-", "//input[@name='lastName[0].string']", Property.ERRORxPATH_1);
     TryInputTextPressEnterExpectError("", "//input[@name='lastName[0].string']", Property.ERRORxPATH_1);
     TryInputText("Text", "//input[@name='lastName[0].string']", Property.ERRORxPATH_1);
     TryInputText("Text Text", "//input[@name='lastName[0].string']", Property.ERRORxPATH_1);
     TryInputText("Text-Text", "//input[@name='lastName[0].string']", Property.ERRORxPATH_1);

     // Город
        System.out.println("    Поле Город: ");
        TryInputText("Text", "//input[@data-qa='suggestCity resume-city']", Property.ERRORxPATH_1);
        TryInputText("Text12", "//input[@data-qa='suggestCity resume-city']", Property.ERRORxPATH_1);   // тест проваливается т.к. не корректный кейс
        TryInputTextPressEnterExpectError("", "//input[@data-qa='suggestCity resume-city']", Property.ERRORxPATH_1);
        // как выбирать из списка не понял

        // Тестируем дату
        System.out.println("Тестируем Дату рождения: ");
        TryInputDate(21,2,1990);
     TryInputDate(1, 12, 1980);
     TryInputDate(44,0,1888);
     TryInputDate(11,13,1944);

//        driver.quit();

    }

    public static void waitTime(Integer sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод - Возвращает true если веб элемент присутствует на странице
     * @param xPath - веб элемент указанный через уникальный xPath
     * @return true если веб элемент присутствует на странице, false - если нет.
     */
    public static boolean FindElemrnts(String xPath) {

         if (driver.findElements(By.xpath(xPath)).size() > 0)
            return true;
         else
             return false;
    }

    /**
     * Метод проверяет выводит ли сайт сообщение об ошибке при вводе определенного текста и нажатия TAB
     * ожидаем повление ошибки (есть шибка тест - pass)
     * @param text - вводимый текст
     * @param xPath - xPath куда вводим текст
     * @param errorXPath - xPath появившейся ошибки
     */
    public static void TryInputTextExpectError (String text, String xPath, String errorXPath) {

        WebElement element = driver.findElement(By.xpath(xPath));
        element.clear();
        element.sendKeys(text);
        element.sendKeys(Keys.TAB);
        waitTime(3);

        if (FindElemrnts(errorXPath))
            System.out.println(text + "  - test PASS");
        else System.out.println(text + " - test FAIL");

       }

    /**
     * Метод проверяет выводит ли сайт сообщение об ошибке при вводе определенного текста
     * НЕ ожидаем ошибки (есть нет шибки тест - pass)
     * @param text - вводимый текст
     * @param xPath - xPath куда вводим текст
     * @param errorXPath - xPath появившейся ошибки
     */
    public static void TryInputText (String text, String xPath, String errorXPath) {

        WebElement element = driver.findElement(By.xpath(xPath));
        element.clear();
        element.sendKeys(text);
        element.sendKeys(Keys.TAB);
        waitTime(3);

        if (!FindElemrnts(errorXPath))
            System.out.println(text + " - test PASS");
        else System.out.println(text + " - test FAIL");

       }

/**
 * Метод проверяет выводит ли сайт сообщение об ошибке при вводе определенного текста и нажатия Enter
 * ожидаем повление ошибки (есть шибка тест - pass)
 * @param text - вводимый текст
 * @param xPath - xPath куда вводим текст
 * @param errorXPath - xPath появившейся ошибки]
 */
    public static void TryInputTextPressEnterExpectError (String text, String xPath, String errorXPath) {

        WebElement element = driver.findElement(By.xpath(xPath));
        element.clear();
        element.sendKeys(text);
        element.sendKeys(Keys.ENTER);
        waitTime(3);

        if (FindElemrnts(errorXPath))
            System.out.println(text + "  - test PASS");
        else System.out.println(text + " - test FAIL");

        }

    /**
     * Метод - проверяет на корректность ввод даты рождения. (Есть проблемма: почему то не очищается текстовое поле)
     * @param dd    - день рождения int
     * @param mm    - месяй int
     * @param yyyy  - год   int
     */
        public static void TryInputDate (int dd, int mm, int yyyy) {

            WebElement element = driver.findElement(By.xpath("//input[@data-qa='resume__birthday__day']"));
            element.clear();
            element.sendKeys("" + dd);
            element.sendKeys(Keys.TAB);

            if ((mm >= 0) & (mm <=12)) {
                Select select = new Select(driver.findElement(By.xpath("//select[@data-qa='resume__birthday__month-select']")));
                select.selectByIndex(mm);
            }

            element = driver.findElement(By.xpath("//input[@data-qa='resume__birthday__year']"));
            element.sendKeys("");
            element.sendKeys("" + yyyy);
            element.sendKeys(Keys.TAB);

            waitTime(2);

            if (!FindElemrnts(Property.ERROR_DATE))
                System.out.println("Дата: " + dd + "." + mm + "." + yyyy + " - PASS");
            else  System.out.println("Дата: " + dd + "." + mm + "." + yyyy + " - FAIL");



        }

}
