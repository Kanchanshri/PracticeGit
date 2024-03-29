import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.payload;
public class BasicsExternalFile {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Kanchan_Backup_11JAN24\\Backup\\Study Material for interviews\\API Testing\\addPlace.json")))).when().post("/maps/api/place/add/json")
        .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
        .header("Server","Apache/2.4.52 (Ubuntu)").extract().asString();
		// add place, update place, get place to validate if new address is present in response		
		System.out.println(response);
		JsonPath js =new JsonPath(response);//for parsing json
		String placeId=js.get("place_id");
		System.out.println(placeId);
		
		//Update place
		String newAddress ="Summer walk, Africa";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").when().put("/maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
		
	//get Place
	
	String getPlaceResponse =
		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json").then().assertThat().log().all().statusCode(200)
		.extract().response().asString();
		
	JsonPath js1 =new JsonPath(getPlaceResponse);
	String actualAddress= js1.getString("address");
	System.out.println(actualAddress);
	//now we are outside of restassured , so we use Testng jar
	Assert.assertEquals(actualAddress, newAddress);

		
        
	}

}
