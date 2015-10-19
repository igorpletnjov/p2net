package ee.ttu.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.digest.DigestUtils;

import ee.ttu.http.handlers.CrackHandler;
import ee.ttu.http.service.NetworkCache;
import ee.ttu.util.Log;

public class MD5Cracker {
	
	 private char[] charset;

	    private int min; //var added for min char length
	    private int max; //var added for max char length
	    public static String code;
	    public static String hash;
	    public static String strTemp;
	    public static String wildcard;
	    public static String tempSet;
	    public static int j;

	    public MD5Cracker() {
	        charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	        tempSet ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        min = 1; //char min start
	        max = 7; //char max end 
	    }

	    public String generate(String str, int pos, int length, String toCrack) {
	    	//System.out.println("Generate välja kutsutud! Muutujad on, str: " + str + ", pos: " + pos + ", length: "
	    		//	+ length + ", toCrack: " + toCrack);
	        if (length == 0) {
            	// teeme md5 instance
				MessageDigest m = DigestUtils.getMd5Digest();
				//System.out.println("Teen md5 instance!!");
				
				// saame stringi baitid
				m.update( str.getBytes() );
		
				//stringi baiditele teeme md5 kama
				byte[] b = m.digest();
				
				//System.out.println("Loon hashi!!");
				// hash on siis hetke stringi md5, see alumine kama siin
				hash = DatatypeConverter.printHexBinary(b);
				//teeme väiketähtedeks sest ta tuleb alati suurte tähetena välja
				hash.toLowerCase();
				//temporary string, lihtsalt et oleks väline muutuja
				strTemp = str;
				
				//System.out.println("PRINDIN STR!!");
				//pmst prindib stringi välja, a...z, aa...zz, jne...
				//System.out.println(str);
	        } else {
	            if (pos != 0) {
	                pos = 0;
	            }
	            //charseti pikkuseni teeme seda
	            for (int i = pos; i < charset.length; i++) {
	            	//teeme hashi lowercasiks double sest millegipärast ta lükkab caps locki uuesti tagasi, kui see võrnde
	            	//crackiga siis tagastame selle stringi, mis see värk on
	                if(hash.toLowerCase().equals(toCrack)) {
	                	//System.out.println("Leidsin hash == toCrack! tagastan strTemp!");
	                	return strTemp;
	                } else {
	                //kui ei leidnud, siis lihtsalt lähme edasi
	               // System.out.println("Kohe kutsun generate uuesti välja!");
	                code = generate(str + charset[i], i, length-1,toCrack);
	                }
	            }
	        }   

	        return code;
	    }
	    public static int range;
	    public static int tempRange;
	    public static String calculator(String toCrack) {
	    	//System.out.println("olen omadega kalkulaatoris!");
	    	final double startTime = System.currentTimeMillis();
	    	double duration;
	    	System.out.println("MD5 Cracker");
	    	System.out.println("Cracking in progress...");
	    	//uus instance
	    	//String test = generate("ig" + charset[i], i, length-1,toCrack)
	        MD5Cracker bruteforce = new MD5Cracker();
	        //System.out.println("väljun kalkulaatorist!");
	        
	        //pikkus on siis ette antud, nt meil min 1 ja max 7, teeb kuni selle pikkuseni
			range = 62;
			if(NetworkCache.getReadyMachines().size() < 1) {
				tempRange = 62;
			} else {
				tempRange = 62 / NetworkCache.getReadyMachines().size();
			}
	        for (int length = bruteforce.min; length < bruteforce.max; length++) { // Change bruteforce.min and bruteforce.max for number of characters to bruteforce. 
	        	//System.out.println("Siin kutsun bruteforce.generate välja!");
				/*for(k = 62 - CrackHandler.range; k <= CrackHandler.tempRange + CrackHandler.j; k++) {
					//Log.info("char " +tempSet.charAt(i));
					System.out.println(k);
					bruteforce.generate(tempSet.charAt(k) + "", 0, length-1, toCrack);
				}*/
	        	/*for(int k= 62 - CrackHandler.range; k <= CrackHandler.tempRange + CrackHandler.j; k++) {
	        		bruteforce.generate(tempSet.charAt(k) + "", 0, length-1, toCrack); //prepend_string, pos, length 
	        		//System.out.println(bruteforce.generate(tempSet.charAt(k) + "", 0, length-1, toCrack));
	        	}*/
			//while(range > 0) {
					//j = 62 - range;
					//if(range - tempRange > 0 && range - (2 * tempRange) > 0 ) {
					//	tempRange = range;
					//}
					for(int i = 62 - range; i < tempRange + j; i++) {
						bruteforce.generate(tempSet.charAt(i) + "", 0, length-1, toCrack);
					}
					//bruteforce.generate("", 0, length-1, toCrack);
					//range = range - tempRange;
				//}
	        	//bruteforce.generate(tempSet.charAt(CrackHandler.i) + "", 0, length-1, toCrack);
	        	//bruteforce.generate("", 0, length-1, toCrack);
	        	//bruteforce.generate("", 0, length-1, toCrack);
	            duration = (System.currentTimeMillis() - startTime) / 1000;
	            
	            //kui leiab võrdse, tagastab selle
	            if(hash.toLowerCase().equals(toCrack)) {
	            	System.out.println("Cracked it ## " + strTemp + " ## Time taken: " + duration + " seconds");
	            	return strTemp;
	            }
	            //else mine edasi, iga järgmine tähemärkide pikkus prindi välja
	            System.out.println("String length is: " + (strTemp.length() + 1) +" chars. Time taken: " + duration + " seconds"); 
	        }
			return null;
	    }
	}