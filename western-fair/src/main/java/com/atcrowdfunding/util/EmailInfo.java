package com.atcrowdfunding.util;

import java.util.Properties;

/**
 * Created by Nicky on 2017/11/12.
 */
public class EmailInfo {
    //邮件发�?�服务器Host
    private String mailServerHost;
    //邮件发�?�服务器端口
    private String mailServerPort="25";
    //邮件发�?��?�的地址
    private String mailFormAddress;
    //邮件接收者的地址
    private String mailToAddress;
    //邮件发�?�服务器的用户名
    private String username;
    //�?启SMTP服务申请的独立密�?
    private String password;
    //是否�?要身份验�?
    private boolean validate;
    //邮件主题
    private String subject;
    //邮件的文本内�?
    private String content;
    //邮件附件的文件名
    private String[] attachFileNames;

    public Properties getProperties(){
        Properties pro=new Properties();
        pro.put("mail.smtp.host",this.mailServerHost);
        pro.put("mail.smtp.port",this.mailServerPort);
        pro.put("mail.smtp.auth",validate?"true":"false");
        return pro;
    }

    public String getMailServerHost() {
        return mailServerHost;
    }

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public String getMailFormAddress() {
        return mailFormAddress;
    }

    public void setMailFormAddress(String mailFormAddress) {
        this.mailFormAddress = mailFormAddress;
    }

    public String getMailToAddress() {
        return mailToAddress;
    }

    public void setMailToAddress(String mailToAddress) {
        this.mailToAddress = mailToAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }

    public void setAttachFileNames(String[] attachFileNames) {
        this.attachFileNames = attachFileNames;
    }
}
