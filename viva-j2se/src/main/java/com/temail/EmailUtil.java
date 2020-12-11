package com.temail;

import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {

    public static EmailRespone sendMimeEmail(EmailWrapper wrapper) {
        EmailRespone respone = new EmailRespone();
        try {
            // 检查
            checkEmailWrapper(wrapper);
            // 创建session，开启邮件会话
            Session session = Session.getInstance(EmailUtilHolder.getProperties());
            // 加载mime邮件
            MimeMessage message = new MimeMessage(session);
            // 设置发件人
            Address addr = new InternetAddress(EmailUtilHolder.FROM, EmailUtilHolder.USER_NAME, EmailUtilHolder.CHARSET);
            message.setFrom(addr);
            // 设置标题
            message.setSubject(wrapper.getSubject(), EmailUtilHolder.CHARSET);
            // 设置收件人
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(wrapper.getToAddr()));
            // 设置发件时间
            message.setSentDate(new Date());
            // 设置抄送人
            if (StringUtils.isNotBlank(wrapper.getCcAddr())) {
                message.setRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(wrapper.getCcAddr()));
            }
            // 设置密送人
            if (StringUtils.isNotBlank(wrapper.getBccAddr())) {
                message.setRecipients(MimeMessage.RecipientType.BCC, InternetAddress.parse(wrapper.getBccAddr()));
            }
            // 邮件内容，包含内容，附件等
            MimeMultipart mp = new MimeMultipart();
            // 设置邮件内容
            MimeBodyPart bp = new MimeBodyPart();
            bp.setContent(wrapper.getHtmlMsg(), "text/html;charset=" + EmailUtilHolder.CHARSET);
            mp.addBodyPart(bp);
            // 设置附件
            if (null != wrapper.getFileList() && !wrapper.getFileList().isEmpty()) {
                wrapper.getFileList().forEach(file -> {
                    try {
                        MimeBodyPart filePart = new MimeBodyPart();
                        FileDataSource dataSource = new FileDataSource(file);
                        DataHandler dataHandler = new DataHandler(dataSource);
                        // 文件处理
                        filePart.setDataHandler(dataHandler);
                        // 附件名称,处理中文乱码
                        String fileName = MimeUtility.encodeText(file.getName());
                        filePart.setFileName(fileName);
                        // 放入正文（有先后顺序，所以在正文后面）
                        mp.addBodyPart(filePart);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            message.setContent(mp);
            // 保存邮件
            message.saveChanges();
            // 创建连接对象
            Transport transport = session.getTransport();
            // 建立连接(邮件地址、授权码)
            transport.connect(EmailUtilHolder.FROM, EmailUtilHolder.PASSWORD);
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
            // 发送完毕关闭连接对象
            transport.close();
            // 构建系统内邮件响应实体
            respone.setSuccess(true);
            respone.setMsg("邮件发送成功!");
        } catch (Exception e) {
            respone.setSuccess(false);
            respone.setMsg("邮件发送失败!");
            respone.setThrowable(e);
        }
        return respone;
    }

    private static void checkEmailWrapper(EmailWrapper wrapper) {
        if (StringUtils.isBlank(wrapper.getSubject())) {
            throw new RuntimeException("邮件主题为空，无法发送！");
        }
        if (StringUtils.isBlank(wrapper.getHtmlMsg())) {
            throw new RuntimeException("邮件内容为空，无法发送！");
        }
        if (StringUtils.isBlank(wrapper.getToAddr())) {
            throw new RuntimeException("接收人邮件地址为空，无法发送！");
        }
        String emailReg = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern p = Pattern.compile(emailReg);
        Arrays.stream(wrapper.getToAddr().split(",")).forEach(addr -> {
            Matcher m = p.matcher(addr);
            if (!m.matches()) {
                throw new RuntimeException("接收人邮件格式不对，无法发送！");
            }
        });
        if (StringUtils.isNotBlank(wrapper.getCcAddr())) {
            Arrays.stream(wrapper.getCcAddr().split(",")).forEach(addr -> {
                Matcher m = p.matcher(addr);
                if (!m.matches()) {
                    throw new RuntimeException("抄送人邮件格式不对，无法发送！");
                }
            });
        }
        if (StringUtils.isNotBlank(wrapper.getBccAddr())) {
            Arrays.stream(wrapper.getBccAddr().split(",")).forEach(addr -> {
                Matcher m = p.matcher(addr);
                if (!m.matches()) {
                    throw new RuntimeException("密送人邮件格式不对，无法发送！");
                }
            });
        }
    }

    private static class EmailUtilHolder {

        private static String PROTOCOL = "smtp";
        private static String HOST = "smtp.163.com";
        private static String PORT = "465";
        private static String FROM = "pixingdaiyue051@163.com";
        private static String USER_NAME = "【邮件系统测试】pixingdaiyue051";
        private static String PASSWORD = "pixingdaiyue051";
        private static String CHARSET = "UTF-8";
        private static Properties props;

        private static Properties getProperties() {
            if (null == props) {
                props = new Properties();
                props.setProperty("mail.transport.protocol", PROTOCOL);// 使用协议：smtp
                props.setProperty("mail.smtp.host", HOST);// 协议地址
                props.setProperty("mail.smtp.port", PORT);// 协议端口
                props.setProperty("mail.smtp.auth", "true");// 开启授权(QQ邮箱)
                props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");// 配置SSL认证类
                props.setProperty("mail.smtp.socketFactory.fallback", "false");// 是否处理非SSL认证请求
                props.setProperty("mail.smtp.socketFactory.port", PORT);// 配置SSL认证端口
            }
            return props;
        }
    }
}
