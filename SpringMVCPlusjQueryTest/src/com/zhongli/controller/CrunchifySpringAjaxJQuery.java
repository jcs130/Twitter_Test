package com.zhongli.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhongli.dao.TwetDAO;
import com.zhongli.model.TwetMsg;

@Controller
public class CrunchifySpringAjaxJQuery {
 
    @RequestMapping("/ajax")
    public ModelAndView helloAjaxTest() {
        return new ModelAndView("ajax", "message", "Twitter on Google Map DEMO...");
    }
 
//    @RequestMapping(value = "/ajaxtest", method = RequestMethod.GET)
//    public @ResponseBody
//    String getTime() {
//        Random rand = new Random();
//        float r = rand.nextFloat() * 100;
//        String result = "<br>Next Random # is <b>" + r + "</b>. Generated on <b>" + new Date().toString() + "</b>";
//        System.out.println("Debug Message from CrunchifySpringAjaxJQuery Controller.." + new Date().toString());
//
//    	
//		
//        return result;
//    }
    @RequestMapping(value = "/ajaxgetloc", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
    public @ResponseBody
    String getNewLoc() {
    	ObjectMapper mapper = new ObjectMapper();
    	ApplicationContext context = new ClassPathXmlApplicationContext("DBSetting.xml");
		TwetDAO twitterDAO = (TwetDAO) context.getBean("twetDAO");
		TwetMsg tMsg = twitterDAO.findByMaxID();
		ShowMsg sMsg = new ShowMsg(tMsg);
		
		
		try {
			String json= mapper.writeValueAsString(sMsg);
			System.out.println(json);
			return json;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
}