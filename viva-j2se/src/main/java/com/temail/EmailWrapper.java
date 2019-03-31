package com.temail;

import org.apache.commons.mail.EmailAttachment;

import java.util.List;

public class EmailWrapper {

    private String subject;// 邮件主题，必须
    private String toEmail;// 邮件地址，必须
    private String toName;// 收件人姓名，非必须
    private String emailMsg;// 邮件内容，可以是html信息，必须
    private List<EmailAttachment> attachments;// 附件，非必须

    public EmailWrapper() {
    }

    public EmailWrapper(String subject, String toEmail, String toName, String emailMsg) {
        this.subject = subject;
        this.toEmail = toEmail;
        this.toName = toName;
        this.emailMsg = emailMsg;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getEmailMsg() {
        return emailMsg;
    }

    public void setEmailMsg(String emailMsg) {
        this.emailMsg = emailMsg;
    }

    public List<EmailAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<EmailAttachment> attachments) {
        this.attachments = attachments;
    }
}
