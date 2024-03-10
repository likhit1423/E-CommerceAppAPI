import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    public Response res = null; //Response
    public JsonPath jp  = null; //JsonPath

    //Instantiate a Helper Test Methods (testUtils) Object



    @BeforeClass
    public void setup() {
        //Test Setup

        RestAssuredUtils.setBaseURI(); //Setup Base URI

    }

    @AfterClass
    public void afterTest() {
        //Reset Values
        RestAssuredUtils.resetBaseURI();
        RestAssuredUtils.resetBasePath();
    }
}
