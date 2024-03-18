package files;
import static io.restassured.RestAssured.*;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI="http://localhost:8080/";
		SessionFilter session =new SessionFilter();
		String response= given().header("Content-Type","application/json")
		.body("{ \"username\": \"kanchanshri\", \"password\": \"HappyUs@11052013\" }").log().all()
		.when().log().all().filter(session).post("rest/auth/1/session").then().extract().response().asString();
		
		//Add comments
		given().pathParam("key", "10105").log().all().header("Content-Type","application/json").body("{\r\n"
				+ "    \"body\": \"This is my first comment\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session)
		.when().post("rest/api/2/issue/{key}/comment").then().log().all().assertThat().statusCode(201);
		
		// Add Attachments in Jira
		given().header("X-Atlassian-Token" ,"no-check").filter(session).pathParam("key", "10105")
		.header("Content-Type","multipart/form-data")
		.multiPart("file",new File("jira.txt"))
		.when().post("rest/api/2/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		//Get issue
		String issueDetails=given().filter(session).pathParam("key", "10105")
				.queryParam("fields", "comment").log().all().when().get("rest/api/2/issue/{key}")
		.then().log().all().extract().response().asString();
		System.out.println(issueDetails);

		

	}

}
