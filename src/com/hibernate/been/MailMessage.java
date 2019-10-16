package com.hibernate.been;


public class MailMessage {

    private String to;
    private String cc;
    private String subject;
    private String content;
    private String error;

    public MailMessage(String to, String cc, String subject, String content) {
        this.to = to;
        this.cc = cc;
        this.subject = subject;
        this.content = content;
    }

    public MailMessage() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTo() {
        return to;
    }

    public String getCc() {
        return cc;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

}
