package com.zhongli.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.zhongli.dao.TwetDAO;
import com.zhongli.model.TwetMsg;

public class App {

	public static void main(String[] args) {
		ApplicationContext context = new FileSystemXmlApplicationContext(
				"WebContent/WEB-INF/test-servlet.xml");

		TwetDAO twitterDAO = (TwetDAO) context.getBean("twetDAO");
		String MSG="@_nickysnickers	@Chelly_Mariex3 😘😘😘😍😍😍❤️❤️❤️🎉🎉🎉🎉👏👏👏👏	Sat May 02 16:37:12 EDT 2015	40.770695,-73.901547	und";
		// TwetMsg tMsg = new TwetMsg("@ernesteyyyn",
		// "Yesterday’s sunset 😍❤️ #nofilter @ Puerto de San Juan La Union https://t.co/4WDdYmLDZ9",
		// "Fri May 01 23:28:05 EDT 2015",16.670112, 120.333139, "es");
//		TwetMsg tMsg = new TwetMsg(MSG);
//		twitterDAO.insert(tMsg);
		TwetMsg tMsg = twitterDAO.findByID(6);
		System.out.println(tMsg);
	}
}
