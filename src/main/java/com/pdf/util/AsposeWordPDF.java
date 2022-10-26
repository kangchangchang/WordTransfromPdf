package com.pdf.util;

/**
 * @author kc
 * @date Create in  2022/10/26
 */
import com.aspose.words.Document;
import com.aspose.words.FontSettings;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class AsposeWordPDF {

    /**
     * Word文档转换PDF
     * <p>支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换</p>
     * @param docFile word文件
     * @param outPdfFile 输入pdf文件
     */
    public static void word2pdf(String docFile,String outPdfFile){
        log.info("输入合同DOC文件路径：【{}】", docFile);
        log.info("输出合同PDF文件路径: 【{}】", outPdfFile);
        // 验证License
//        if (!getLicense()) {
//            return;
//        }
        FileOutputStream os = null;
        try {
            File file = new File(outPdfFile);
            File pathFile = new File(file.getParent());
            if(!pathFile.exists()){
                pathFile.mkdirs();
            }
            os = new FileOutputStream(file);

            Document doc = new Document(docFile);
            // 从类路径里获取字体目录

            //linux  字体库地址  /usr/share/fonts
            //windows字体库地址  File.separator + "usr" +File.separator + "share" + File.separator + "fonts";
            String fontPath = "C:\\Windows\\Fonts";
            log.info("字体目录: 【{}】", fontPath);

            FontSettings.getDefaultInstance().setFontsFolder(fontPath, true);
            //设置多个字体目录
            //FontSettings.setFontsFolders(new String[] {"/home/server/fonts1", "/home/server/fonts2"}, true);

            doc.save(os, SaveFormat.PDF);
            log.info("word转pdf成功： word文件：【{}】; PDF文件: 【{}】", docFile, outPdfFile);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("word转pdf异常, word文件:【{}】", docFile, e);
        } finally {
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {}
            }
        }
    }



    public static void html2word(String htmlFile,String outDocFile){
        // 验证License
        if (!getLicense()) {
            return;
        }
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(outDocFile);
            Document html = new Document(htmlFile);
            html.save(os, SaveFormat.HTML);
        } catch (Exception e) {
            log.error("html2word异常", e);
        } finally {
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {}
            }
        }
    }

    /**
     * 获取license
     * @return
     */
    public static boolean getLicense() {
        boolean result = false;
        InputStream is = null;
        try {
            is = AsposeWordPDF.class.getClassLoader().getResourceAsStream("license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            log.error("asp许可证认证失败",e);
        } finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {}
            }
        }
        return result;
    }

}
