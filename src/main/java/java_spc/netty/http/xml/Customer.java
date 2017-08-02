package java_spc.netty.http.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author SPC
 * 2017年7月27日
 * 顾客信息
 */
public class Customer {
    @XStreamAsAttribute
    private long id;                  //客户id
    private String firstName;         //名
    private String lastName;          //姓
    private List<String> middleNames; //中间名
    private String tel;               //电话号码

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getMiddleNames() {
        return middleNames;
    }

    public void setMiddleNames(List<String> middleNames) {
        this.middleNames = middleNames;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String toString() {
        return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
                + ", middleNames=" + middleNames + "]";
    }

    public static Customer generate(long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName("pc");
        customer.setLastName("s");
        customer.setMiddleNames(null);
        customer.setTel("13247689527");
        return customer;
    }
}
