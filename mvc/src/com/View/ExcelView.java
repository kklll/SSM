package com.View;

import com.Interface.ExcelExportService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ExcelView extends AbstractXlsView {
    //文件名
    private String fileName = null;
    //导出视图自定义接口
    private ExcelExportService excelExportService = null;

    //构造方法1
    public ExcelView(ExcelExportService excelExportService) {
        this.excelExportService = excelExportService;
    }

    //构造方法2
    public ExcelView(String viewName, ExcelExportService excelExportService) {
        this.setBeanName(viewName);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ExcelExportService getExcelExportService() {
        return excelExportService;
    }

    public void setExcelExportService(ExcelExportService excelExportService) {
        this.excelExportService = excelExportService;
    }


    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (excelExportService == null) {
            throw new RuntimeException("导出服务接口不能为空");
        }
        //文件名不为空,为空则使用请求路径中的字符串为文件名
        if (!StringUtils.isEmpty(fileName)) {
            String reqCharset = request.getCharacterEncoding();
            reqCharset = reqCharset == null ? "UTF-8" : reqCharset;
            fileName = new String(fileName.getBytes(reqCharset), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        }
        excelExportService.makeWorkBook(model, workbook);
    }
}