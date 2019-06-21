package twitter.statuses;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import twitter.common.RestUtilities;
import twitter.constants.EndPoints;
import twitter.constants.Path;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasItem;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.typeCompatibleWith;

public class UserTimelineTest {

    RequestSpecification reqSpec;
    ResponseSpecification resSpec;


    @BeforeClass
    public void setUp() {
        reqSpec = RestUtilities.getRequestSpecification();
        reqSpec.queryParam("user_id", "apiautomation");
        reqSpec.basePath(Path.STATUSES);

        resSpec = RestUtilities.getResponseSpecification();
    }

    @Test
    public void readTweets1() {

        given()
               // .spec(reqSpec)
                .spec(RestUtilities.createQueryParam(reqSpec,"count", "2"))
        .when()
                .get(EndPoints.STATUSES_USER_TIMELINE)
        .then()
                .spec(resSpec)
              //  .log().all()
                .body("user.screen_name", hasItem("apiautomation"));


    }

    @Test
    public void readTweets2() {
        RestUtilities.setEndPoint(EndPoints.STATUSES_USER_TIMELINE);
        Response res = RestUtilities.getResponse(
                RestUtilities.createQueryParam(reqSpec,"count", "2"), "get");
        ArrayList<String> screenNameList = res.path("user.screen_name");
        System.out.println(screenNameList);
        Assert.assertTrue(screenNameList.contains("apiautomation"));
    }
}
