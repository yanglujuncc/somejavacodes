package lucene;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;

public class testLuceneQuery {


/**
	 * 查询方法
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 * @throws ParseException 
	 * @throws InvalidTokenOffsetsException 
	 */
	public  ScoreDoc[] Search(String searchString,  IndexSearcher isearcher) throws Exception{
		
		//方法一:
	    //String[] fields = {"fileName","fieldid","date"};
        //MultiFieldQueryParser  用来查询多个字段 queryparser 
        //QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_40 ,fields, this.indexSettings.getAnalyzer());
		//Query query = parser.parse(searchString);
	    
		//方法二: 词条查询 
        //Term t = new Term("fileName", searchString);
        //Query query = new TermQuery(t);
	    //String[] searchFields = {"Infotitle","Linkaddr","Skuname","Skudetaile"};
        //布尔类型查询（BooleanQuery）
	    //BooleanQuery query = new BooleanQuery();
	    BooleanQuery query = new BooleanQuery();
	    //检索的关键词
	    /*QueryParser parser = new MultiFieldQueryParser(Version.LUCENE_40 ,searchFields,this.indexSettings.getAnalyzer());
        Query q1 = parser.parse("苹果");
        query.add(q1,BooleanClause.Occur.MUST);*/
       //哪个模块下下的
        Term t2 = new Term("fileName","java宝典");
        Query q2 = new TermQuery(t2);
       // query.add(q2,BooleanClause.Occur.SHOULD);
        Term t2s = new Term("fileName","java神器");
        Query q2s = new TermQuery(t2s);
       // query.add(q2s,BooleanClause.Occur.SHOULD);
        
        
        
        
       //供求类型
        /*Term t3 = new Term("Infotype","2");
        Query q3 = new TermQuery(t3);
        query.add(q3,BooleanClause.Occur.MUST);*/
       /* 
      //地区编号
        Term t4 = new Term("Areacode","");
        PrefixQuery q4 = new PrefixQuery(t4);
       query.add(q4,BooleanClause.Occur.MUST);
        
      //产品种类 
        Term t5 = new Term("Infocateg","");
        PrefixQuery q5 = new PrefixQuery(t5);
        query.add(q5,BooleanClause.Occur.MUST);*/
	   /* Term t1 = new Term("fileName", "*");
        TermQuery q1 = new TermQuery(t1);
	    
	    Term t2 = new Term("date", "");
	    PrefixQuery q2 = new PrefixQuery(t2);*/
	    
	   // Term t3 = new Term("fileName", "java");
        //PrefixQuery q3 = new PrefixQuery(t3);
        
		
		//query.add(q1,BooleanClause.Occur.MUST);
        //query.add(q3,BooleanClause.Occur.MUST);
        //query.add(q2,BooleanClause.Occur.MUST);
		
        //范围搜索（TermRangeQuery） ------------------------
        //当查询范围较大且满足条件的文档数量也很大时效率是很低的
	    //Date date = new Date();
        //SimpleDateFormat time=new SimpleDateFormat("yyyyMMddHHmmss");
        //String times = time.format(date);
        //范围搜索（TermRangeQuery）
        //当查询范围较大且满足条件的文档数量也很大时效率是很低的
        //BytesRef lowerTerm = new BytesRef("19000101010100");
        //BytesRef upperTerm = new BytesRef(times);
        //System.out.println("19000101010100");
        //System.out.println(times);
        //TermRangeQuery query = new  TermRangeQuery("Pubtime", lowerTerm, upperTerm, true, true);
        //模糊查询
        //另外两个构造函数 
        //FuzzyQuery fd  = new FuzzyQuery(term, maxEdits)
        //maxEdits 最大相似度，默认为0.5（待议 3.0版的）
        //prefixLength 表示进行模糊匹配的时候，要有多少个前缀字母必须完全匹配
        //如果是1，表示所有词条只有第一个字母与检索关键字相符时，才会被放入备选集合中
        //FuzzyQuery fd  = new FuzzyQuery(t,int 1,int prefixLength)
        //FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("fileName", "java"), 0); // 数字越小越精确0-2
		
        //前缀查询  最前面的几个字
        //PrefixQuery query2 = new PrefixQuery(term);
        
        //短语查询 先拆分成单字 核心方法 使用 布尔类型查询（BooleanQuery）来装载子句
        //子句间关系设为 MUST取其交集
        //setSlop（int） 设置坡度，默认值为0，给出严格匹配短语的文档作为结果，
        //设置为setSlop（1）,表示中间可以插入无关字数的个数 1代表中间可以插入无关字个数为1个
        //PhraseQuery query2 = new PhraseQuery();
        //query2.add(term);
        //query2.setSlop(1);
        
        //多短语查询（自我感觉鸡肋,也许有一天我会用到他的）
        //MultiPhraseQuery query2 = new MultiPhraseQuery();
        
        //通配符搜索
        //Term term = new Term("filed","?o*");
        //WildcardQuery query2 = new WildcardQuery(term); 
        
        //跨度搜索
        //SpanTermQuery query2 = new SpanTermQuery(term);
        
        //Sort sort = new Sort();
        //SortField sortField = new SortField("fileid",FieldCache.DEFAULT_INT_PARSER, false); 
        //sort.setSort(sortField);
        //ScoreDoc[] docs = this.indexSearcher.search(query,100,sort).scoreDocs;
        
        ScoreDoc[] docs = isearcher.search(query,100).scoreDocs;
		System.out.println("一共有:"+docs.length+"条记录");
		//List result = luceneResultCollector.collect(docs, this.indexSearcher, query ,this.indexSearcher,this.indexSettings.getAnalyzer());
		return docs;
	}
}
