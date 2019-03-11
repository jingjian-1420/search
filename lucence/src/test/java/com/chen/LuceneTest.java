package com.chen;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Description:
 * Author: wei
 * Date：2018/10/21
 */
public class LuceneTest {

    /**
     * 创建索引
     */
    @Test
    public void test() throws IOException {
        //将索引存储在内存中：
//        Directory directory = new RAMDirectory();
        //若要在磁盘上存储索引，请使用以下方法:
        Path path = Paths.get("D:", "Lucene", "test","index");
        Directory director1y = FSDirectory.open(path);

        //默认使用 StandardAnalyzer 标准分词器
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig();

        //值 词法分析-语言处理-语法分析
        IndexWriter indexWriter = new IndexWriter(director1y,indexWriterConfig);

        Document doc = new Document();
        doc.add(new Field("name","wo shi shui, ni hao ya?",TextField.TYPE_STORED));

        indexWriter.addDocument(doc);

        indexWriter.close();
        director1y.close();
    }

    /**
     * 搜索索引
     */
    @Test
    public void test1() throws IOException, ParseException {
        Path path = Paths.get("D:", "Lucene", "test","index");

        FSDirectory directory = FSDirectory.open(path);
        //读索引
        IndexReader indexReader = DirectoryReader.open(directory);
        //搜索索引
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);


        //标准分词器
        Analyzer analyzer = new StandardAnalyzer();

        //搜索 含有wo 的文档
        QueryParser parser = new QueryParser("name", analyzer);
        Query query = parser.parse("wo");
        ScoreDoc[] hits = indexSearcher.search(query,1000).scoreDocs;

        //迭代结果:
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = indexSearcher.doc(hits[i].doc);
        }
        indexReader.close();
        directory.close();
    }
}
