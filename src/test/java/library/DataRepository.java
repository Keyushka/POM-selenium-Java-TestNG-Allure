package library;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DataRepository {
    private final XSSFSheet sheet;
    private final XSSFWorkbook workbook;
    private static final Logger logger = LogManager.getLogger(DataRepository.class);
    private static final String FILE_LOAD_ERROR = "Failed to load Excel file from ";

    public DataRepository(String filePath, String sheetName) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(new File(filePath));
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            this.workbook = workbook;
            this.sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            logger.error(FILE_LOAD_ERROR + "{}", filePath, e);
            throw new RuntimeException(FILE_LOAD_ERROR + filePath, e);
        }
    }

    public String getStringCellValue(String cellContent) {
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell.getCellType() == CellType.STRING && cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
                    String cellValue = sheet.getRow(row.getRowNum()).getCell(cell.getColumnIndex() + 1).getStringCellValue().trim();
                    logger.info("Found value: {} for cell content: {}", cellValue, cellContent);
                    return cellValue;
                }
            }
        }
        logger.warn("No value found for cell content: {}", cellContent);
        return "";
    }
}
