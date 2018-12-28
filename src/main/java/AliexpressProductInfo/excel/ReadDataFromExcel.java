package AliexpressProductInfo.excel;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static AliexpressProductInfo.util.ProjectUtils.*;

public class ReadDataFromExcel {
    private ArrayList<String> arrayList = new ArrayList<>();

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

//            for (String s: arrayList) {
//                // Find the all possible category from the specific url
//                System.out.println(s);
//                iterateLists.rawHtml(s);
//            }
//            long b = System.currentTimeMillis();
//            System.out.println(b - a);
        } catch (IOException ex) {
            System.out.println("The file could not be read : " + ex.getMessage());
        } catch (EncryptedDocumentException ex) {
            System.out.println("The AliexpressProductInfo.excel file is encrypted : " + ex.getMessage());
        } finally {
            executorService.shutdown();
            System.out.println("End of excel file URL iterations");
        }

//        Visiting https://www.aliexpress.com/category/200002617/moisturizing-gloves/1.html?isFavorite=y&SortType=total_tranpro_desc
//        URL https://www.aliexpress.com/category/200002617/moisturizing-gloves/1.html?isFavorite=y&SortType=total_tranpro_desc
//        Connection reset https://www.aliexpress.com/category/200002617/moisturizing-gloves/1.html?isFavorite=y&SortType=total_tranpro_desc
//        In For next page iteration
//        URL https://www.aliexpress.com/category/200002617/moisturizing-gloves/1.html?isFavorite=y&SortType=total_tranpro_desc
//        No exception in Jsoup
//        Stop Executing Next Page
//        I 1
//        Beauty & Health > Skin Care > Hands & Nails > Moisturizing Gloves 1 arrayListReturn true
//        End of excel file URL iterations
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