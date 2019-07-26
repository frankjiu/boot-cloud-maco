package com.mail;

import java.io.Serializable;

import lombok.Data;

/**
 * 发送邮件-封装接受者信息
 */
@Data
public class MailBean implements Serializable {
   
	private static final long serialVersionUID = 1L;
	private String receipt; //邮件接收人
    private String subject; //邮件主题
    private String content; //邮件内容

}