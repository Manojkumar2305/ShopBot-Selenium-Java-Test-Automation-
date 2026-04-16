package com.shopbot.dataproviders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class TestDataProvider {

    private static final String RES = System.getProperty("user.dir") + "/src/test/resources/";

    private TestDataProvider() {}

    /** Reads loginData.json → { username, password, expectedResult, description } */
    public static Object[][] getLoginDataFromJson() {
        try {
            ObjectMapper m = new ObjectMapper();
            JsonNode root  = m.readTree(new File(RES + "loginData.json"));
            List<Object[]> rows = new ArrayList<>();
            for (JsonNode n : root)
                rows.add(new Object[]{ n.get("username").asText(), n.get("password").asText(),
                                       n.get("expectedResult").asText(), n.get("description").asText() });
            return rows.toArray(new Object[0][]);
        } catch (IOException e) {
            throw new RuntimeException("loginData.json read failed: " + e.getMessage());
        }
    }

    /** Reads LoginData sheet from testdata.xlsx */
    public static Object[][] getLoginDataFromExcel() {
        return readSheet("LoginData", 4);
    }

    /** Reads CheckoutData sheet from testdata.xlsx → { firstName, lastName, postalCode } */
    public static Object[][] getCheckoutDataFromExcel() {
        return readSheet("CheckoutData", 3);
    }

    private static Object[][] readSheet(String sheetName, int cols) {
        List<Object[]> rows = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(RES + "testdata.xlsx");
             Workbook wb = new XSSFWorkbook(fis)) {
            Sheet sheet = wb.getSheet(sheetName);
            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row == null) continue;
                Object[] data = new Object[cols];
                for (int c = 0; c < cols; c++) {
                    Cell cell = row.getCell(c);
                    data[c] = cell == null ? "" : cellVal(cell);
                }
                rows.add(data);
            }
        } catch (IOException e) {
            throw new RuntimeException("Excel read failed [" + sheetName + "]: " + e.getMessage());
        }
        return rows.toArray(new Object[0][]);
    }

    private static String cellVal(Cell c) {
        switch (c.getCellType()) {
            case STRING:  return c.getStringCellValue().trim();
            case NUMERIC: return String.valueOf((long) c.getNumericCellValue());
            default:      return "";
        }
    }
}
