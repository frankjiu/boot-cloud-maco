package com.mail;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * 邮件发送
 * @author xinbe
 *
 */
@Controller
@RequestMapping("/mail")
public class SendMail {
	
	private final static Logger logger = LoggerFactory.getLogger(SendMail.class);
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Value("${MAIL_SENDER}")
	private String MAIL_SENDER;
	
	/**
	 * HTML邮件发送
	 * @param mailBean
	 */
	@RequestMapping("/send")
	@ResponseBody
	public Object sendHTMLMail(MailBean mailBean) {
		
		//设置测试邮件参数
		mailBean.setReceipt("2309094456@qq.com");
		mailBean.setSubject("springboot的mail发送测试");
		mailBean.setContent(".................springboot测试正文...................");
		
        try {
        	MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(MAIL_SENDER);
            mimeMessageHelper.setTo(mailBean.getReceipt());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            StringBuilder stb = new StringBuilder();
            stb.append("<h1>SpirngBoot测试邮件HTML</h1>")
                    .append("<p style='color:#F00'>你是真的太棒了!</p>")
                    .append("<p style='text-align:right'>右对齐</p>");
            mimeMessageHelper.setText(stb.toString(), true);
            javaMailSender.send(mimeMailMessage);
            return "发送成功!";
        } catch (Exception e) {
            logger.error("邮件发送失败", e.getMessage());
            return "发送失败!";
        }
    }
	
	/**
	 * Thymeleaf模板邮件发送
	 */
	@Autowired
    private TemplateEngine templateEngine;

    @RequestMapping("/sendThy")
	@ResponseBody
    public String sendTemplateMail(HttpServletRequest request, MailBean mailBean) throws Exception{
		try {
			// 设置测试邮件参数
			mailBean.setReceipt("2309094456@qq.com");
			mailBean.setSubject("通过springboot发送的thymeleaf模板邮件");
			// 创建邮件正文
			Context context = new Context();
			context.setVariable("userName", "Frankjiu");
			request.setAttribute("userName", "jiujiu");
			// 获取thymeleaf的html模板
			String emailContent = templateEngine.process("mail_thymeleaf_template", context);
			// 封装邮件信息
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(MAIL_SENDER);
			helper.setTo(mailBean.getReceipt());
			helper.setSubject(mailBean.getSubject());
			helper.setText(emailContent, true);
			javaMailSender.send(message);
			return "模板邮件发送成功!";
		} catch (Exception e) {
			logger.error("模板邮件发送失败...", e.getMessage());
			return "模板邮件发送失败!";
		}
    }

	

}
