package com.example.tmall;

import java.util.ArrayList;
import java.util.List;

import com.example.tmall.bean.Address;
import com.example.tmall.bean.Product;
import com.example.tmall.bean.User;

public class Datas {
	/*���ﳵ*/
	public static List<Product> cartProducts=new ArrayList<Product>();
	/*ȷ�϶���*/
	public static List<Product> ensureOrderProducts=new ArrayList<Product>();
	/*�ջ���ַ*/
	public static List<Address> adderssList=new ArrayList<Address>();
	/*Ĭ���ջ���ַ*/
	public static Address morenAddress=new Address();
	/**/
	static{
		morenAddress.setName("����");
		morenAddress.setPhone("800-82080000");
		morenAddress.setCity("����ʡ�人�к�ɽ��");
		morenAddress.setDetail("����ũҵ��ѧ");
	}
	/*��ǰ��¼�û�*/
	public static User currentUser=new User();
	static{
		currentUser.setUserName("���¼");
	}
	/*�Ƿ��¼*/
	public static boolean isLogin=false;
}
