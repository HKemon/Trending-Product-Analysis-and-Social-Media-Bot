package AliexpressProductInfo.excel;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static AliexpressProductInfo.util.ProjectUtils.*;

public class ReadDataFromExcel {
    // Used to Read URL from AliexpressProductInfo.excel and pass it for further operation
    public void readDataForDatabase() {
        try (InputStream inputStream = new FileInputStream(excelFolder + "Aliexpress3.xlsx")) {
            Workbook workbook = WorkbookFactory.create(inputStream);

//            long a = System.currentTimeMillis();
            int x = workbook.getNumberOfSheets();
            for (int in = 0; in < x; in++) {
                Sheet sheet = workbook.getSheetAt(in);
                int firstRow = sheet.getFirstRowNum();
                int lastRow = sheet.getLastRowNum();
                for (int r = firstRow; r <= lastRow; r++) {
                    Row row = sheet.getRow(r);
                    int firstCellNumber = row.getFirstCellNum();
                    int lastCellNumber = row.getLastCellNum();
                    for (int c = firstCellNumber; c < lastCellNumber; c++) {
                        Cell cell = row.getCell(c);
                        if (r != 0 && c == 0) {
                            System.out.println(r + " " + c);
                            iterateLists.rawHtml(cell.toString());
//                            iterateLists.rawHtml(s);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("The file could not be read : " + ex.getMessage());
        } catch (EncryptedDocumentException ex) {
            System.out.println("The AliexpressProductInfo.excel file is encrypted : " + ex.getMessage());
        } finally {
            executorService.shutdown();
            System.out.println("End of excel file URL iterations");
        }
    }

    // Used to populate the Ip Agent from Excel file to 2D Array
    public void readDataForIpAgent() {
        try (InputStream inputStream = new FileInputStream(excelFolder + "IpPort.xlsx")) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            int x = workbook.getNumberOfSheets();
            for (int in = 0; in < x; in++) {
                Sheet sheet = workbook.getSheetAt(in);
                int firstRow = sheet.getFirstRowNum();
                int lastRow = sheet.getLastRowNum();
                for (int r = firstRow; r <= lastRow; r++) {
                    Row row = sheet.getRow(r);
                    int firstCellNumber = row.getFirstCellNum();
                    int lastCellNumber = row.getLastCellNum();

                    for (int c = firstCellNumber; c < lastCellNumber; c++) {
                        Cell cell = row.getCell(c);
                        cell.setCellType(CellType.STRING);
                        tempIpAgent[r][c] = cell.toString();
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("The file could not be read : " + ex.getMessage());
        } catch (EncryptedDocumentException ex) {
            System.out.println("The AliexpressProductInfo.excel file is encrypted : " + ex.getMessage());
        }
    }
}