package com.hdu.lease.utils;

import com.tencentcloudapi.tsf.v20180326.models.LaneInfo;
import jnr.ffi.annotations.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jackson
 * @date 2022/5/5 20:09
 * @description:
 */
@Slf4j
public class ExcelUtils {

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

    public static String getPath() {
        return ExcelUtils.class.getResource("/").getPath();
    }

    public static File createNewFile(String pathName) {
        File file = new File(getPath() + pathName);
        if (file.exists()) {
            file.delete();
        } else {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        return file;
    }

    public static File readFile(String pathName) {
        return new File(getPath() + pathName);
    }

    public static File readUserHomeFile(String pathName) {
        return new File(System.getProperty("user.home") + File.separator + pathName);
    }

    /**
     * POI解析excel文件
     *
     * @param file
     * @return
     */
    public static List<Map<Integer, List<String>>> readExcel(MultipartFile file) {
        Workbook workbook = null;
        List<Map<Integer, List<String>>> totalList = new ArrayList<>();
        try {
            workbook = WorkbookFactory.create(file.getInputStream());
            // 获取sheet数量
            int sheetNumber = workbook.getNumberOfSheets();
            log.info("共有{}个sheet页", sheetNumber);
            for (int i = 0; i < sheetNumber; i++) {
                // 存储单页sheet信息map
                Map<Integer, List<String>> map = new HashMap<>();

                // 获取当前sheet
                Sheet sheet = workbook.getSheetAt(i);
                int rowNum = sheet.getLastRowNum();
                log.info("共有{}行数据", rowNum);

                for (int k = 1; k <= rowNum; k++) {
                    List<String> infoList = new ArrayList<>();
                    Row rowData = sheet.getRow(k);
                    if (rowData != null) {
                        // 读取列
                        int cellCount = rowData.getLastCellNum();
                        for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                            Cell cell = rowData.getCell(cellNum);
                            // 匹配数据类型
                            String cellValue = "";
                            if (cell != null) {
                                if (cell.getCellTypeEnum() == CellType.STRING) {
                                    cellValue = cell.getStringCellValue();
                                }

                                if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                                    //数值型
                                    //poi读取整数会自动转化成小数，这里对整数进行还原，小数不做处理
                                    long longValue = Math.round(cell.getNumericCellValue());
                                    if (Double.parseDouble(longValue + ".0") == cell.getNumericCellValue()) {
                                        cellValue = String.valueOf(longValue);
                                    } else {
                                        cellValue = String.valueOf(cell.getNumericCellValue());
                                    }
                                }
                                infoList.add(cellValue);
                            } else {
                                infoList.add(null);
                            }
                        }
                        map.put(k, infoList);
                    }
                }
                totalList.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return totalList;
    }
}
