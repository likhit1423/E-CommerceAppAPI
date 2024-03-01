public class Payload {

    public String signUpPayload(String email,String password)
    {
        return "{\n" +
                "    \"email\": \""+email+"\",\n" +
                "    \"password\": \""+password+"\"\n" +
                "}";
    }


}
