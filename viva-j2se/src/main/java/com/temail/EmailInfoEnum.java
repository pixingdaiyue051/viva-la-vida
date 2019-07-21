package com.temail;

public enum EmailInfoEnum {
    TEST("test", "", "");

    private String subject;
    private String toEmail;
    private String toName;

    EmailInfoEnum(String subject, String toEmail, String toName) {
        this.subject = subject;
        this.toEmail = toEmail;
        this.toName = toName;
    }

    public String getSubject() {
        return subject;
    }

    public String getToEmail() {
        return toEmail;
    }

    public String getToName() {
        return toName;
    }
}