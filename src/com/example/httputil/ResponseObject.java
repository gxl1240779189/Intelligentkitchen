package com.example.httputil;


public class ResponseObject<T> {

	private int state;
	private T datas;
	
	public ResponseObject() {
		
	}
	
	public ResponseObject(int state, T datas) {
		super();
		this.state = state;
		this.datas = datas;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public T getDatas() {
		return datas;
	}

	public void setDatas(T datas) {
		this.datas = datas;
	}
	
}
