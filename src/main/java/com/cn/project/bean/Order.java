package com.cn.project.bean;

public class Order {
   /**
    * 订单id
    */
	private String orderid;
	/**
	 * 订单实付金额
	 */
	private String payment;
	/**
	 * 支付类型
	 */
	private String paytype;
	/**
	 * 订单状态
	 */
	private int orderstatus;
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public int getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(int orderstatus) {
		this.orderstatus = orderstatus;
	}
	@Override
	public String toString() {
		return "Order [orderid=" + orderid + ", payment=" + payment + ", paytype=" + paytype + ", orderstatus="
				+ orderstatus + "]";
	}
}
