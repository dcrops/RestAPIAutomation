package twitter.statuses;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import twitter.common.RestUtilities;
import twitter.constants.EndPoints;
import twitter.constants.Path;

import static io.restassured.RestAssured.given;


public class E2EWorkflowTest {

    RequestSpecification reqSpec;
    ResponseSpecification resSpec;
    String tweetID;

    @BeforeClass
    public void setUp() {
        reqSpec = RestUtilities.getRequestSpecification();
       // reqSpec.queryParam("user_id", "apiautomation");
        reqSpec.basePath(Path.STATUSES);

        resSpec = RestUtilities.getResponseSpecification();
    }

    @Test
    public void createTweet() {
        Response res =
        given()
                /*.spec(reqSpec)
                .queryParam("status", "E2E Test4")*/
                .spec(RestUtilities.createQueryParam(reqSpec,"status", "E2E Test7" ))
        .when()
                .post(EndPoints.STATUSES_TWEET_POST)
        .then()
                .spec(resSpec)
                .extract().response();

        tweetID = res.path("id_str");
        System.out.println(tweetID);
    }

    @Test(dependsOnMethods = {"createTweet"})
    public void readTweet() {
        Response res =
            given()
                .spec(RestUtilities.createQueryParam(reqSpec,"id", tweetID))
            .when()
                .get(EndPoints.STATUSES_TWEET_READ_SINGLE)
            .then()
                .spec(resSpec)
                .extract().response();
        String text = res.path("text");
        System.out.println("The tweet text is: " + text);
    }

    @Test(dependsOnMethods = {"readTweet"})
    public void deleteTweet() {

        RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_DESTROY);
        RestUtilities.getResponse(
                RestUtilities.createQueryParam(reqSpec,"id", tweetID), "post");
    }

}
