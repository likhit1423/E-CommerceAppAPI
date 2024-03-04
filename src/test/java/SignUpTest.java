import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import static org.hamcrest.Matchers.*;

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
    public void test2_login()
    {
        RestAssured.baseURI="https://www.apicademy.dev";
        usersCreds.put(email,password);
        String signUpResponse=given().baseUri("https://www.apicademy.dev").log().all().header("Content-Type","application/json").
                body(payload.signUpPayload(email,password)).
                when().post("api/auth/login").then().log().all().extract().response().asString();
    }

    @Test
    public void test3_signUpAgainSameUser()
    {
        RestAssured.baseURI="https://www.apicademy.dev";
        Iterator<Map.Entry<String,String>> itr=usersCreds.entrySet().iterator();
        if(itr.hasNext())
        {
            Map.Entry<String,String> entry=itr.next();
            String username=entry.getKey();
            String password=entry.getValue();
            given().baseUri("https://www.apicademy.dev").log().all().header("Content-Type","application/json").
                    body(payload.signUpPayload(username,password)).
                    when().post("api/auth/signup").then().log().all().statusCode(401).body("error",equalTo("User already registered"));
        }
    }
    @Test
    public void test4_invalidUser()
    {
         email=library.generateRandomEmail();
         password=randomPasswordGenerator.generateRandomPassword(12);
        RestAssured.baseURI="https://www.apicademy.dev";
        given().baseUri("https://www.apicademy.dev").log().all().header("Content-Type","application/json").
                body(payload.signUpPayload(email,password)).
                when().post("api/auth/login").then().log().all().statusCode(401).body("error",equalTo("Invalid login credentials"));
    }

}
