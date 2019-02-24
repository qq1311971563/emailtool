package com.ant.tool;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailTool {


    public EmailTool(String sender, String password) {
        this.sender = sender;
        this.password = password;
    }

    private String sender;
    private String password;
    private String mailHost = "smtp.163.com";
    private String mailTransportProtocol = "smtp";
    private String mailSmtpAuth = "true";

    /**
     * 发送邮件
     *
     * @param receiver       邮件接收人
     * @param messageTitle   邮件标题
     * @param messageContent 邮件内容
     * @return true 发送成功 false 发送失败
     * @throws Exception
     */
    public boolean sendEmail(String receiver, String messageTitle, String messageContent) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.host", mailHost);
        prop.setProperty("mail.transport.protocol", mailTransportProtocol);
        prop.setProperty("mail.smtp.auth", mailSmtpAuth);
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(mailHost, sender, password);
        //4、创建邮件
        Message message = createSimpleMail(session, receiver, messageTitle, messageContent);
        //5、发送邮件
        try {
            ts.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        ts.close();
        return true;
    }

    private MimeMessage createSimpleMail(Session session, String receiver, String messageTitle, String messageContent)
            throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(sender));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        //邮件的标题
        message.setSubject(messageTitle);
        //邮件的文本内容
        message.setContent(messageContent, "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }
}
