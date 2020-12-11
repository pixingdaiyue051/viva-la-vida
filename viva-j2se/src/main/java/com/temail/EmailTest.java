package com.temail;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EmailTest {

    public static void main(String[] args) {

        EmailWrapper wrapper = new EmailWrapper();
        wrapper.setSubject("【test】");
        wrapper.setToAddr("pixingdaiyue051@163.com");
        wrapper.setCcAddr("pixingdaiyue051@dingtalk.com");
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append("<head><title>504 Gateway Time-out</title></head>");
        builder.append("<body bgcolor='white'>");
        builder.append("<center><h1>504 Gateway Time-out</h1></center>");
        builder.append("<hr><center>nginx</center>");
        builder.append("</body>");
        builder.append("</html>");
        wrapper.setHtmlMsg(builder.toString());
        // 桌面文件
        File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
        wrapper.setFileList(Arrays.stream(homeDirectory.listFiles()).filter(File::isFile)
                .filter(f -> !f.getAbsolutePath().endsWith("lnk"))
                .limit(1)
                .collect(Collectors.toList()));
        EmailRespone emailRespone = EmailUtil.sendMimeEmail(wrapper);
        System.out.println(emailRespone.getSuccess());
        System.out.println(emailRespone.getMsg());
        System.out.println(emailRespone.getThrowable());
    }
}
