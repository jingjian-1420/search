package com.chen;

import com.chen.domain.Product;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.LBHttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SolrTest {

    /**
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void test() throws IOException, SolrServerException {
        LBHttpSolrClient lbHttpSolrClient = new LBHttpSolrClient.Builder()
                .withBaseSolrUrl("http://localhost:8983/solr/")
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        try {
            Random random = new Random();
            int[] a = new int[]{1,2,3};
            double[] b = new double[]{10.21,1.23,4.56,8.59,2.33,1.23,5.99};
            for(int i =0;i<10;i++) {
                Product product = new Product();
                product.setId("123123"+i);
                product.setName1("abcdefg");
                product.setName2("333");
                product.setShowType(a[RandomUtils.nextInt(3)]);
                product.setPerPrice(new Double(i));
                product.setCommission(new Double(100.15+RandomUtils.nextInt(6)));
                product.setTestMuValue(Arrays.asList(i * 1.5d,i*3d));
                product.setTestMupValues(Arrays.asList("专场"+i,"专场"+(i+1),"专题："+i,"专题"+(i+1)));
                UpdateResponse test_core = lbHttpSolrClient.addBean("test_core1", product);
                lbHttpSolrClient.commit("test_core1");
            }
        }catch (Exception e){
            e.printStackTrace();
            lbHttpSolrClient.close();
        }

    }

    /**
     * 移除
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void test1() throws IOException, SolrServerException {
        LBHttpSolrClient lbHttpSolrClient = new LBHttpSolrClient.Builder()
//                .withBaseSolrUrl("http://localhost:8983/solr/")
                .withBaseSolrUrl("http://39.108.19.165:8983/solr/")
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        try {

            lbHttpSolrClient.deleteById("ejavashopcore","32976");
            lbHttpSolrClient.commit("ejavashopcore");
        }catch (Exception e){
            e.printStackTrace();
            lbHttpSolrClient.close();
        }
    }

    /**
     * 查询
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void test2() throws IOException, SolrServerException {
        LBHttpSolrClient lbHttpSolrClient = new LBHttpSolrClient.Builder()
                .withBaseSolrUrl("http://localhost:8983/solr/")
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        try {
            SolrQuery solrQuery = new SolrQuery("*:*");
            QueryResponse response = lbHttpSolrClient.query("test_core1", solrQuery);
            List<Product> beans = response.getBeans(Product.class);
            System.out.println(beans);

        }catch (Exception e){
            e.printStackTrace();
            lbHttpSolrClient.close();
        }
    }

    @Test
    public void test3(){

        LBHttpSolrClient lbHttpSolrClient = new LBHttpSolrClient.Builder()
//                .withBaseSolrUrl("http://search.sibu.net.cn/solr/")
                .withBaseSolrUrl("http://localhost:8983/solr/")
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        try {
            SolrQuery solrQuery = new SolrQuery();
            solrQuery.set("q","show_type:(1 OR 3)");
            solrQuery.set("start",0);
            solrQuery.set("rows",10);
            solrQuery.set("sort"," per_Price desc");
            solrQuery.setFilterQueries("commission:[* TO *]");
//            QueryResponse response = lbHttpSolrClient.query("ejavashopcore", solrQuery);
            QueryResponse response = lbHttpSolrClient.query("test_core1", solrQuery);
            List<Product> beans = response.getBeans(Product.class);
            System.out.println(beans);

        }catch (Exception e){
            e.printStackTrace();
            lbHttpSolrClient.close();
        }
    }

    @Test
    public void test4(){
        LBHttpSolrClient lbHttpSolrClient = new LBHttpSolrClient.Builder()
//                .withBaseSolrUrl("http://search.sibu.net.cn/solr/")
                .withBaseSolrUrl("http://localhost:8983/solr/")
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
        try {
            final SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", "1231239");
            doc.addField("commission",null);
            UpdateResponse add = lbHttpSolrClient.add("test_core1",doc);
            System.out.println(add);
            lbHttpSolrClient.commit("test_core1");
        }catch (Exception e){
            e.printStackTrace();
            lbHttpSolrClient.close();
        }
    }

    @Test
    public void test5(){
        getSolrQ(123, Arrays.asList("1","3"));
    }

    private String getSolrQ(Integer productCateId, List<String> showTypes) {
        StringBuilder sb = new StringBuilder();
        sb.append("productCateId:"+productCateId).append(" ");
        if(!CollectionUtils.isEmpty(showTypes)){
            sb.append(" AND ");
            sb.append("showType:(").append(" ");
            for(String s : showTypes){
                sb.append(s).append(" OR ");
            }
            sb.replace(sb.length()-3,sb.length(),")");
        }

        return sb.toString();
    }


}
