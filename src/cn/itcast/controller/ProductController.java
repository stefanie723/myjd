package cn.itcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.itcast.pojo.SearchResult;
import cn.itcast.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	
	/**
	 * 需求:跳转到商品搜索列表页面product_list
	 * 参数:String queryString,String catalog_name,String price,Integer page,Integer rows,String sort,Model model
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	public String list(String queryString,
			String catalog_name,
			String price,
			@RequestParam(defaultValue="1")Integer page,
			@RequestParam(defaultValue="60")Integer rows,
			@RequestParam(defaultValue="1")String sort,
			Model model) throws Exception{
		SearchResult result = productService.queryProductsWithCondition(queryString, catalog_name, price, page, rows, sort);
		//回显主要查询条件
		model.addAttribute("queryString",queryString);
		//类别
		model.addAttribute("catalog_name", catalog_name);
		//价格
		model.addAttribute("price", price);
		//排序
		model.addAttribute("sort", sort);
		//分页数据
		model.addAttribute("result", result);
		
		return "product_list";
	}
}
