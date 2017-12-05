package cn.itcast.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.ProductDao;
import cn.itcast.pojo.SearchResult;
import cn.itcast.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;
	
	/**
	 * service:业务层--参数封装--分页--高亮
	 * 需求:根据条件查询索引库
	 * 参数:String queryString,String catalog_name,String price,Integer page, Integer rows,String sort
	 */
	public SearchResult queryProductsWithCondition(String queryString, String catalog_name, String price, Integer page,
			Integer rows, String sort) throws Exception {
		
		//创建solrQuery,封装所有查询参数
		SolrQuery solrQuery = new SolrQuery();
		
		//判断调教是否为空.或者是空字符串
		if(queryString != null && !"".equals(queryString)){
			solrQuery.setQuery(queryString);
		} else {
			solrQuery.setQuery("*:*");
		}
		
		//分类过滤查询
		if(catalog_name != null && !"".equals(catalog_name)){
			solrQuery.addFilterQuery("product_catalog_name:"+catalog_name);
		}
		//价格过滤
		if(price != null && !"".equals(price)){
			//切割价格
			String[] prices = price.split("-");
			solrQuery.addFilterQuery("product_price:["+prices[0]+" TO "+prices[1]+"]");
		}
		
		//分页设置
		//计算起始页
		int startNo = (page - 1) * rows;
		solrQuery.setStart(startNo);
		solrQuery.setRows(rows);
		
		//排序
		if("1".equals(sort)){
			solrQuery.addSort("product_price", ORDER.asc);
		} else {
			solrQuery.addSort("product_price", ORDER.desc);
		}
		
		//高亮设置
		//开启高亮
		solrQuery.setHighlight(true);
		//设置高亮字段
		solrQuery.addHighlightField("product_name");
		//设置高亮前缀
		solrQuery.setHighlightSimplePre("<font color='red'>");
		//后缀
		solrQuery.setHighlightSimplePost("</font>");
		
		//设置默认域
		solrQuery.set("df", "product_keywords");
		
		
		SearchResult result = productDao.queryProductsWithCondition(solrQuery);
		//先设置当前页值到分页包装类对象
		result.setCurPage(page);
		//计算总页码
		int count = result.getTotalRecored();
		int pages = count / rows;
		//如果总页码除以每页显示条数除不尽,那么就再增加一页
		if(pages % rows > 0){
			pages++;
		}
		//把总页码设置到分页包装类对象
		result.setTotalPage(pages);
		return result;
	}

	

}
