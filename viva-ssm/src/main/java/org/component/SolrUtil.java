package org.component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SolrUtil {
	@Autowired
	private HttpSolrServer httpSolrServer;

	/**
	 * @param SolrInputDocument
	 *            添加单条索引
	 */
	public void saveSingle(SolrInputDocument obj) {
		try {
			httpSolrServer.add(obj);
			httpSolrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加多条索引
	 * 
	 * @param list
	 *            为SolrInputDocument对象集合
	 */
	public void saveList(List<SolrInputDocument> list) {
		try {
			httpSolrServer.add(list.iterator());
			httpSolrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除单个索引
	 * 
	 * @param 索引id
	 */
	public void deleteById(String id) throws SolrServerException, IOException {
		try {
			httpSolrServer.deleteByQuery("id:" + id);
			httpSolrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除多个索引
	 * 
	 * @param ids
	 *            为多个索引id的集合
	 */
	public void deleteByIds(List<String> ids) throws SolrServerException, IOException {
		try {
			String idName = "id";
			if (null != ids && !ids.isEmpty()) {
				StringBuffer query = new StringBuffer();
				for (String id : ids) {
					query.append(idName + ":" + id + " OR ");
				}
				query.delete(query.length() - 4, query.length());
				httpSolrServer.deleteByQuery(query.toString());
				httpSolrServer.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除所有索引
	 */
	public void deleteAll() {
		try {
			httpSolrServer.deleteByQuery("*:*");
			httpSolrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param query 
	 *            查询条件字符串，格式为 "key1:val1"
	 * @param pageIndex
	 *            查询开始页
	 * @param pageSize
	 *            查询页数据条数
	 * @param field
	 *            排序依据字段
	 * @param order
	 *            排列顺序asc,或者空为正序
	 * @param highLightFields
	 *            高亮显示的字段必须是solr内的字段
	 ***/
	public SolrDocumentList search(String query, Integer pageIndex, Integer pageSize, String field, String order,
			List<String> highLightFields) {
		try {
			httpSolrServer.setRequestWriter(new BinaryRequestWriter());
			SolrQuery sq = new SolrQuery();
			sq.setQuery(query);
			sq.setStart(pageIndex);
			sq.setRows(pageSize);
			sq.setSort(field, "asc".equals(order) | !"desc".equals(order) ? ORDER.asc : ORDER.desc);
			boolean isHightLight = highLightFields != null && !highLightFields.isEmpty();
			if (isHightLight) {
				sq.setHighlight(true);
				sq.setHighlightSimplePre("<font color=\"red\">");
				sq.setHighlightSimplePost("</font>");
				for (String highLightField : highLightFields) {
					sq.addHighlightField(highLightField);
				}
			}
			QueryResponse response = httpSolrServer.query(sq);
			SolrDocumentList docs = response.getResults();
			if (isHightLight) {
				Map<String, Map<String, List<String>>> map = response.getHighlighting();
				for (SolrDocument doc : docs) {
					for (String highLightField : highLightFields) {
						List<String> list = map.get(doc.getFieldValue("id")).get(highLightField);
						if (list != null && !list.isEmpty()) {
							doc.setField(highLightField, list);
						}
					}
				}
			}
			return docs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
