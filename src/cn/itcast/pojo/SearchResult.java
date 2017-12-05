package cn.itcast.pojo;

import java.util.List;

public class SearchResult {

	//1.当前页
	private Integer curPage;
	//2.总页数
	private Integer totalPage;
	//3.总记录数
	private Integer totalRecored;
	//4.商品集合
	private List<Product> productList;
	
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getTotalRecored() {
		return totalRecored;
	}
	public void setTotalRecored(Integer totalRecored) {
		this.totalRecored = totalRecored;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	
	
}
