package service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Error;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class ExcelCreatorService {

    public File createExcelFile(List<Error> errors) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Druckfehler");

        // set cell styles
        CellStyle style = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row row = sheet.createRow(0);
        Cell cellAnwender = row.createCell(0);
        cellAnwender.setCellValue("Anwender");
        cellAnwender.setCellStyle(style);
        Cell cellBestellnummer = row.createCell(1);
        cellBestellnummer.setCellValue("Bestellnummer");
        cellBestellnummer.setCellStyle(style);
        Cell cellPos = row.createCell(2);
        cellPos.setCellValue("Position");
        cellPos.setCellStyle(style);
        Cell cellDatum = row.createCell(3);
        cellDatum.setCellValue("Datum");
        cellDatum.setCellStyle(style);
        Cell cellWerk = row.createCell(4);
        cellWerk.setCellValue("Werk");
        cellWerk.setCellStyle(style);

        // write data in file
        int rowCount = 0;
        for (Error e : errors) {
            rowCount++;
            Row r = sheet.createRow(rowCount);
            Cell cellA = r.createCell(0);
            cellA.setCellValue(e.getAnwender());
            Cell cellB = r.createCell(1);
            cellB.setCellValue(e.getBestellnummer());
            Cell cellC = r.createCell(2);
            cellC.setCellValue(e.getPositionsnummer());
            Cell cellD = r.createCell(3);
            cellD.setCellValue(e.getDatum());
            Cell cellE = r.createCell(4);
            cellE.setCellValue(e.getWerk());
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);

        // create file
        long time = new Timestamp(System.currentTimeMillis()).getTime();
        File file = new File("ExcelFiles/nicht_gedruckte_Bestellungen_" + errors.get(0).getAnwender() + "_" + time + ".xlsx");
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);

        return file;
    }
}
