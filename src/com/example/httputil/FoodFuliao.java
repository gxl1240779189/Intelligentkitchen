package com.example.httputil;

public class FoodFuliao {
	String fuliaoname;
	String fuliaoshuliang;

	public String getFuliaoname() {
		return fuliaoname;
	}

	public void setFuliaoname(String fuliaoname) {
		this.fuliaoname = fuliaoname;
	}

	public String getFuliaoshuliang() {
		return fuliaoshuliang;
	}

	public void setFuliaoshuliang(String fuliaoshuliang) {
		this.fuliaoshuliang = fuliaoshuliang;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return fuliaoname + " --" + fuliaoshuliang;
	}

}
