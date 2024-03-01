import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
public class SignUpTest {

    HashMap<String,String > usersCreds=new HashMap<>();
    Payload payload=new Payload();
    FunctionalLibrary library=new FunctionalLibrary();
    RandomPasswordGenerator randomPasswordGenerator=new RandomPasswordGenerator();
    String email=library.generateRandomEmail();
    String password=randomPasswordGenerator.generateRandomPassword(12);

    @Test
    public void test1_signIn()
    {
        RestAssured.baseURI="https://www.apicademy.dev";
        usersCreds.put(email,password);
        String signUpResponse=given().baseUri("https://www.apicademy.dev").log().all().header("Content-Type","application/json").
                body(payload.signUpPayload(email,password)).
                when().post("api/auth/signup").then().log().all().extract().response().asString();
    }

    @Test
    public void test2_userAlreadyRegistered()
    {

    }

    @Test
    public void test3_login()
    {
        RestAssured.baseURI="https://www.apicademy.dev";
        usersCreds.put(email,password);
        String signUpResponse=given().baseUri("https://www.apicademy.dev").log().all().header("Content-Type","application/json").
                body(payload.signUpPayload(email,password)).
                when().post("api/auth/login").then().log().all().extract().response().asString();
    }


}
