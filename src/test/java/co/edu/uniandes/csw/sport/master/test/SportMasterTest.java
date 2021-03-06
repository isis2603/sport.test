/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.csw.sport.master.test;

import co.edu.uniandes.csw.address.logic.dto.AddressDTO;
import co.edu.uniandes.csw.sport.logic.dto.SportDTO;
import co.edu.uniandes.csw.sport.master.utils.test.InitializeDataUserMaster;
import co.edu.uniandes.csw.user.logic.dto.UserDTO;
import co.edu.uniandes.csw.user.master.persistence.api.IUserMasterPersistence;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author Jj.alarcon10
 */


public class SportMasterTest {
        // Es la instancia inicial del web driver que controlará el navegador firefox
        private static WebDriver driver;
        // url en el cual se aloja la página web (en este caso localhost:8080)
        private static String baseUrl;
        private static UserDTO dataUser;
        private static AddressDTO dataAddress;
        private static SportDTO dataSport;
        // variable que indica si varios alert consecutivos (alert javascript) se tomarán
        private static boolean acceptNextAlert = true;
        private static StringBuffer verificationErrors = new StringBuffer();
       /*La anotación ‘@BeforeClass’ indica lo que se debe ejecutar ANTES de correr el archivo de pruebas. Este método instancia un nuevo driver firefox (causando la apertura de una ventana física de firefox).*/
     
        @BeforeClass
        public static void setUp() throws Exception {
            driver = new FirefoxDriver();
        // se define el url base del proyecto web
            baseUrl = "http://localhost:8080";
        //* Indica cuanto se espera para la respuesta de cualquier comando realizado hacia el navegador*/.
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
              /** Cambia el tamanio de la ventana del explorador para que los controles backbone se muestren correctamente en la prueba*/
            driver.manage().window().setSize(new Dimension(1400, 700));
            
        }
  
        
        @Before
        public void setUpUrl(){
            driver.get(baseUrl + "/sport.web/");
            //carga en la base de datos 4 objetos de tipo sport
            InitializeDataUserMaster.insertUserAddressSport(4);
        
        }
        /**
     *
     */
        @After
        public void deleteBdatos(){
             InitializeDataUserMaster.deleteBd();  
        }
        
        
        
    @Test
    public void testCreateUserMaster() throws Exception{
        
            /**
         * Comando que realiza click sobre el boton "create" del toolbar del maestro. La
         * función 'find' encuentra el control y posteriormente hace clic en el
         * mismo. La forma en la que se busca el control es utilizando
         * expresiones xPath ya que los id de los mismos nunca son iguales (se
         * generan con junto con el valor de componentId que varía).
         */
          driver.findElement(By.xpath("//button[@class='btn btn-default dropdown-toggle']")).click();
          Thread.sleep(1000);
          String User ="User Master";
          driver.findElement(By.xpath("//a[contains(text(),'"+User+"')]")).click();
          Thread.sleep(3000);
          //Es necesario cambiar de iframe :)
          driver.switchTo().frame(driver.findElement(By.id("container")));
          driver.findElement(By.xpath("//button[contains(@id,'createButton')]")).click();
          Thread.sleep(2000);
          dataUser= InitializeDataUserMaster.generateUser();
          driver.findElement(By.id("userName")).clear();
          driver.findElement(By.id("userName")).sendKeys(dataUser.getUserName());
          driver.findElement(By.id("firstName")).clear();
          driver.findElement(By.id("firstName")).sendKeys(dataUser.getFirstName());
          driver.findElement(By.id("lastName")).clear();
          driver.findElement(By.id("lastName")).sendKeys(dataUser.getLastName());
          driver.findElement(By.id("docNumber")).clear();
          driver.findElement(By.id("docNumber")).sendKeys(dataUser.getDocNumber());
          String Address="Address";
          driver.findElement(By.xpath("//a[contains(text(),'"+Address+"')]")).click();
          Thread.sleep(2500);
         //*[@id="93-createButton"] /html/body/div[1]/div[2]/div/div[1]/div[1]/nav/div[2]/form/button
          driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/div[1]/nav/div[2]/form/button")).click();         
          dataAddress=InitializeDataUserMaster.generateAddress();
          Thread.sleep(2000);
          driver.findElement(By.id("street")).clear();
          driver.findElement(By.id("street")).sendKeys(dataAddress.getStreet());
          driver.findElement(By.id("aveneu")).clear();
          driver.findElement(By.id("aveneu")).sendKeys(dataAddress.getAveneu());
          driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/div[1]/nav/div[2]/form/button[2]")).click();
          Thread.sleep(2000);
          driver.findElement(By.xpath("//button[contains(@id,'saveButton')]")).click();
          Thread.sleep(2000);
          List<WebElement> table = driver.findElements(By.xpath("//table[contains(@class,'table striped')]/tbody/tr"));
          boolean success = false;
          for (WebElement webElement : table) {
            List<WebElement> elems = webElement.findElements(By.xpath("td"));
            if (elems.get(0).getText().equals(dataUser.getUserName()) && elems.get(1).getText().equals(dataUser.getFirstName())) {
                success = true;
            }
          }
          
          assertTrue(success); 
    }
        
    @Test 
     public void testEditAddress() throws Exception{
         
         driver.get(baseUrl + "/sport.web/userMaster.html");
         Thread.sleep(2000);
         List<WebElement> table = driver.findElements(By.xpath("//table[contains(@class,'table striped')]/tbody/tr"));
         if (table.size()>0){
             //Trae el ultimo elemento fila y hace click
             driver.findElements(By.linkText("Edit")).get(driver.findElements(By.linkText("Edit")).size()-1).click();
             Thread.sleep(2000);
             String Address="Address";
             driver.findElement(By.xpath("//a[contains(text(),'"+Address+"')]")).click();
             Thread.sleep(2500);
             //El mock no esta guardando el detalle address
             driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/div[3]/div/table/tbody/tr/td[4]/a[1]")).click();//create       
                                          
             dataAddress=InitializeDataUserMaster.generateAddress();
             Thread.sleep(2000);
             driver.findElement(By.id("street")).clear();
             driver.findElement(By.id("street")).sendKeys(dataAddress.getStreet());
             driver.findElement(By.id("aveneu")).clear();
             driver.findElement(By.id("aveneu")).sendKeys(dataAddress.getAveneu());
             driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[1]/div[1]/nav/div[2]/form/button[2]")).click();//save
             Thread.sleep(2000);
             List<WebElement> tables = driver.findElements(By.xpath("/html/body/div[1]/div[2]/div/div[1]/div[3]/div/table/tbody/tr"));
                                                                     
             boolean success = false;
             for (WebElement webElement : tables) {
              List<WebElement> elems = webElement.findElements(By.xpath("td"));
              if (elems.get(0).getText().equals(dataAddress.getStreet()) && elems.get(1).getText().equals(dataAddress.getAveneu())) {
                  success = true;
              }
             }
             
             driver.findElement(By.xpath("//button[contains(@id,'saveButton')]")).click();
             Thread.sleep(2000);
             
          
             assertTrue(success);     
         
         }
    }

     
     /**
         * CreateUserSport-> Crea un nuevo User y le asocia varios elementos sport
         * 
         */
    @Test
    public void createUserSport() throws Exception {
        
        
        Thread.sleep(2000);
        
        driver.get(baseUrl + "/sport.web/userMaster.html");
        Thread.sleep(5000);
        driver.findElement(By.xpath("//button[contains(@id,'createButton')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("userName")).clear();
        driver.findElement(By.id("userName")).sendKeys("nombre");
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("nombre1");
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("nombre2");
        //HREF indicando el tab de detalle a seleccionar
        driver.findElement(By.xpath("//a[contains(@href,'sport')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[contains(@id,'addButton')]")).click();
        //Toma todos los webElement cuyo id sea "selection" y de tipo checkbox.
        List<WebElement> lst = driver.findElements(By.xpath("//input[contains(@id,'selection')][@type='checkbox']"));
            for (WebElement lst1 : lst) {
                if (!lst1.isSelected()) {
                    lst1.click(); // Si no esta seleccionado hace click en el checkbox, agrega todos los deportes existentes
                }
            }
        driver.findElement(By.id("addButton")).click();
        List<WebElement> tables = driver.findElement(By.xpath("//div[contains(@id,'sport')]")).findElements(By.xpath("//table[contains(@class,'table striped')]/tbody/tr"));
        if (tables.size() != lst.size()) {
            fail();
        }
        driver.findElement(By.xpath("//button[contains(@id,'saveButton')]")).click();
        Thread.sleep(3000);
        List<WebElement> table = driver.findElements(By.xpath("//table[contains(@class,'table striped')]/tbody/tr"));
        boolean fail = false;
        for (WebElement webElement : table) {
            List<WebElement> elems = webElement.findElements(By.xpath("td"));
            if (elems.get(0).getText().equals("nombre") && elems.get(1).getText().equals("nombre1")) {
                fail = true;
            }
        }
        assertTrue(fail);
    }

    /**
         * editUserSport-> Edita un User existente que tiene varios sports asociados. 1. Elimina todos los elementos sports asociados 2. Agrega varios sports 3. Verfica que el objeto haya sido actualizado
         * 
         */
    @Test
    public void editUserSport() throws Exception {
        
        driver.get(baseUrl + "/sport.web/userMaster.html");
        driver.findElements(By.linkText("Edit")).get(driver.findElements(By.linkText("Edit")).size() - 1).click();
        Thread.sleep(2000);
        driver.findElement(By.id("userName")).clear();
        driver.findElement(By.id("userName")).sendKeys("nombre1mod");
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("nombre2mod");
        driver.findElement(By.xpath("//a[contains(@href,'sport')]")).click();
        Thread.sleep(2000);
        List<WebElement> tables = driver.findElements(By.xpath("/html/body/div[1]/div[2]/div/div[2]/div[3]/div/table/tbody/tr"));
        SportDTO sport;
        Thread.sleep(2000);
            for (WebElement table : tables) {
                driver.findElements(By.linkText("Delete")).get(driver.findElements(By.linkText("Delete")).size()-1).click();
                Thread.sleep(2000);
            }
            
        driver.findElement(By.xpath("//button[contains(@id,'addButton')]")).click();
        List<WebElement> lst = driver.findElements(By.xpath("//input[contains(@id,'selection')][@type='checkbox']"));
             for (WebElement lst1 : lst) {
                  if (!lst1.isSelected()) {
                         lst1.click();
                          }
                      } 
        driver.findElement(By.id("addButton")).click();  
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[contains(@id,'saveButton')]")).click();
        Thread.sleep(3000);
        List<WebElement> table = driver.findElements(By.xpath("//table[contains(@class,'table striped')]/tbody/tr"));
        boolean fail = false;
        for (WebElement webElement : table) {
            List<WebElement> elems = webElement.findElements(By.xpath("td"));
            if (elems.get(0).getText().equals("nombre1mod") && elems.get(1).getText().equals("nombre2mod")) {
                fail = true;
            }
        }
        assertTrue(fail);
    }
    
    /**
         * deleteUserSport->Hace clic en el vinculo "Delete" del primer elemento de la lista
         * de sports
         */
    @Test
    public void deleteUserSport() throws Exception {
        
        driver.get(baseUrl + "/sport.web/userMaster.html");
        driver.findElement(By.linkText("Delete")).click();
        Thread.sleep(2000);
        /**
         * Se verifica que en la lista el elemento halla desaparecido. Si
         * existe, hubo un error.
         */
        try {
            List<WebElement> table = driver.findElements(By.xpath("//table[contains(@class,'table striped')]/tbody/tr"));
            boolean fail = false;
            for (WebElement webElement : table) {
                List<WebElement> elems = webElement.findElements(By.xpath("td"));
                if (elems.get(0).getText().equals("nombre")) {
                    fail = true;
                }
            }
            WebElement dialog = driver.findElement(By.xpath("//div[contains(@style,'display: block;')]"));
            assertTrue(dialog != null && !fail);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
    
    /**
         *  La anotación ‘@AfterClass’ indica lo que se debe ejecutar DESPUÉS de ejecutar
            el archivo de pruebas. Este método cierra la ventana de firefox 
            abierta por @BeforeClass que se utilizó para la prueba. 
         */
    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
      
    }
}
