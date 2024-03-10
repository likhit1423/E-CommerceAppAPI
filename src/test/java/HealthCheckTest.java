import io.restassured.RestAssured;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class HealthCheckTest extends BaseTest {

    ConfigReader configReader=ConfigReader.getInstance();

    @Test
    public void healthCheckTest()
    {

        given().log().all().when().get(configReader.getUrl("health-check")).then().log().all().statusCode(200).body("message",equalTo("ok"));
    }
}
