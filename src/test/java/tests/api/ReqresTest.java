package tests.api;

import api.*;
import io.restassured.http.ContentType;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    private final static String URL = "https://reqres.in";

    @Test
    @Description("Make sure that the names of the user avatar files match and that the user emails end in 'regress.in'")
    public void checkAvatarAndIdTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<UserData> users = given()
                .when()
                .get("/api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);
        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("reqres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
       // List<String> avatars = users.stream().map(UserData::getAvatar).toList(); // from Java 16 +
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());
        for(int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    public void successRegisterTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");
        SuccessRegister successReg = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(SuccessRegister.class);
        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());

        Assert.assertEquals(id, successReg.getId());
        Assert.assertEquals(token, successReg.getToken());
    }

    @Test
    public void failedRegisterTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecError400());
        Register user = new Register("sydney@fife", "");
        UnSuccessRegister unSuccessReg = given()
                .body(user)
                .when()
                .post("/api/register")
                .then().log().all()
                .extract().as(UnSuccessRegister.class);
        Assert.assertEquals("Missing password", unSuccessReg.getError());
    }

    @Test
    @Description("Ensure that the LIST<RESOURCE> operation returns data sorted by year.")
    public void sortedYearsTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<ColorsData> colors = given()
                .when()
                .get("/api/unknown")
                .then().log().all()
                .extract().body().jsonPath().getList("data", ColorsData.class);
        List<Integer> years = colors.stream().map(ColorsData::getYear).toList();
        List<Integer> sortedYears = years.stream().sorted().toList();
        //System.out.println(years);
        //System.out.println(sortedYears);
        Assert.assertEquals(sortedYears, years); //expected, actual
    }

    @Test
    public void deleteUserTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(204));
        given()
                . when()
                .delete("/api/users/2")
                .then().log().all();
    }

    @Test
    public void timeTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        UserTime user = new UserTime("morpheus", "zion resident");
        UserTimeResponse response = given()
                .body(user)
                .when()
                .put("/api/users/2")
                .then().log().all()
                .extract().as(UserTimeResponse.class);
        //String regex = "(.{6})$"; // the last 6 symbols
        String regex = "\\..*";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");
        String responseTime = response.getUpdatedAt().replaceAll(regex, "");
       // System.out.println(currentTime);
       // System.out.println(responseTime);
        Assert.assertEquals(currentTime, responseTime);
    }
}
