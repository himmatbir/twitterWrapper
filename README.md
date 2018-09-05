# TwitterWrapper

NOTE : As my twitter dev account access is pending since 3 days, I could not test the code by invoking twitter apis after generating access token and hence the response flow from twitter apis is not tested

Please replace NOT_KNOWN with actual values : com.personal.twitterwrapper.consumerKey=NOT_KNOWN and com.personal.twitterwrapper.consumerSecret=NOT_KNOWN in application.properties

Below are the custom error codes:
	//Custom Error Codes
	public static final String SUCCESS_CODE = "1001";
	public static final String INTERNAL_SERVER_ERROR = "1002";
	public static final String AUTH_ERROR_CODE = "1003";

	
Steps:
1. Install java 8 and maven
2. Checkout code as using the git repository link
3. Import the project in IDE
4. Install maven plugin in IDE
5. In IDE, right click on "pom.xml" file and select "Add to maven"
6. Right click on the project and select "Rebuild Module twitterWrapper"
7. Create run configuration with main class as: com.personal.twitterwrapper.Application and path to java jdk
8. Now click on run
9. Now import the postman collection and invoke apis