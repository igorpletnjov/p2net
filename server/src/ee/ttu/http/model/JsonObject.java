package ee.ttu.http.model;

import java.util.ArrayList;
import java.util.List;

public class JsonObject {
	
	// This is for /resource POST json only
	
	String ip;
	String port;
	String id;
	String md5;
	List<String> ranges = new ArrayList<String>();
	String wildCard;
	List<IntegerPair> symbolRange = new ArrayList<IntegerPair>();
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getWildCard() {
		return wildCard;
	}
	public void setWildCard(String wildCard) {
		this.wildCard = wildCard;
	}
	public List<String> getRanges() {
		return ranges;
	}
	public List<IntegerPair> getSymbolRange() {
		return symbolRange;
	}
	@Override
	public String toString() {
		return "JsonObject [ip=" + ip + ", port=" + port + ", id=" + id
				+ ", md5=" + md5 + ", ranges=" + ranges + ", wildCard="
				+ wildCard + ", symbolRange=" + symbolRange + "]";
	}
	

}
