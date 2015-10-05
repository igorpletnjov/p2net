package ee.ttu.md5;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Cracker {
	
	// TODO get some sort of library to do this for me -> done (org.apache.commons)
	
	public String crack(String md5string, String template, String wildcard) {
		DigestUtils.md5(template); // Kuidas ühe stringi md5 hashi leida
		// peab siis pmst võrdlema seda argumendina kaasa tulnud md5string hashiga
		// template ja wildcard abil
		
		return null;
	}

}
