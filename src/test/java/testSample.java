import java.io.IOException;
import java.util.ArrayList;

public class testSample {

	public static void main(String[] args) throws IOException {
		// Create an instance of the dataDriven class
		dataDriven d = new dataDriven();

		// Fetch data for the test case named "Add Profile"
		ArrayList data = d.getData("Add Profile");

		// Print the retrieved data
		System.out.println(data.get(0));
		System.out.println(data.get(1));
		System.out.println(data.get(2));
		System.out.println(data.get(3));
	}

}
