package java_spc.netty.http.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author SPC
 * 2017年7月27日
 * 订单信息
 */
@XStreamAlias("order")
public class Order {
	@XStreamAsAttribute
	private long id;           // 订单id
	private Customer customer; // 顾客信息
	private Address billTo;    // 账单邮寄地址
	private Address shipTo;    // 货物邮寄地址
	private Shipping shipping; // 邮寄种类
	@XStreamAsAttribute
	private Float total;       // 支付价格

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getBillTo() {
		return billTo;
	}

	public void setBillTo(Address billTo) {
		this.billTo = billTo;
	}

	public Address getShipTo() {
		return shipTo;
	}

	public void setShipTo(Address shipTo) {
		this.shipTo = shipTo;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}
	
	public String toString() {  
        return "Order [id=" + id + ", customer=" + customer + ", billTo=" + billTo + ", shipping="  
                + shipping.toString() + ", shipTo=" + shipTo + ", total=" + total + "]";  
    } 
	
	public static Order generate(long id) {
		Order order=new Order();
		order.setId(id);
		order.setCustomer(Customer.generate(id++));
		order.setBillTo(Address.generate(id++));
		order.setShipping(Shipping.DOMESTIC_EXPRESS);
		order.setTotal((float) 550.0);
		return order;
	}
}
