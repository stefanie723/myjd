package cn.itcast.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.itcast.dao.ProductDao;
import cn.itcast.pojo.Product;
import cn.itcast.pojo.SearchResult;

@Repository
public class ProductDaoImpl implements ProductDao{

	@Autowired
	private SolrServer solrServer;
	
	/**
	 * DAO:数据访问层--访问索引库 参数:SolrQuery 方法名:queryProductsWithCondition
	 */
	public SearchResult queryProductsWithCondition(SolrQuery solrQuery) throws Exception {
		//创建分页包装类对象
		SearchResult result = new SearchResult();
		//创建list集合,封装从索引库查询商品数据
		List<Product> productList = new ArrayList<Product>();
		try {
			//根据分装参数对象访问索引库
			QueryResponse response = solrServer.query(solrQuery);
			//获取文档集合
			SolrDocumentList results = response.getResults();
			//获取命中总商品数量
			Long numFound = results.getNumFound();
			//把总记录数字封装到分页包装类对象
			result.setTotalRecored(numFound.intValue());
			// 直接循环文档集合对象
			for (SolrDocument docs : results) {
				//创建商品对象,封装每一个商品文档数据
				Product product = new Product();
				String id = (String) docs.get("id");
				product.setPid(id);
				
				//标题
				String name = (String) docs.get("product_name");
				product.setName(name);
				
				//图片地址
				String picture = (String) docs.get("product_picture");
				product.setPicture(picture);
				
				//价格
				Float price = (Float) docs.get("product_price");
				product.setPrice(price);
				
				//获取高亮
				Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
				//第一个map的key就是id
				Map<String, List<String>> map = highlighting.get(id);
				//第二个map的key是高亮字段名称
				List<String> list = map.get("product_name");
				if(list != null && list.size() > 0){
					name = list.get(0);
				}
				//把商品对象放入集合
				productList.add(product);
			}
			//把查询所有商品结果放入分页包装类对象
			result.setProductList(productList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
