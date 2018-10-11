package com.shushan.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

public class BeanUtil {
	 private static final String APP_CFG = "applicationContext.xml";
	 private static ApplicationContext ac;
	 static{
		 try{
			 ac = ContextLoader.getCurrentWebApplicationContext();
			 if (ac == null) {
				 ac = new ClassPathXmlApplicationContext(APP_CFG);
			 }
		 }catch (Exception e){
			 e.printStackTrace();
		 }
	 }
	 public static Object getBean(String beanName){
	 	return ac.getBean(beanName);
	 }
	 @SuppressWarnings("unchecked")
	 public static Object getBean(Class beanClass){
	 	return ac.getBean(beanClass);
 	}
}
