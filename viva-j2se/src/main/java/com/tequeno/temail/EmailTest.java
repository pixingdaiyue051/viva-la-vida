package com.tequeno.temail;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EmailTest {

    public static void main(String[] args) {
        EmailRequest request = new EmailRequest();
        request.setSubject("【test】");
        request.setToAddr("pixingdaiyue051@163.com");
        request.setCcAddr("pixingdaiyue051@dingtalk.com");
//        request.setBccAddr("pixingdaiyue051@dingtalk.com...");
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append("<head><title>504 Gateway Time-out</title></head>");
        builder.append("<body bgcolor='white'>");
        builder.append("<center><h1>504 Gateway Time-out</h1></center>");
        builder.append("<hr><center>nginx</center>");
        builder.append("</body>");
        builder.append("</html>");
        request.setContent(builder.toString());
        // 桌面文件
        File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
        request.setFileList(Arrays.stream(homeDirectory.listFiles())
                .filter(f -> f.isFile() && !f.isHidden() && !f.getAbsolutePath().endsWith("lnk"))
//                .map(EmailFile::new)
                .map(f -> new EmailFile(f, String.valueOf(System.currentTimeMillis())))
                .limit(2)
                .collect(Collectors.toList()));
        EmailResponse emailResponse = EmailUtil.sendEmail(request);
        System.out.println(emailResponse.isSuccess());
        System.out.println(emailResponse.getMsg());
    }
}
