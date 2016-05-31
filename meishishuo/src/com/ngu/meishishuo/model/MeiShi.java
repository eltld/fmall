package com.ngu.meishishuo.model;

public class MeiShi {
	private long _id;//数据库id
	private String id;//菜谱id
	private String name;//名称
	private String  food;//食物
	private String img;//图片
	private String images;//图片, 
	private String description;//描述
	private String keywords;//关键字
	private String message;//资讯内容
	private String count ;//访问次数
	private String fcount;//收藏数
	private String rcount;//评论读数
	public MeiShi() {
		
	}
	public String getFcount() {
		return fcount;
	}
	public void setFcount(String fcount) {
		this.fcount = fcount;
	}
	public String getRcount() {
		return rcount;
	}
	public void setRcount(String rcount) {
		this.rcount = rcount;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFood() {
		return food;
	}
	public void setFood(String food) {
		this.food = food;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public long get_id() {
		return _id;
	}
	public void set_id(long _id) {
		this._id = _id;
	}
	
}
