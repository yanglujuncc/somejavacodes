import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class testPattern {
	public static void main(String[] argvs) throws ParseException
	{
		System.out.println("************case  *************");
		
		
		  //²éÕÒÒÔJava¿ªÍ·,ÈÎÒâ½áÎ²µÄ×Ö·û´®
		  Pattern pattern = Pattern.compile("^Java.*");
		  Matcher matcher = pattern.matcher("Java²»ÊÇÈË");
		  
	
		  boolean b= matcher.matches();
		  //µ±Ìõ¼şÂú×ãÊ±£¬½«·µ»Øtrue£¬·ñÔò·µ»Øfalse
		 
		  System.out.println(b);
		  String str="2012-02-26T02:38:01+08:00 qn162-157 master_27[28264]: [86588]INFO|CHAT|¶ÓÎé(127115) 1819960027 Ì§Í·Î¢Ğ¦0476 IP:123.82.13.224 ÔõÃ´ÁË°¡£¬²»È»÷ÈÀ­²»×¡³ğ";
		 
		 // ÔÚJavaÓïÑÔÖĞ£¬\\±íÊ¾Òª²åÈëÕıÔò±í´ïÊ½µÄ·´Ğ±Ïß£¬²¢ÇÒºóÃæµÄ×Ö·ûÓĞÌØÊâÒâÒå¡£
		  boolean a= Pattern.matches(".*INFO\\|CHAT.*", str);
	
		  System.out.println("a="+a);
		 
		  //ÒÔ¶àÌõ¼ş·Ö¸î×Ö·û´®Ê±
		  System.out.println("************case 0 *************");
		
		  Pattern pattern0 = Pattern.compile("[, |]+");
		  String[] strs = pattern0.split("Java Hello World  Java,Hello,,World|Sun");
		  for (int i=0;i<strs.length;i++) {
		      System.out.println(strs[i]);
		  } 
		  
		  
		  
		  //ÎÄ×ÖÌæ»»£¨Ê×´Î³öÏÖ×Ö·û£©
		  System.out.println("************case 1 *************");
		 
		  Pattern pattern1 = Pattern.compile("ÕıÔò±í´ïÊ½");
		  Matcher matcher1 = pattern1.matcher("ÕıÔò±í´ïÊ½ Hello World,ÕıÔò±í´ïÊ½ Hello World");
		  //Ìæ»»µÚÒ»¸ö·ûºÏÕıÔòµÄÊı¾İ
		
		  System.out.println(matcher1.replaceFirst("Java"));
		  System.out.println(matcher1.replaceAll("c++"));
		  
		  
		  //ÕÒ³öÆ¥ÅäµÄ×Ö
		  System.out.println("************case 2 *************");
		  Pattern pattern2 = Pattern.compile("Java.");
		  
		  
		 // String message_reg1="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2} .+ .+ \\[[0-9]+\\]INFO|CHAT|IMMESSAGE .+";
		  String message_reg="[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}\\+\\d{2}:\\d{2}\\s.+\\s.+\\s\\[\\d+\\]INFO\\|CHAT\\|IMMESSAGE\\s.+";
		  String line="2012-02-26T21:49:13+08:00 hz174-20 gas_141[15771]: [86588]INFO|CHAT|IMMESSAGE SenderId:1724310141 RecverId:1721550141 IP:61.130.215.128 Message:¼¼ÄÜ¿ÉÒÔÂıÂıÀ´";

		  Pattern pattern_message = Pattern.compile(message_reg);
		  Matcher matcher_message=pattern_message.matcher(line);
		  if(matcher_message.matches())
		  {
			  System.out.println(line);
		  }
		  else
		  {
			  System.out.println("not match");
		  }
		  String[] fileds=line.split("\\s");
		  System.out.println(Arrays.toString(fileds));
		  String time=fileds[0].split("\\+")[0].replace("T", " ");
		  String senderID=fileds[4].split(":")[1];
		  String recverID=fileds[5].split(":")[1];
		  String messageContent=fileds[7].split(":")[1];
		  
		  System.out.println("time="+time);
		  System.out.println("senderID="+senderID);
		  System.out.println("recverID="+recverID);
		  System.out.println("messageContent="+messageContent);
		  
		 
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		  Date date = sdf.parse(time);
		  long timeUnix= date.getTime();
		  
		
		  System.out.println("timeUnix="+timeUnix);
		  System.out.println("senderID="+senderID);
		  System.out.println("recverID="+recverID);
		  System.out.println("messageContent="+messageContent);
		  
		  
		   String reg_adj="ÄãÊÇ.*((sb)|TM|ÇĞ²Ë|ÄÔ²Ğ|·ÑÇ®|Ç¿|Èõ|²»Æ½ºâ|²»ºÏÀí|²»¹«Æ½|²»Íæ|²»ÍæÁË|Ã»ÒâË¼|²»ºÃÍæ|ÎŞÁÄ|IMBA|BUG|bug|Bug)";
		   
		   String yiren="(((^|±ä|²î|ÌØ|Áé|Àë|Ææ|²ï|¸÷|¹Ö|¹î|´óÍ¬Ğ¡)Òì(²Ê|³£|µØ|ÏëÌì¿ª|ĞÄ|ĞÔ|Ñù|ÓÚ|$))|([ôèÒåÒÇÛİÒÄÒÊÚ±ÒËâùåÆâÂß×ÒÌÜèêİíôÒÈğêÒÆÉßÒÅÒÃÒÉáÚÒÍÒåÒÚß®Ø×ÒäÒÕØî°¬ÒéÒàÒÙÒìÒÂØıß½ÒÛÒÖÒëÒØÙ«á»âøÒ×Ğ¹¸íÒïÒèæäŞÈŞÄÒßôàéóÊ³Éäã¨ŞÚÒæÒêÛüñ´ÒîÒİàÉÒâÒççËÒŞÒáğùòæÒãìÚï×ØæàæéìŞ²ôèÒíÒÜñ¯ïîÜ²ÒÒÒÑÒÔîÆÎ²ÒÓÜÓÒÀåÆô¯ÒÏÒĞôıâ¢ÒÎ¶êì½Ò»ñÂÒÁÒÂÒ½ÒÀßŞâ¢Ò¿Ò¼Ò¾ÒÎäôàæ÷ğ][ÈËØéÈÊÈÉÈÎÈĞÈÏØğÈÎÈÒÈÑéíÈÍâ¿ñÅİØÈÌÜóïşÈÔÜµÈÓ])|yiren|yren|yi)";
		    	
		   String meizhe="((÷È([^Á¦»óÈË]|$))|([Ã»Ã¶ÃµÃ¼İ®Ã·Ã½áÒäØâ­é¹ÃºÃ¸ïÑğÌÃ¹ÃÓÃÃÃÁñÇÃÕÃÄÃÂ÷ÈÃ¿ÃÀä¼Ã¾][×ÅØ±ÕÛÕÜéüÕİòØÚØß¡íİÕŞÕâèÏÕãÕáğÑÕßñÒÕàô÷ñŞÕÛòØÕÚó§])|meizhe|mei|m|mz|gm)";
			

		   Pattern pattern_reg_adj = Pattern.compile(yiren,Pattern.CASE_INSENSITIVE);
		   Matcher matcher_reg_adj=pattern_reg_adj.matcher(line);
		   if(matcher_reg_adj.matches())
		   {
			   System.out.println("match:"+line);
		   }
		   else
			   System.out.println("not match:"+line);
		   
		   
		   String no_adj=".*(?<!ĞĞ)²»ĞĞ(?!Ã´).*";
		   
		   line="ĞĞ²»ĞĞ°¡";
	

		   Pattern pattern_no_adj = Pattern.compile(no_adj,Pattern.CASE_INSENSITIVE);
		   matcher_reg_adj=pattern_no_adj.matcher(line);
		   if(matcher_reg_adj.matches())
		   {
			   System.out.println("match:"+line);
		   }
		   else
			   System.out.println("not match:"+line);
		
		 //  line="Òì¿ÚÍ¬";
		//   String no_ciyu= ".*Òì(?!¿ÚÍ¬Éù).*";
		   
		   //line="w¸÷Òì²Ê¹ş";
		   //line="MZ¹ş";
		   //line="MZ Ò½Ò©¹ş";
		   // line="MZnan·½¹ş";
		   //line="MZnan ÏÀÒå¿Î¹ş";
		   //line="MZnan ±¦µ¶¹ş";
		  // line="MZnan ¼×ÓãJS¹ş";
		   line="MZnan ·¢ÉäJS¹ş";
		   
		   String yiren_reg= ".*(((?<!(±ä|²î|ÌØ|Áé|Àë|Ææ|²ï|¸÷|¹Ö|¹î|´óÍ¬Ğ¡))Òì(?!(²Ê|³£|µØ|ÏëÌì¿ª|ĞÄ|ĞÔ|Ñù|ÓÚ)))|((?<![a-z])yiren(?![a-z]))|((?<![a-z])yr(?![a-z]))|([ôèÒåÒÇÛİÒÄÒÊÚ±ÒËâùåÆâÂß×ÒÌÜèêİíôÒÈğêÒÆÉßÒÅÒÃÒÉáÚÒÍÒåÒÚß®Ø×ÒäÒÕØî°¬ÒéÒàÒÙÒìÒÂØıß½ÒÛÒÖÒëÒØÙ«á»âøÒ×Ğ¹¸íÒïÒèæäŞÈŞÄÒßôàéóÊ³Éäã¨ŞÚÒæÒêÛüñ´ÒîÒİàÉÒâÒççËÒŞÒáğùòæÒãìÚï×ØæàæéìŞ²ôèÒíÒÜñ¯ïîÜ²ÒÒÒÑÒÔîÆÎ²ÒÓÜÓÒÀåÆô¯ÒÏÒĞôıâ¢ÒÎ¶êì½Ò»ñÂÒÁÒÂÒ½ÒÀßŞâ¢Ò¿Ò¼Ò¾ÒÎäôàæ÷ğ][ÈËØéÈÊÈÉÈÎÈĞÈÏØğÈÎÈÒÈÑéíÈÍâ¿ñÅİØÈÌÜóïşÈÔÜµÈÓ])).*";
		   String mei_reg=".*((÷È(?!(Á¦|»ó|ÈË)))|((?<![a-z])meizhe(?![a-z]))|((?<![a-z])mz(?![a-z]))|((?<![a-z])mei(?![a-z]))|((?<![a-z])m(?![a-z]))|([Ã»Ã¶ÃµÃ¼İ®Ã·Ã½áÒäØâ­é¹ÃºÃ¸ïÑğÌÃ¹ÃÓÃÃÃÁñÇÃÕÃÄÃÂ÷ÈÃ¿ÃÀä¼Ã¾][×ÅØ±ÕÛÕÜéüÕİòØÚØß¡íİÕŞÕâèÏÕãÕáğÑÕßñÒÕàô÷ñŞÕÛòØÕÚó§])).*";
		   String yishi_reg=".*(((?<!(¾Í|´Ó|·¨|¾ü|Ãû|Î×|Î÷|Ğ£|ĞĞ|Ó¹|ÑÀ|ÖĞ|ĞÄÀí|Ö÷ÖÎ))Ò½(?!(Ò©|Ôº|ÖÎ|¿Æ|ÁÆ|Öö|Êõ|µÂ|Îñ|Êé)))|((?<![a-z])yishi(?![a-z]))|((?<![a-z])ys(?![a-z]))|((?<![a-z])yi(?![a-z]))|((?<![0-9])1(?![0-9]))|([ôèÒåÒÇÛİÒÄÒÊÚ±ÒËâùåÆâÂß×ÒÌÜèêİíôÒÈğêÒÆÉßÒÅÒÃÒÉáÚÒÍÒåÒÚß®Ø×ÒäÒÕØî°¬ÒéÒàÒÙÒìÒÂØıß½ÒÛÒÖÒëÒØÙ«á»âøÒ×Ğ¹¸íÒïÒèæäŞÈŞÄÒßôàéóÊ³Éäã¨ŞÚÒæÒêÛüñ´ÒîÒİàÉÒâÒççËÒŞÒáğùòæÒãìÚï×ØæàæéìŞ²ôèÒíÒÜñ¯ïîÜ²ÒÒÒÑÒÔîÆÎ²ÒÓÜÓÒÀåÆô¯ÒÏÒĞôıâ¢ÒÎ¶êì½Ò»ÒÁÒÂÒ½ÒÀßŞâ¢Ò¿Ò¼Ò¾ÒÎäôàæ÷ğ][Ê®Ê²Ê¯Ê±Ê¶ÊµÊ°ìÂÊ´Ê³ÛõİªË¶öåÊ¿ÊÏÊÀÊËÊĞÊ¾Ê½ËÆÊÂÊÌÊÆÔóÊÓÊÔÊÎÊÒÖÅÊÑÊÃÊÇÊÁêÛÊÊİªéøÊÅß±ÚÖÊÍÊÈóßÊÄÊÉÊ·Ê¸õ¹Ê¹Ê¼Ê»ÊºÊ¬Ê§Ê¦Ê­Ê«Ê©Ê¨Êª])).*";
		   String fangshi_reg=".*(((?<!(°Ë|±±|³¤|³§|³Ë|´¦|´ó|ºó|µĞ|µØ|¶«|¹Ù|¼º|¾¯|¾ü|Á¢|ÃØ|Ãî|Ä§|ÄÏ|Åä|Æ«|Æ½|Ç°|ÉÏ|ÏÂ|Ò©|ËÄ|Î÷|Ô¶|Õı))·½(?!(·¨|µÊ|°¸|±ã|¿é|¿ò|Ãæ|´ç|ÌÇ|Ïò|³Ì|Î»|Ô²|ĞÎ|ÑÔ|ÕÉ|Õó|ÖÛ)))|((?<![a-z])fangshi(?![a-z]))|((?<![a-z])fs(?![a-z]))|((?<![a-z])fang(?![a-z]))|((?<![a-z])f(?![a-z]))|([·À·»·Á·¿·¾öĞ·Å·Â·Ãáİ·Äô³·½Úú·»·¼èÊîÕ][Ê²Ê¯Ê±Ê¶ÊµÊ°ìÂÊ´Ê³ÛõİªË¶öåÊ¿ÊÏìêÊÀÊËÊĞÊ¾Ê½ËÆÊÂÊÌÊÆÔóÊÓÊÔÊÎÊÒÖÅÊÑÊÃÊÇÊÁêÛÊÊêÈóÂİªéøÊÅîæß±ÚÖÊÍÊÈóßÊÄÊÉó§Ê·Ê¸õ¹Ê¹Ê¼Ê»ÊºÊ¬Ê§Ê¦Ê­Ê«Ê©Ê¨ÊªİéĞêõ§öõ])).*";
		   String xiake_reg=".*(((?<!(´ó|ºÀ|½£|Îä|ÓÎ))ÏÀ(?!(Òå)))|((?<!(º£|Èı|´ó))Ï¿(?!(¹È)))|(ÏÁ(?!(Õ­|°¯|Â·Ïà·ê|Ğ¡|Òå)))|((?<!(³¯|ÔÆ|ÑÌ|Íí|²Ê|×Ï))Ï¼(?!(¹â|¹È)))|((?<!(¶Ô|»ùÎ§))Ïº)|((?<![a-z])xiake(?![a-z]))|((?<![a-z])xk(?![a-z]))|((?<![a-z])xia(?![a-z]))|((?<![a-z])x(?![a-z]))|([¼ĞÏ»ÏÀáòÏ¿èÔÏÁ¼ÙíÌİçåÚÏ¾è¦ğıÏ½Ï¼÷ïÏÅÏÄ»£ÏÃóÁßÈÏºğıÏ¹][¿Î¿Ç¿Èò¤¿É¿Ë¿Ì¿Íã¡ë´æìç¼à¾äÛï¾¿É¿Àá³¿ÊºÇ¿À¿Á¿Âçæ¿ÆéğğâîİòÂîş¿Ãò¤à¾àÀïıñ½¿Åî§¿Äòò÷Á])).*";
		   String daoke_reg=".*(((?<!(°Î|±¦|±ù|²Ë|²Ù|²ñ|´Ì|ï±|´ó|ÌêĞë|°Ñ|¸Ö|¹Îºú|¼â|¼ô|½è|¾ü|¿ª|¿Ì|¿ì|Á­|²å|ÂİË¿|Âí|Ä¥|Å£|Åå|Ç§|ÊÖÊõ|Ë®¹û|Ìê|ÌêÍ·|ÌêĞë|ÍÀ|Ğ¦Àï²Ø|ĞÄÈç|Ñü|Ñı|Õ¢|Õ¡|Õ½|Ö¸»Ó))µ¶(?!(ÇĞ|Á½¶Ï|À«¸«|¶¹|Æ¬|²æ|·¨|·æ|¸«ÊÖ|¸û»ğÖÖ|¹â½£Ó°|¼Ü|¾ß|¿Ú|Ç¹|ÈĞ|É½|ÉË|Ï÷Ãæ|×Ó)))|((?<![a-z])daoke(?![a-z]))|((?<![a-z])dk(?![a-z]))|((?<![a-z])dao(?![a-z]))|((?<![a-z])d(?![a-z]))|([µ½µ¹àüµ¿ìâµÁµÀµ¾ôîµ¼µºµ¹µ·µ»µ¸µ¶ß¶âáë®][¿Ç¿Èò¤¿É¿Ë¿Ì¿Íã¡ë´æìç¼à¾äÛï¾¿É¿Àá³¿Î¿ÊºÇ¿À¿Á¿Âçæ¿ÆéğğâîİòÂîş¿Ãò¤à¾àÀïıñ½¿Åî§¿Äòò÷Á])).*";
		   String jiashi_reg=".*(((?<!(±£|±¦|´©É½|¶ª¿øÆú|»¨|Ö¸|îø|¿ø|ÁÛ|Âí|Åû|Éí»³Áù|Ìú|Öº|×°))¼×(?!(×´ÏÙ|°å|³æ|´¼|¸Î|¹ÇÎÄ|¿º|ëĞ|¹éÌï|²»Áô)))|((?<![a-z])jiashi(?![a-z]))|((?<![a-z])js(?![a-z]))|((?<![a-z])jia(?![a-z]))|((?<![a-z])j(?![a-z]))|([¼Ò¼×¼ĞÛ£Ş×¼Ôí¢ê©ñÊîòòÌò¡¼Õ¼Û¼İ¼Ü¼Ö¼Ù¼Ş¼Ú¼×áµëÎ¼Ö¼Ø¼ÙØÅğı¼Ó¼ĞÙ¤¼ÑÇÑåÈĞ®¼Ïä¤çìÛÁ¼ÒğèóÕôÂİçõÊ¼ÎïØ][Ê²Ê¯Ê±Ê¶ÊµÊ°ìÂÊ´Ê³ÛõİªË¶öåÊ¿ÊÏìêÊÀÊËÊĞÊ¾Ê½ËÆÊÂÊÌÊÆÔóÊÓÊÔÊÎÊÒÖÅÊÑÊÃÊÇÊÁêÛÊÊêÈóÂİªéøÊÅîæß±ÚÖÊÍÊÈóßÊÄÊÉó§Ê·Ê¸õ¹Ê¹Ê¼Ê»ÊºÊ¬Ê§Ê¦Ê­Ê«Ê©Ê¨ÊªİéĞêõ§öõ])).*";
		   String sheshou_reg=".*(((?<!(µã|·¢|·´|·Å|·ø|º¬É³|Åç|É¢|É¨|ÉÁ|ËÄ|µ¯|Í¶|Ó°|Ó³|ÕÕ|ÕÛ|×¢))Éä(?!(Ïß|ÃÅ|Æµ|Èë|É±|Ïß|³Ì|»÷|¼ı|¾«|ÁÔ)))|((?<!(¿ª|Íä|¾ª|µ¯|±­))¹­(?!(Ñü|ÏÒ|åóÊÖ|±³|ĞÎ)))|((?<![a-z])sheshou(?![a-z]))|((?<![a-z])gongjian(?![a-z]))|((?<![a-z])ss(?![a-z]))|((?<![a-z])gj(?![a-z]))|((?<![a-z])she(?![a-z]))|((?<![a-z])gong(?![a-z]))|((?<![a-z])s(?![a-z]))|([ÉàÙÜÕÛÉßŞéÒ¶ØÇÉèÉçÉáÊ°ÉäÉæÉâÉåÉãäÜì¨÷êÉáÉİâ¦ÉŞî´][ÊÙÊÜá÷ÊŞÊÛÊÚç·ÊİÊÖÊØÊ×ô¼ÊÕ])).*";
		 
		   //String no_d="\\d{3}(?!abc)\\w";
		  //  line= "123a";
		   
		   Pattern pattern_no_ciyu = Pattern.compile(sheshou_reg,Pattern.CASE_INSENSITIVE);
		   matcher_reg_adj=pattern_no_ciyu.matcher(line);
		   
		   if(matcher_reg_adj.matches())
		   {
			   System.out.println("match:"+line);
		   }
		   else
			   System.out.println("not match:"+line);
	
	}
}
