package dev.alexcoss.reportgenerator.service;

import dev.alexcoss.reportgenerator.dto.ApplicantData;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class ExcelWriter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Lock lock;
    private final SXSSFWorkbook workbook;
    private final Sheet sheet;
    private int rowCount;

    public ExcelWriter() {
        this.lock = new ReentrantLock();
        this.workbook = new SXSSFWorkbook();
        this.sheet = workbook.createSheet("Data");
        this.rowCount = 0;

        addHeaderRow();
    }

    public void writeBatch(List<ApplicantData> data) {
        lock.lock();
        try {
            for (ApplicantData item : data) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(item.getApplicantId());
                row.createCell(1).setCellValue(item.getFullName());
                row.createCell(2).setCellValue(item.getDateOfBirth().format(DATE_FORMATTER));
                row.createCell(3).setCellValue(item.getCountryName());
                row.createCell(4).setCellValue(item.getApplicationId());
                row.createCell(5).setCellValue(item.getStatusName());
                row.createCell(6).setCellValue(item.getApplicationDate().format(DATE_FORMATTER));
                row.createCell(7).setCellValue(item.getDocumentTypeName());
            }
        } finally {
            lock.unlock();
        }
    }

    public void saveToFile(Path path) throws IOException {
        createDirectoriesIfNotExist(path);

        try (OutputStream outputStream = Files.newOutputStream(path)){
            workbook.write(outputStream);
        } finally {
            workbook.dispose();
        }
    }

    private void addHeaderRow() {
        Row headerRow = sheet.createRow(rowCount++);
        headerRow.createCell(0).setCellValue("ApplicantID");
        headerRow.createCell(1).setCellValue("FullName");
        headerRow.createCell(2).setCellValue("DateOfBirth");
        headerRow.createCell(3).setCellValue("CountryName");
        headerRow.createCell(4).setCellValue("ApplicationID");
        headerRow.createCell(5).setCellValue("StatusName");
        headerRow.createCell(6).setCellValue("ApplicationDate");
        headerRow.createCell(7).setCellValue("DocumentTypeName");
    }

    private void createDirectoriesIfNotExist(Path path) throws IOException {
        File dir = path.getParent().toFile();
        if (!dir.exists() && !dir.mkdirs()) {
            log.error("Failed to create directories: {}", dir.getAbsolutePath());
            throw new IOException("Failed to create directories: " + dir.getAbsolutePath());
        }
    }
}
