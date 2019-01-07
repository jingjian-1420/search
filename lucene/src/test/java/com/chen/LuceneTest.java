package com.chen;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
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

    @Test
    public void test() throws IOException, ParseException {
        //标准分词器
        Analyzer analyzer = new StandardAnalyzer();

        //将索引存储在内存中：
//        Directory directory = new RAMDirectory();
        //若要在磁盘上存储索引，请使用以下方法:

        Directory directory = FSDirectory.open(Paths.get("d:\\tmp\\testindex"));

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter iwriter = new IndexWriter(directory, config);


        Document doc = new Document();
        String text = "This is the text to be indexed.";
        doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
        iwriter.addDocument(doc);
        iwriter.close();

        //现在搜索索引:
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);

        //解析搜索“text”的简单查询：
        QueryParser parser = new QueryParser("fieldname", analyzer);
        Query query = parser.parse("text");
        ScoreDoc[] hits = isearcher.search(query,1).scoreDocs;

//        assertEquals(1, hits.length);
        //迭代结果:
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = isearcher.doc(hits[i].doc);
//            assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
        }
        ireader.close();
        directory.close();
    }

    /**
     * 分词
     */
    @Test
    public void test1(){

    }
}
