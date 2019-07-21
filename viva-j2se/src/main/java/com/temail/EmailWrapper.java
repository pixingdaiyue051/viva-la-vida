package com.temail;

import org.apache.commons.mail.EmailAttachment;

import java.util.List;

public class EmailWrapper {

    public static EmailInfoSender email(EmailInfoEnum emailInfoEnum, String msg) {
        EmailInfoSender emailInfoSender = new EmailInfoSender();
        emailInfoSender.setSubject(emailInfoEnum.getSubject());
        emailInfoSender.setToEmail(emailInfoEnum.getToEmail());
        emailInfoSender.setToName(emailInfoEnum.getToName());
        emailInfoSender.setEmailMsg(msg);
        return emailInfoSender;
    }

    public static EmailInfoSender email(EmailInfoEnum emailInfoEnum, String msg, List<EmailAttachment> attachmentList) {
        EmailInfoSender emailInfoSender = email(emailInfoEnum, msg);
        emailInfoSender.setAttachments(attachmentList);
        return emailInfoSender;
    }
}