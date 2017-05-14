package com.verity.utilities;

import org.testng.annotations.DataProvider;


public class GetData {
@DataProvider (name="TestData")
 public static Object[][] getData() {
	return new Object[][] {
			{"qbowtest801","n1234567"}
	};
}

}
