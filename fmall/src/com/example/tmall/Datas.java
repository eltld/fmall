package com.example.tmall;

import java.util.ArrayList;
import java.util.List;

import com.example.tmall.bean.Address;
import com.example.tmall.bean.Product;
import com.example.tmall.bean.User;

public class Datas {
	/*购物车*/
	public static List<Product> cartProducts=new ArrayList<Product>();
	/*确认订单*/
	public static List<Product> ensureOrderProducts=new ArrayList<Product>();
	/*收货地址*/
	public static List<Address> adderssList=new ArrayList<Address>();
	/*默认收货地址*/
	public static Address morenAddress=new Address();
	/**/
	static{
		morenAddress.setName("肥瞄");
		morenAddress.setPhone("800-82080000");
		morenAddress.setCity("湖北省武汉市洪山区");
		morenAddress.setDetail("华中农业大学");
	}
	/*当前登录用户*/
	public static User currentUser=new User();
	static{
		currentUser.setUserName("请登录");
	}
	/*是否登录*/
	public static boolean isLogin=false;
}
