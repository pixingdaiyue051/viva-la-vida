package com.temail;

import org.apache.commons.mail.EmailAttachment;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EmailTest {

    public static void main(String[] args) {
        boolean sendOver;
//        sendOver = EmailUtil.send("pixingdaiyue051@163.com", "test", "测试哦中文乱码");
//        System.out.println(sendOver);
//
//        sendOver = EmailUtil.sendHtmlMsg("pixingdaiyue051@163.com", "test", "测试发送文件", homeDirectory.getPath() + "/1984.txt", "1984");
//        System.out.println(sendOver);

        EmailWrapper wrapper = new EmailWrapper("test", "pixingdaiyue051@163.com", "", "测试wrapper的使用");

        FileSystemView fsv = FileSystemView.getFileSystemView();
        File homeDirectory = fsv.getHomeDirectory();
//        File[] files = homeDirectory.listFiles();
//        for (File f : files) {
//            System.out.println(f.getName() + "--" + f.isAbsolute() + "--" + f.length());
//        }

        wrapper.setAttachments(Arrays.stream(homeDirectory.listFiles()).filter(File::isFile)
                .filter(f -> !f.getAbsolutePath().endsWith("lnk")).map(f -> {
                    EmailAttachment attachment = new EmailAttachment();
                    attachment.setName(f.getName());
                    attachment.setPath(f.getAbsolutePath());
                    return attachment;
                }).limit(5).collect(Collectors.toList()));
        sendOver = EmailUtil.send(wrapper);
        System.out.println(sendOver);

    }
}
