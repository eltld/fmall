package com.example.tmall.bean;

import java.io.Serializable;

public class Product implements Serializable {  
      
		private static final long serialVersionUID = 1L;
		private int id;
        private String proImg;  //��ƷͼƬ
        private String ProName;   //��Ʒ����
        private String ProMsg;//��Ʒ��Ϣ
        private String ProClassify;//��Ʒ����
        private int ProStock;//��Ʒ���
        private double ProPrice;    //��Ʒ�۸�
        private int proCount=1;//��Ʒ��������
        
    	public int getId() {
    		return id;
    	}
    	public void setId(int id) {
    		this.id = id;
    	}
        public String getProImg() {  
            return proImg;  
        }  
        public void setProImg(String proImg) {  
            this.proImg = proImg;  
        }  
        public String getProName() {  
            return ProName;  
        }  
        public void setProName(String proName) {  
            ProName = proName;  
        }
		public double getProPrice() {
			return ProPrice;
		}
		public void setProPrice(double proPrice) {
			ProPrice = proPrice;
		}
		public int getProCount() {
			return proCount;
		}
		public void setProCount(int proCount) {
			this.proCount = proCount;
		}
		public String getProMsg() {
			return ProMsg;
		}
		public void setProMsg(String proMsg) {
			ProMsg = proMsg;
		}
		public String getProClassify() {
			return ProClassify;
		}
		public void setProClassify(String proClassify) {
			ProClassify = proClassify;
		}
		public int getProStock() {
			return ProStock;
		}
		public void setProStock(int proStock) {
			ProStock = proStock;
		}  
		
       
}  