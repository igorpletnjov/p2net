package ee.ttu.p2net.util;

public class Constants {
	
	// Check with these when handling a /resource GET (url parameters)
	public static final String[] REQUIRED_RESOURCE_PARAMETERS = {"sendip", "sendport", "ttl"};
	public static final String[] OPTIONAL_RESOURCE_PARAMETERS = {"id", "noask"};
	
	// Check with these when handling a /checkmd5 POST (requestbody json content)
	public static final String[] REQUIRED_CHECKMD5_PARAMETERS = {"ip", "port", "id", "md5", "ranges"};
	public static final String[] OPTIONAL_CHECKMD5_PARAMETERS = {"wildcard", "symbolrange"};
	
	// Check with these when handling a /answermd5 POST (requestbody json content)
	public static final String[] REQUIRED_ANSWERMD5_PARAMETERS = {"ip", "port", "id", "md5", "resut", "resultstring"};

}
