package ee.ttu.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Cracker {
	
	 private char[] charset;

	    private int min; //var added for min char length
	    private int max; //var added for max char length
	    public static String code;
	    public static String hash;
	    public static String toCrack;
	    public static String strTemp;
	    public static String wildcard;

	    public MD5Cracker() {
	        charset = "abcdefghijklmnopqrstuvwxyzAEIOU0123456789".toCharArray();
	        min = 1; //char min start
	        max = 7; //char max end 
	    }

	    public String generate(String str, int pos, int length) {
	        if (length == 0) {
	            try {
					MessageDigest m = MessageDigest.getInstance("MD5");
					//wildcard = "p" + str + "s";
					m.update(str.getBytes());
					//m.update(wildcard.getBytes());
					byte[] b = m.digest();
					hash = javax.xml.bind.DatatypeConverter.printHexBinary(b);
					hash.toLowerCase();
					//strTemp = wildcard;
					strTemp = str;
					//System.out.println(str);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
	        } else {
	            if (pos != 0) {
	                pos = 0;
	            }
	            for (int i = pos; i < charset.length; i++) {
	                if(hash.toLowerCase().equals(toCrack)) {
	                	return strTemp;
	                } else {
	                code = generate(str + charset[i], i, length-1);
	                }
	            }
	        }
	        

	        return code;
	    }

	    public static void main(String[] args) {
	    	final double startTime = System.currentTimeMillis();
	    	double duration;
	    	System.out.println("md5 cracker");
	    	System.out.println("cracking in progres...");
	    	toCrack="17de7832728f3ede291623dea0e0a377"; // praks
	        MD5Cracker bruteforce = new MD5Cracker();
	        for (int length = bruteforce.min; length < bruteforce.max; length++) { // Change bruteforce.min and bruteforce.max for number of characters to bruteforce. 
	            bruteforce.generate("", 0, length-1); //prepend_string, pos, length 
	            duration = (System.currentTimeMillis() - startTime) / 1000;
	            if(hash.toLowerCase().equals(toCrack)) {
	            	System.out.println("Cracked it ## " + strTemp + " ## Time taken: " + duration + " seconds");
	            	return;
	            }
	            System.out.println("String length is: " + (strTemp.length() + 1) +" chars. Time taken: " + duration + " seconds");
	        }
	    }
	}