import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Run {

    static WebDriver driver;    // задем имя драйвера

    public static void main(String[] args) {

        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Software\\chrome\\chromedriver.exe");

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().
                timeouts().
                implicitlyWait(8, TimeUnit.SECONDS);

       /**
         * Авторизируемся на hh.ru и создаем резюме
          */

     driver.get("https://ufa.hh.ru");

     System.out.println("Мы на странице " + driver.getTitle());

     WebElement login = driver.findElement(By.xpath("(//a[@data-qa='login'])[2]"));    // входим в меню авторизации
     login.click();

     WebElement userName = driver.findElement(By.xpath("//input[(@name='username')]"));    // вводим логи
     userName.sendKeys("89872504965");

     WebElement password = driver.findElement(By.xpath("//input[@data-qa='login-input-password']"));  // вводим пароль
     password.sendKeys("Finland06");

     WebElement sendSubmit = driver.findElement(By.xpath("//input[@data-qa='account-login-submit']"));
     sendSubmit.click();

     WebElement myResume = driver.findElement(By.xpath("//a[@data-qa = 'mainmenu_myResumes']"));
     myResume.click();

     WebElement createNewResume = driver.findElement(By.xpath("//a[@class = 'bloko-button bloko-button_stretched']"));
     createNewResume.click();

     // авторизация закнчена --

     /**
     * Заполняем резюме
     */
        // Имя:
        System.out.println("    Поле Имя: ");
     TryInputTextExpectError("1Text", "//input[@name='firstName[0].string']", Property.ERRORxPATH_1);
     TryInputTextExpectError("Text-", "//input[@name='firstName[0].string']", Property.ERRORxPATH_1);
     TryInputTextExpectError("", "//input[@name='firstName[0].string']", Property.ERRORxPATH_1);
     TryInputText("Text", "//input[@name='firstName[0].string']", Property.ERRORxPATH_1);
     TryInputText("Text Text", "//input[@name='firstName[0].string']", Property.ERRORxPATH_1);
     TryInputText("Text-Text", "//input[@name='firstName[0].string']", Property.ERRORxPATH_1);

        // Фамилия:
        System.out.println("    Поле Фамилия: ");
        TryInputTextExpectError("1Text", "//input[@name='lastName[0].string']", Property.ERRORxPATH_1);
     TryInputTextExpectError("Text-", "//input[@name='lastName[0].string']", Property.ERRORxPATH_1);
     TryInputTextExpectError("", "//input[@name='lastName[0].string']", Property.ERRORxPATH_1);
     TryInputText("Text", "//input[@name='lastName[0].string']", Property.ERRORxPATH_1);
     TryInputText("Text Text", "//input[@name='lastName[0].string']", Property.ERRORxPATH_1);
     TryInputText("Text-Text", "//input[@name='lastName[0].string']", Property.ERRORxPATH_1);


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
     * Метод проверяет выводит ли сайт сообщение об ошибке при вводе определенного текста
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
        waitTime(1);

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
//        element.findElements(By.xpath(Property.EMPTY_CLICK));
//        element.click();
        waitTime(1);

        if (!FindElemrnts(errorXPath))
            System.out.println(text + " - test PASS");
        else System.out.println(text + " - test FAIL");
    }
}
