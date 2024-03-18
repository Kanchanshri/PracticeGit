import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import files.payload;


public class Test {

	public static void main(String[] args) {
		JsonPath js =new JsonPath(payload.CoursePrice());
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		int amount =js.getInt("dashboard.purchaseAmount");
		System.out.println(amount);
		
		String title=js.getString("courses[0].title");
		System.out.println(title);
		
		for(int i=0;i<count;i++)
		{
			String allTitles=js.get("courses["+i+"].title");
			int prices=js.get("courses["+i+"].price");
			System.out.println(allTitles);
			System.out.println(prices);
			
		}
		for(int i=0;i<count;i++)
		{
			String allTitles=js.get("courses["+i+"].title");
			if (allTitles.equalsIgnoreCase("RPA"))
			{
			int rpaCount= js.getInt("courses["+i+"].copies");
			System.out.println("count of RPA courses = "+rpaCount);
			break;
			}
			
			
			
		}
			 

	}

}
