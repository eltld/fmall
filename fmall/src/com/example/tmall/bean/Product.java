package com.example.tmall.bean;

import java.io.Serializable;

public class Product implements Serializable {  
      
		private static final long serialVersionUID = 1L;
		private int id;
        private String proImg;  //商品图片
        private String ProName;   //商品名称
        private String ProMsg;//商品信息
        private String ProClassify;//商品分类
        private int ProStock;//商品库存
        private double ProPrice;    //商品价格
        private int proCount=1;//商品购买数量
        
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