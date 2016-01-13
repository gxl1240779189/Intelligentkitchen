package com.example.httputil;

public class FoodsItem {
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 链接
	 * 
	 */
	private String link;
	/**
	 * 发布日期
	 */
	private String date;
	/**
	 * 图片的链接
	 */
	private String imgLink;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 作者名
	 */
	private String writer;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getImgLink() {
		return imgLink;
	}
	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return title;
	}
}
