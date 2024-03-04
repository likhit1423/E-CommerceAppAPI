import io.restassured.RestAssured;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class HealthCheckTest {

    @Test
    public void healthCheckTest()
    {
        RestAssured.baseURI="https://www.apicademy.dev";
        given().log().all().when().get("health-check").then().log().all().statusCode(200).body("message",equalTo("ok"));
    }
}
