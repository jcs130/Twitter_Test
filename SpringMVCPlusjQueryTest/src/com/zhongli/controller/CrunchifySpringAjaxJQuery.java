package com.zhongli.controller;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;



import com.zhongli.NLPPart.alchemyapi.api.AlchemyAPI;
import com.zhongli.dao.TwetDAO;
import com.zhongli.model.ShowMsg;
import com.zhongli.model.TwetMsg;

@Controller
public class CrunchifySpringAjaxJQuery {

	private int oldID = 0;

	@RequestMapping("/ajax")
	public ModelAndView helloAjaxTest() {
		return new ModelAndView("ajax", "message",
				"Twitter on Google Map DEMO...");
	}

	// @RequestMapping(value = "/ajaxtest", method = RequestMethod.GET)
	// public @ResponseBody
	// String getTime() {
	// Random rand = new Random();
	// float r = rand.nextFloat() * 100;
	// String result = "<br>Next Random # is <b>" + r + "</b>. Generated on <b>"
	// + new Date().toString() + "</b>";
	// System.out.println("Debug Message from CrunchifySpringAjaxJQuery Controller.."
	// + new Date().toString());
	//
	//
	//
	// return result;
	// }
	@RequestMapping(value = "/ajaxgetloc", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public @ResponseBody String getNewLoc() {
	//	ObjectMapper mapper = new ObjectMapper();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"DBSetting.xml");
		TwetDAO twitterDAO = (TwetDAO) context.getBean("twetDAO");
		TwetMsg tMsg = twitterDAO.findByMaxID();
		ShowMsg sMsg = new ShowMsg(tMsg);
		// 如果没有情绪标记则取得标记
		if (tMsg.getId() != oldID) {
			// 判断语言并调用情感判断的API判断情感色彩
			if (tMsg.getLanguage().equals("en")) {
				AlchemyAPI alchemyObj = AlchemyAPI
						.GetInstanceFromString("b232c9bbb50818d45e1ecd2f14ea0bc47bdea8d1");
				try {
					Document doc = alchemyObj.TextGetTextSentiment(tMsg
							.getMsg());
					System.out.println(getStringFromDocument(doc));
					// 使用 DOM解析返回的XML文档
					String emotion = doc.getElementsByTagName("type").item(0)
							.getTextContent();
					double score;
					if (emotion.equals("neutral")) {
						score=0;
					} else {
						score = Double.parseDouble(doc
								.getElementsByTagName("score").item(0)
								.getTextContent());
					}
					sMsg.setEmotion(emotion);
					twitterDAO.updateEmotion(tMsg.getId(), emotion, score);
				} catch (XPathExpressionException | IOException | SAXException
						| ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println(tMsg.getLanguage() + "不是英语");
			}
			oldID = tMsg.getId();
		} else {
			System.out.println("重复句子");
		}
			//String json = mapper.writeValueAsString(sMsg);
			JSONObject json = JSONObject.fromObject(sMsg);
			System.out.println(json);
			return json.toString();

	}

	private static String getStringFromDocument(Document doc) {
		try {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);

			return writer.toString();
		} catch (TransformerException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}