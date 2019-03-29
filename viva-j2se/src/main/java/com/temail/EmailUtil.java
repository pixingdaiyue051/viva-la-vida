package com.temail;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtil {

    private final static boolean ssl = true;
    private final static String HOST_NAME = "smtp.163.com";
    private final static String SSL_PORT = "465";
    private final static String USER_NAME = "pixingdaiyue051@163.com";
    private final static String PASSWORD = "pixingdaiyue051";
    private final static String CHARSET = "UTF-8";

    public static boolean send(EmailWrapper wrapper) {
        try {
            if (StringUtils.isBlank(wrapper.getToEmail())) {
                return false;
            }
            if (StringUtils.isBlank(wrapper.getEmailMsg())) {
                return false;
            }
            HtmlEmail email = new HtmlEmail();
            email.setCharset(CHARSET);
            email.setSSL(ssl);
            email.setHostName(HOST_NAME);
            email.setSslSmtpPort(SSL_PORT);
            email.setAuthentication(USER_NAME, PASSWORD);
            email.setFrom(USER_NAME, USER_NAME);
            if (StringUtils.isNotBlank(wrapper.getToName())) {
                email.addTo(wrapper.getToEmail(), wrapper.getToName());
            } else {
                email.addTo(wrapper.getToEmail());
            }
            if (StringUtils.isNotBlank(wrapper.getSubject())) {
                email.setSubject(wrapper.getSubject());
            }
            email.setHtmlMsg(wrapper.getEmailMsg());
            if (wrapper.getAttachments() != null && !wrapper.getAttachments().isEmpty()) {
                wrapper.getAttachments().stream().forEach((emailAttachment) -> {
                    try {
                        emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
                        email.attach(emailAttachment);
                    } catch (EmailException e) {
                        e.printStackTrace();
                    }
                });
            }
            email.send();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean send(String emailTo, String subject, String msg) {
        try {
            SimpleEmail email = new SimpleEmail();
            email.setCharset(CHARSET);
            email.setSSL(ssl);
            email.setHostName(HOST_NAME);
            email.setSslSmtpPort(SSL_PORT);
            email.setAuthentication(USER_NAME, PASSWORD);
            email.setFrom(USER_NAME, USER_NAME);
            email.addTo(emailTo);
            email.setSubject(subject);
            email.setMsg(msg);
            email.send();
            return true;
        } catch (EmailException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendHtmlMsg(String emailTo, String subject, String htmlMsg, String filePath, String fileName) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setCharset(CHARSET);
            email.setSSL(ssl);
            email.setHostName(HOST_NAME);
            email.setSslSmtpPort(SSL_PORT);
            email.setAuthentication(USER_NAME, PASSWORD);
            email.setFrom(USER_NAME);
            email.addTo(emailTo);
            email.setSubject(subject);
            email.setHtmlMsg(htmlMsg);
            if (StringUtils.isNotBlank(filePath)) {
                EmailAttachment emailAttachment = new EmailAttachment();
                emailAttachment.setPath(filePath);
                emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
                emailAttachment.setName(fileName);
                email.attach(emailAttachment);
            }
            email.send();
            return true;
        } catch (EmailException e) {
            e.printStackTrace();
            return false;
        }
    }
}
