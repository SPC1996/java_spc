package java_spc.netty.http.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author SPC
 * 2017年7月27日
 * 地址信息
 */
@XStreamAlias("address")
public class Address {
	private String street;        //街道
	private String city;          //城市
	private String country;       //国家

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String toString() {  
        return "Address [street=" + street + ", city=" + city + ", country=" + country  + "]";  
    } 
	
	public static Address generate(long id) {
		Address address=new Address();
		address.setStreet("文源街道");
		address.setCity("长沙");
		address.setCountry("中南");
		return address;
	}
}
