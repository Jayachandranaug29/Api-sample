package APITests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class OrdersTest {

    private static String authToken;

    @Test
    public void testGetOrders() throws ParseException {

        signUp("test@tw.com",
                "!abcd1234");
        login("test@tw.com",
                "!abcd1234");


    }


    String makePostCallAndReturnResponseWithToken(String URI, JSONObject requestBody) {

        RestAssured.baseURI = URI;
        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+authToken)
                .body(requestBody)
                .post();

        System.out.println(response.getBody().asString());
        return response.getBody().asString();
    }

    protected void signUp(String username, String password) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", username);
        requestBody.put("password", password);

        makePostCallAndReturnResponseWithToken("http://localhost:3000/user/signup", requestBody);
    }

    protected void login(String username, String password) throws ParseException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", username);
        requestBody.put("password", password);

        String fetchTokenFromResponse = makePostCallAndReturnResponseWithToken("http://localhost:3000/user/login", requestBody);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(fetchTokenFromResponse);
        authToken = jsonObject.get("token").toString();
        System.out.println(authToken);

    }

}
