package cn.itcast.service;

import org.apache.solr.client.solrj.SolrQuery;

import cn.itcast.pojo.SearchResult;

public interface ProductService {

	/**
	 * service:业务层--参数封装--分页--高亮
	 * 需求:根据条件查询索引库
	 * 参数:String queryString,String catalog_name,String price,Integer page, Integer rows,String sort
	 */
	public SearchResult queryProductsWithCondition(String queryString,String catalog_name,String price,Integer page, Integer rows,String sort) throws Exception;
}
