package com.temail;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtil {

    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    public static EmailResponse sendEmail(EmailRequest request) {
        EmailResponse response = new EmailResponse();
        try {
            // 检查
            checkEmailRequest(request);
            // 创建session,开启邮件会话
            Session session = Session.getInstance(EmailUtilHolder.getProperties());
            // 加载mime邮件
            MimeMessage message = new MimeMessage(session);
            // 设置发件人
            Address addr = new InternetAddress(EmailUtilHolder.FROM, EmailUtilHolder.USER_NAME, EmailUtilHolder.CHARSET);
            message.setFrom(addr);
            // 设置标题
            message.setSubject(request.getSubject(), EmailUtilHolder.CHARSET);
            // 设置收件人
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(request.getToAddr()));
            // 设置发件时间
            message.setSentDate(new Date());
            // 设置抄送人
            if (StringUtils.isNotBlank(request.getCcAddr())) {
                message.setRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(request.getCcAddr()));
            }
            // 设置密送人
            if (StringUtils.isNotBlank(request.getBccAddr())) {
                message.setRecipients(MimeMessage.RecipientType.BCC, InternetAddress.parse(request.getBccAddr()));
            }
            // 邮件内容,包含内容,附件等
            MimeMultipart mp = new MimeMultipart();
            // 设置邮件内容
            MimeBodyPart bp = new MimeBodyPart();
            bp.setContent(request.getContent(), "text/html;charset=" + EmailUtilHolder.CHARSET);
            mp.addBodyPart(bp);
            // 设置附件
            if (null != request.getFileList() && !request.getFileList().isEmpty()) {
                request.getFileList().forEach(emailFile -> {
                    try {
                        File file = emailFile.getFile();
                        String extFileName = emailFile.getExtFileName();
                        // 文件处理
                        MimeBodyPart filePart = new MimeBodyPart();
                        FileDataSource dataSource = new FileDataSource(file);
                        DataHandler dataHandler = new DataHandler(dataSource);
                        filePart.setDataHandler(dataHandler);
                        // 文件名处理
                        String name = file.getName();
                        String fileName = Optional.ofNullable(extFileName)
                                .map(extName -> extName += name.substring(name.lastIndexOf(".")))
                                .orElse(name);
                        // 附件名称,处理中文乱码
                        filePart.setFileName(MimeUtility.encodeText(fileName));
                        // 放入正文（有先后顺序,所以在正文后面）
                        mp.addBodyPart(filePart);
                    } catch (Exception e) {
                        logger.info("添加附件失败!", e);
                        throw new EmailException("添加附件失败!");
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
            response.setSuccess(true);
            response.setMsg("邮件发送成功!");
        } catch (Exception e) {
            response.setSuccess(false);
            if (e instanceof EmailException) {
                response.setMsg(e.getMessage());
            } else {
                logger.info("邮件发送失败!", e);
                response.setMsg("邮件发送失败!");
            }
        }
        return response;
    }

    private static void checkEmailRequest(EmailRequest request) {
        if (StringUtils.isBlank(request.getSubject())) {
            throw new EmailException("邮件主题为空,无法发送!");
        }
        if (StringUtils.isBlank(request.getContent())) {
            throw new EmailException("邮件内容为空,无法发送!");
        }
        if (StringUtils.isBlank(request.getToAddr())) {
            throw new EmailException("接收人邮件地址为空,无法发送!");
        }
        Pattern p = Pattern.compile(EmailUtilHolder.EMAIL_REG);
        Arrays.stream(request.getToAddr().split(",")).forEach(addr -> {
            Matcher m = p.matcher(addr);
            if (!m.matches()) {
                throw new EmailException("接收人邮件格式不对,无法发送!");
            }
        });
        if (StringUtils.isNotBlank(request.getCcAddr())) {
            Arrays.stream(request.getCcAddr().split(",")).forEach(addr -> {
                Matcher m = p.matcher(addr);
                if (!m.matches()) {
                    throw new EmailException("抄送人邮件格式不对,无法发送!");
                }
            });
        }
        if (StringUtils.isNotBlank(request.getBccAddr())) {
            Arrays.stream(request.getBccAddr().split(",")).forEach(addr -> {
                Matcher m = p.matcher(addr);
                if (!m.matches()) {
                    throw new EmailException("密送人邮件格式不对,无法发送!");
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
        private static String EMAIL_REG = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
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
