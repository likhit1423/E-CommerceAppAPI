import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
public class SignUpTest extends BaseTest{

    public static HashMap<String,String > usersCreds=new LinkedHashMap<>();
    Payload payload=new Payload();
    FunctionalLibrary library=new FunctionalLibrary();
    RandomPasswordGenerator randomPasswordGenerator=new RandomPasswordGenerator();
    String email=library.generateRandomEmail();
    String password=randomPasswordGenerator.generateRandomPassword(12);
    ConfigReader configReader=ConfigReader.getInstance();
    public static String token=null;

    @Test
    public void test1_signIn()
    {

          usersCreds.put(email,password);
//        String signUpResponse=given().baseUri("https://www.apicademy.dev").log().all().header("Content-Type","application/json").
//                body(payload.signUpPayload(email,password)).
//                when().post("api/auth/signup").then().log().all().extract().response().asString();
                given().log().all().header("Content-Type","application/json").
                body(payload.signUpPayload(email,password)).
                when().post(configReader.getUrl("signup")).then().log().all().statusCode(201).
                extract().response();



    }

    @Test
    public void test2_login()
    {
        usersCreds.put(email,password);
        Iterator<Map.Entry<String,String >> it=usersCreds.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<String ,String> entry=it.next();
            String email=entry.getKey();
            String password=entry.getValue();
            String signUpResponse=given().log().all().header("Content-Type","application/json").
                    body(payload.signUpPayload(email,password)).
                    when().post(configReader.getUrl("login")).then().log().all().extract().response().asString();
            break;
        }

    }

    @Test
    public void test3_signUpAgainSameUser()
    {
        Iterator<Map.Entry<String,String>> itr=usersCreds.entrySet().iterator();
        if(itr.hasNext())
        {
            Map.Entry<String,String> entry=itr.next();
            String username=entry.getKey();
            String password=entry.getValue();
            given().log().all().header("Content-Type","application/json").
                    body(payload.signUpPayload(username,password)).
                    when().post(configReader.getUrl("signup")).then().log().all().statusCode(401).body("error",equalTo("User already registered"));
        }
    }
    @Test
    public void test4_invalidUser()
    {
         email=library.generateRandomEmail();
         password=randomPasswordGenerator.generateRandomPassword(12);
         given().log().all().header("Content-Type","application/json").
                 body(payload.signUpPayload(email,password)).
                 when().post(configReader.getUrl("login")).then().log().all().statusCode(401).
                 body("error",equalTo("Invalid login credentials"));
    }

    public String getToken()
    {
        usersCreds.put(email,password);
        Iterator<Map.Entry<String,String >> it=usersCreds.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<String ,String> entry=it.next();
            String email=entry.getKey();
            String password=entry.getValue();
            Response signUpResponse=given().log().all().header("Content-Type","application/json").
                    body(payload.signUpPayload(email,password)).
                    when().post(configReader.getUrl("login")).then().log().all().extract().response();
            JsonPath jsonPath =RestAssuredUtils.getJsonPath(signUpResponse);
            token=jsonPath.getString("data.session.access_token");
            System.out.println("token is "+token);

            break;
        }
        return token;
    }
}
