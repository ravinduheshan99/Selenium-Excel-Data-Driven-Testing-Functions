import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class dataProvide {
	
	@Test(dataProvider="driveTest")
	public void testCaseData(String param1, String param2, int param3) {
		System.out.println(param1+" "+param2+" "+param3);
	}
	
	@DataProvider(name="driveTest")
	public Object[][] getData() {
		Object[][] data = {{"Hello1","Text1",1},{"Hello2","Text2",2},{"Hello3","Text3",3}};
		return data;
	}

}
