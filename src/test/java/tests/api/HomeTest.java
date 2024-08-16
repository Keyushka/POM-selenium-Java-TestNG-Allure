package tests.api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HomeTest {

    @BeforeTest
    public static void setBaseUrl(){
        RestAssured.baseURI = "https://api.spacexdata.com/v3";
    }

    @Test
    public void some() {

    }
}
