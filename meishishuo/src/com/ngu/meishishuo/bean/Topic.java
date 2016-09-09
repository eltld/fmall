package com.ngu.meishishuo.bean;

import java.util.List;

/**
 * @author zhoufeng06@qq.com
 * @time 2016年6月3日-下午1:45:54
 */
public class Topic {
	private Long _id;//数据库id
	private String name;//用户名
	private String content;//内容
	private String time;//时间
	private String praiseCount;//赞数
	private String commentCount;//评论数
	private List<Comment> comments;//评论列表
	public Long get_id() {
		return _id;
	}
	public void set_id(Long _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(String praiseCount) {
		this.praiseCount = praiseCount;
	}
	public String getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
