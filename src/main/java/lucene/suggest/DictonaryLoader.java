package lucene.suggest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import java.util.Map.Entry;

import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.suggest.FileDictionary;



public class DictonaryLoader {


	public static Dictionary loadDictionary(String dicPath) throws Exception{
		
		File dicFile=new File(dicPath);
    	FileInputStream fileInputStream=new FileInputStream(dicFile);
    	InputStreamReader inputStreamReader=new InputStreamReader(fileInputStream,"utf-8");
    	BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
    //	PlainTextDictionary  dictionary=new PlainTextDictionary(dicFile);
    	FileDictionary  dictionary=new FileDictionary(bufferedReader);
    	
    	bufferedReader.close();
    	
    	return dictionary;
	}
	public static void main(String[] args) throws Exception{
		

		
		 
	}
	
}
