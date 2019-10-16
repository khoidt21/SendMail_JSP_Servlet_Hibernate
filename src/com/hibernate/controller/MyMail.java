/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hibernate.controller;

import com.sun.mail.imap.IMAPFolder;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.hibernate.been.MailMessage;
import com.hibernate.been.TempMessage;


public class MyMail {

    final String userMail = "";//Them Email o day
    final String passMail = "";//Them Password o day
    private static MyMail instance;
    final int MAILS_PER_PAGE = 4;
    private static int numPages;
    private static TempMessage[] tm;

    private MyMail() {
    }

    public static MyMail getInstance() {
        if (instance == null) {
            instance = new MyMail();
        }
        return instance;
    }

    public void send(MailMessage mm) throws AddressException, MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userMail, passMail);
            }
        });

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(userMail));

        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mm.getTo()));
        message.setRecipients(Message.RecipientType.CC,InternetAddress.parse(mm.getCc()));

        message.setSubject(mm.getSubject());

        message.setContent(mm.getContent(),"text/html; charset=utf-8");

        Transport.send(message);
    }

    public void traceEmails(int traceIndex, String traceType) throws MessagingException, Exception {
        
        IMAPFolder folder = null;
        Store store = null;
        String subject = null;
        Flags.Flag flag = null;
        try {
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);

            store = session.getStore("imaps");
            store.connect("imap.googlemail.com", userMail, passMail);

            folder = (IMAPFolder) store.getFolder("INBOX");

            if (!folder.isOpen()) {
                folder.open(Folder.READ_ONLY);
            }
            Address a;
            Message[] mm;
            mm = folder.getMessages();
            if(traceType.equalsIgnoreCase("page")){
                getEmailsByPage(mm, traceIndex);
            } else {
                getEmailByIdx(mm, traceIndex);
            }
        } catch (MessagingException ex) {
            Logger.getLogger(MyMail.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (folder != null && folder.isOpen()) {
                folder.close(true);
            }
            if (store != null) {
                store.close();
            }
        }
    }

    private void getEmailsByPage(Message[] mm, int idxPage) throws MessagingException, Exception {
        if(mm == null || mm.length == 0) return;
        int len = mm.length;
        int noFrom, noTo;
        numPages = len / MAILS_PER_PAGE;
        if(len % MAILS_PER_PAGE > 0) {
            numPages += 1;
        }
        if(idxPage > 0) idxPage--;
        if(idxPage < 0) idxPage = 0;
        if (numPages >= idxPage) {
            noFrom = len - (MAILS_PER_PAGE * idxPage + 1); // --> len - 10
            noTo = len - (MAILS_PER_PAGE * idxPage + MAILS_PER_PAGE);

        } else {
            noFrom = len - 1;
            noTo = len - MAILS_PER_PAGE;
        }
        if (noTo < 0) {
            noTo = 0;
        }
       
        tm = new TempMessage[noFrom - noTo + 1];
        String subject, from, to, content;
        Date date;
        int sizeChars;
        for (int i = noFrom; i >= noTo; i--) {
            subject = mm[i].getSubject();
            from = mm[i].getFrom()[0].toString();
            to = mm[i].getAllRecipients()[0].toString();
            content = getContentMessage(mm[i]);
            date = mm[i].getReceivedDate();
            sizeChars = mm[i].getSize();
            
            tm[i-noTo] = new TempMessage(mm[i].getSubject(),
                    mm[i].getFrom()[0].toString(),
                    mm[i].getAllRecipients()[0].toString(),
                    "none",
                    mm[i].getReceivedDate(),
                    i);
        }
    }

    private void getEmailByIdx(Message[] mm, int i) throws IOException, MessagingException, Exception {
        
        if(mm == null) return;
        
        tm = new TempMessage[1];
        tm[0] = new TempMessage(mm[i].getSubject(),
                    mm[i].getFrom()[0].toString(),
                    mm[i].getAllRecipients()[0].toString(),
                    getContentMessage(mm[i]),
                    mm[i].getReceivedDate(),
                    i);
    }

    public static int getNumPages() {
        return numPages;
    }

    public static TempMessage[] getTm() {
        return tm;
    }
    
    //Lay ra noi dung cua tin nhan
    public String getContentMessage(Part p) throws Exception {
        String result = "";

        if (p.isMimeType("text/plain")) {
            result = (String) p.getContent();
        } 
        else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++) {
                result += getContentMessage(mp.getBodyPart(i)) + "\n";
            }
        }
        else if (p.isMimeType("message/rfc822")) {
            result += getContentMessage((Part) p.getContent());
        } else {
            Object o = p.getContent();
            if (o instanceof String) {
                result += (String) o + "\n";
            } else {
                result += o.toString();
            }
        }
        return result;
    }
}
