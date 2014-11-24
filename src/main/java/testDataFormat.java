import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testDataFormat {
	
	/*
	 * //
	 * 
	 * 注意HH与hh含认不同，HH表示以24小时制取，hh表示以12小时制取
	     常用的格式的含义，摘自Jdk，注意大小写的含义通常是不同的：
		字母    含义                            示例
		y    Year                         1996;96             哪一年
		M    Month in year  J             uly;Jul;07           一年中的哪一月
		m    Minute in hour               30                    一个小时中的第几分钟
		w    Week in year                 27                   一年中的第几个星期
		W    Week in month                2                    一个月中的第几个星期
		D    Day in year                  189                 一年中的第几天
		d    Day in month                 10                  一个月中的第几天
		H    Hour in day (0-23)           0                   一天中的第几个小时（24小时制）
		h    Hour in am/pm (1-12)         12                  一天中上午、下午的第几个小时（12小时制）
		S    Millisecond                  978                 毫秒数
		s    Second in minute             55                  一分钟的第几秒
	 */
	public static void main(String[] args) {
		
		
		long longTime = 1329148803000L;
		String aStringTime = "2012-02-14T00:00:03";
		SimpleDateFormat a_format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		
		SimpleDateFormat a_format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date aDate = new Date(longTime);
		System.out.println(aDate);
		System.out.println(a_format2.format(aDate));

		

		try {
			Date aDate2 = a_format1.parse(aStringTime);
			System.out.println(aDate2);
			System.out.println(a_format2.format(aDate2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
