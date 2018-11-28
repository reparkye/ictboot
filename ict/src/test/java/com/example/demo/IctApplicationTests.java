package com.example.demo;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.collection.NaverITNews;
import com.example.demo.repository.NaverItNewsRepository;

import lombok.extern.slf4j.Slf4j;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class IctApplicationTests {
	static final String CONNECT_URI = "https://news.naver.com/main/list.nhn?mode=LS2D&mid=shm&sid1=105&sid2=230";
	static final String SELECT_QUERY = "ul.type06_headline";
	
	/*@Data
	public class NaverITNews{
		private String uri;
		private String tittle;
		private String aid;
		
	}*/
	
	@Autowired
	private NaverItNewsRepository nitnRepo;
	
	
	@Test
	public void contextLoads() throws IOException{
		Document doc = Jsoup.connect(CONNECT_URI).get();
		Elements ulElements = doc.select(SELECT_QUERY);
		assertEquals(1,ulElements.size());
		log.info("ul size=>{}",ulElements.size());
	}
	
	@Test
	public void selectLis() throws IOException {
		Document doc = Jsoup.connect(CONNECT_URI).get();
		Elements ulElements = doc.select(SELECT_QUERY);
		Elements aElements = ulElements.select("li>dl>dt:not(.photo)>a");
		//log.info("dt size=>{}",dtElements.size());
		assertEquals(10,aElements.size());
		List<NaverITNews> nitnList = new ArrayList<NaverITNews>();
		for(Element aElement : aElements) {
			String uri  = aElement.attr("href");
			int sIdx = uri.indexOf("aid=");
			String aid = uri.substring(sIdx) + "&abc";
			if(aid.indexOf("&")!=-1) {
				aid = aid.substring(0,aid.indexOf("&"));
			}
			aid = aid.replace("aid=","");
			log.info("aid=>{}",aid);
			
			String tittle = aElement.text();
			NaverITNews nitn = new NaverITNews();
			nitn.setAid(aid);
			nitn.setUri(uri);
			nitn.setTittle(tittle);
			nitnList.add(nitn);
		}
		
		nitnRepo.saveAll(nitnList);
	}
}
