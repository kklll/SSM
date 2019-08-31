package com.Interface;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.Map;

public interface ExcelExportService {
    public void makeWorkBook(Map<String, Object> model, Workbook workbook);
}
