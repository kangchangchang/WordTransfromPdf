package com.pdf.Controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.pdf.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author pc
 * @date Create in  2022/10/26
 */
@RestController
public class PdfController {
    @Autowired
    private PdfService  pdfService;
    @RequestMapping("/downloadPdf")
    @ResponseBody
    public void downloadPreapprovalPdf( HttpServletResponse response) throws UnsupportedEncodingException {
        String pdfFileName = LocalDateTimeUtil.format(LocalDateTimeUtil.now(), "yyyyMMddHHmmss");


        response.setContentType("application/pdf");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "content-type,x-requested-with,Authorization");
        response.setHeader("Access-Control-Expose-Headers", "Authorization,authenticated");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Content-Disposition", "inline; filename= " + URLEncoder.encode(pdfFileName, "UTF-8")+".pdf");
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(pdfService.generatePdf());

            outputStream.flush();
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
