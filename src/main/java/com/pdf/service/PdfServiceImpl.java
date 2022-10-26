package com.pdf.service;

import cn.hutool.core.io.resource.ClassPathResource;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.pdf.entity.SubjectResult;
import com.pdf.entity.User;
import com.pdf.util.AsposeWordPDF;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kc
 * @date Create in  2022/10/26
 */
@Service
public class PdfServiceImpl implements  PdfService{
    @Override
    public byte[] generatePdf() {
        //缓存存放
        String  fsLocalPath="C:\\tmp";
        ClassPathResource resource = new ClassPathResource("wordTemplate/studentTemplate.docx");
        String docTemplateFile =  resource.getAbsolutePath();

        byte[] result = null;
        File tmpDocOutFile = null;
        File tmpPdfOutFile = null;

        try {
            tmpDocOutFile = File.createTempFile("SxzdPreapprovalReport", ".docx", new File(fsLocalPath));
            tmpPdfOutFile = File.createTempFile("SxzdPreapprovalReport", ".pdf", new File(fsLocalPath));

            LoopRowTableRenderPolicy loopRowPolicy = new LoopRowTableRenderPolicy();

            Configure config = Configure.builder()
                    .bind("subjectList", loopRowPolicy)
                    .useSpringEL(false)
                    .build();
            List<SubjectResult> subjectList=new ArrayList<SubjectResult>();
            subjectList.add(new SubjectResult("数学","tom",132));
            subjectList.add(new SubjectResult("语文","jack",150));

            Map<String, Object> data = new HashMap<>();

            data.put("title", "张三基本信息");
            data.put("subjectList", subjectList);
            data.put("user", new User("张三","123","高一一班"));



            XWPFTemplate template = XWPFTemplate.compile(resource.getStream(), config).render(
                    data);
            template.writeAndClose(new FileOutputStream(tmpDocOutFile));


            AsposeWordPDF.word2pdf(tmpDocOutFile.getAbsolutePath(), tmpPdfOutFile.getAbsolutePath());

            result = FileUtils.readFileToByteArray(tmpPdfOutFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.deleteQuietly(tmpDocOutFile);
            FileUtils.deleteQuietly(tmpPdfOutFile);
        }

        return result;

    }
}
