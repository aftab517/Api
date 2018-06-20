package SecondsessionApi;

import com.google.gson.JsonObject;
import com.sun.deploy.ref.Helpers;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

//Aftab
public class MockApi {

    @Before
    public void startUp() {
        RestAssured.baseURI = "http://ec2-52-14-141-208.us-east-2.compute.amazonaws.com:9089";
    }

    @Test

    public void blogTest(){
       String token= getAccessToken("admin","admin");
        System.out.println(token);
    }


    public String getAccessToken(String username, String password){
        return given()
                .auth().basic("my-trusted-client", "secret")
                .when()
                .post("/oauth/token?grant_type=password&username=" +username+ "&password=" +password)
                .then()
                .extract().path("access_token").toString();


    }

    @Test
    public void blogPost(){

        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("id","17");
        jsonObject.addProperty("name", "Aftab");
        jsonObject.addProperty("body", "Hi, This is Aftab");

        System.out.println(jsonObject);

        Response response = given().contentType("application/json")
                .queryParam("access_token", getAccessToken("admin", "admin"))
                .auth().basic("my-trusted-client","secret")
                .when()
                .body(jsonObject)
                .post("/post");
        assertThat(response.statusCode(), equalTo(200));


        response.prettyPrint();
    }
    }


