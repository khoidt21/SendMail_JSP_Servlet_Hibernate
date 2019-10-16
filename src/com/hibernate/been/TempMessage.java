
package com.hibernate.been;

import java.util.Date;

public class TempMessage {
    String subject, from, to, content;
    Date date;
    int idx;
    
    public TempMessage(String subject, String from, String to, String content, Date date, int idx) {
        this.subject = subject;
        this.from = from;
        this.to = to;
        this.content = content;
        this.date = date;
        this.idx = idx;
    }

    public int getIdx(){
        return idx;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
