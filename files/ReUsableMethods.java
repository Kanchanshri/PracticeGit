package files;

import io.restassured.path.json.JsonPath;

public class ReUsableMethods {

	public static JsonPath rawToJson(String response) {
		// TODO Auto-generated method stub
		return new JsonPath(response);
	}

}