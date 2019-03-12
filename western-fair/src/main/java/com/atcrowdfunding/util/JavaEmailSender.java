package com.atcrowdfunding.util;


import com.sun.mail.util.MailSSLSocketFactory;

import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class JavaEmailSender {

        public boolean sendTextMail(EmailInfo emailinfo)throws Exception{
                MyAuthenticator auhenticator = null;
                Properties pro = emailinfo.getProperties();
                //如果�?要身份认证，则创建一个密码验证器
                if(emailinfo.isValidate()){
                        auhenticator = new MyAuthenticator(emailinfo.getUsername(),emailinfo.getPassword());
                }
                //根据邮件会话属�?�和密码验证器构造一个发送邮件的session
                Session session = Session.getDefaultInstance(pro,auhenticator);
                //根据Session创建�?个邮件消�?
                Message message = new MimeMessage(session);
                //创建邮件发�?��?�地�?
                Address fromAddress = new InternetAddress(emailinfo.getMailFormAddress());
                //设置邮件消息的发送�??
                message.setFrom(fromAddress);
                //创建邮件接收者地�?
                Address toAddress = new InternetAddress(emailinfo.getMailToAddress());
                //设置邮件消息的接收�??
                message.setRecipient(Message.RecipientType.TO,toAddress);
                //设置邮件发�?�时�?
                message.setSentDate(new Date());
                // 设置邮件消息的主�?
                message.setSubject(emailinfo.getSubject());
                // 设置邮件消息发�?�的时间
                message.setSentDate(new Date());
                // 设置邮件消息的主要内�?
                String mailContent = emailinfo.getContent();
                message.setText(mailContent);
                // 发�?�邮�?
                Transport.send(message);
                return true;
        }

        public boolean sendHtmlMail(EmailInfo emailinfo)throws Exception{
                MyAuthenticator auhenticator = null;
                Properties pro = emailinfo.getProperties();
                //如果�?要身份认证，则创建一个密码验证器
                if(emailinfo.isValidate()){
                        auhenticator = new MyAuthenticator(emailinfo.getUsername(),emailinfo.getPassword());
                }
                //根据邮件会话属�?�和密码验证器构造一个发送邮件的session
                Session session = Session.getDefaultInstance(pro,auhenticator);
                //根据Session创建�?个邮件消�?
                Message message = new MimeMessage(session);
                //创建邮件发�?��?�地�?
                Address fromAddress = new InternetAddress(emailinfo.getMailFormAddress());
                //设置邮件消息的发送�??
                message.setFrom(fromAddress);
                //创建邮件接收者地�?
                Address toAddress = new InternetAddress(emailinfo.getMailToAddress());
                //设置邮件消息的接收�??
                message.setRecipient(Message.RecipientType.TO,toAddress);
                //设置邮件发�?�时�?
                message.setSentDate(new Date());
                // MiniMultipart类是�?个容器类，包含MimeBodyPart类型的对�?
                Multipart multiPart = new MimeMultipart();
                // 创建�?个包含HTML内容的MimeBodyPart
                BodyPart html = new MimeBodyPart();
                // 设置HTML内容
                html.setContent(emailinfo.getContent(), "text/html; charset=utf-8");
                multiPart.addBodyPart(html);
                // 将MiniMultipart对象设置为邮件内�?
                message.setContent(multiPart);
                // 发�?�邮�?
                Transport.send(message);
                return true;
        }


        public static void sendEmail(String toEmailAddress, String emailTitle, String emailContent) throws Exception {
                Properties props = new Properties();

                // �?启debug调试
                props.setProperty("mail.debug", "true");
                // 发�?�服务器�?要身份验�?
                props.setProperty("mail.smtp.auth", "true");
                // 设置邮件服务器主机名
                props.setProperty("mail.host", "smtp.qq.com");
                // 发�?�邮件协议名�?
                props.setProperty("mail.transport.protocol", "smtp");

                MailSSLSocketFactory sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
                props.put("mail.smtp.ssl.enable", "true");
                props.put("mail.smtp.ssl.socketFactory", sf);

                Session session = Session.getInstance(props);

                Message msg = new MimeMessage(session);
                msg.setSubject(emailTitle);
                StringBuilder builder = new StringBuilder();
                builder.append("url = " + "http://blog.csdn.net/");
                builder.append("\n" + emailContent);
                builder.append("\n时间 " + new Date());
                msg.setText(builder.toString());
                msg.setFrom(new InternetAddress("1909318916@qq.com"));

                Transport transport = session.getTransport();
                transport.connect("smtp.qq.com", "1909318916@qq.com", "hlvsxrwvjpqdciai");

                transport.sendMessage(msg, new Address[]{new InternetAddress(toEmailAddress)});
                transport.close();
        }


}
