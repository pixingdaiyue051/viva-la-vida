package com.tequeno.temail;

import java.io.File;

/**
 * @Desription:
 * @Author: hexk
 */
public class EmailFile {

    /**
     * 附件，必须
     */
    private File file;

    /**
     * 附件名字，非必需，空则使用文件名代替
     */
    private String extFileName;

    public EmailFile(File file) {
        this.file = file;
    }

    public EmailFile(File file, String extFileName) {
        this.file = file;
        this.extFileName = extFileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getExtFileName() {
        return extFileName;
    }

    public void setExtFileName(String extFileName) {
        this.extFileName = extFileName;
    }
}
