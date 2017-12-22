package Integration;

import ca.ulaval.glo4002.crm.CrmServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class IntegrationUAT {

    private static CountDownLatch crmReady = new CountDownLatch(1);
    private static Thread crmThread;
    private final String SERVERPORT = "http://localhost:8080/";

    @BeforeClass
    public static void startServer() throws InterruptedException {
        crmThread = new Thread(new CrmServer(new String[]{}, new ContextRefreshedListener()));
        crmThread.start();
        crmReady.await();
    }

    @AfterClass
    public static void stopServer() {
        if (crmThread != null && crmThread.isAlive()) {
            crmThread.interrupt();
        }
    }

    @Test
    public void givenServerPath_WhenPing_ThenReturnStatus200() {
        given().when().get(SERVERPORT).then().statusCode(200);
    }

    @Test
    public void givenClientPath_whenPingClients_ThenReturnStatus200() {
        String typePath = "clients";
        String linksPath = "_links." + typePath + ".href";
        given().when().get(SERVERPORT).then().statusCode(200).body(linksPath, equalTo(SERVERPORT + typePath));
    }

    @Test
    public void givenUsersPath_whenPingUsers_ThenReturnStatus200() {
        String typePath = "users";
        String linksPath = "_links." + typePath + ".href";
        given().when().get(SERVERPORT).then().statusCode(200).body(linksPath, equalTo(SERVERPORT + typePath));
    }

    @Test
    public void givenProductsPath_whenPingProducts_ThenReturnStatus200() {
        String typePath = "products";
        String linksPath = "_links." + typePath + ".href";
        given().when().get(SERVERPORT).then().statusCode(200).body(linksPath, equalTo(SERVERPORT + typePath));
    }

    @Test
    public void givenProfilePath_whenPingProfile_ThenReturnStatus200() {
        String typePath = "profile";
        String linksPath = "_links." + typePath + ".href";
        given().when().get(SERVERPORT).then().statusCode(200).body(linksPath, equalTo(SERVERPORT + typePath));
    }

    @Component
    public static class ContextRefreshedListener implements ApplicationListener<ApplicationReadyEvent> {

        @Override
        public void onApplicationEvent(ApplicationReadyEvent arg0) {
            crmReady.countDown();
        }
    }
}