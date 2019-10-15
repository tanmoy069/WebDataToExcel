package com.alvi.toexcel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alvi.toexcel.model.Job;

import lombok.extern.slf4j.Slf4j;

/**
 * Excel sheet write service class
 * 
 * @author tanmoy.tushar
 * @since 2019-10-15
 */
@Slf4j
public class WriteService {
	
	/**
	 * Main function for writing whole object of job list. <br>
	 * You need to pass a bunch of job list and file path where you want to write.
	 *  
	 * @param jobList {@link pass Job List Object}
	 * @param filePath {@link pass Excel File Path}
	 * @throws IOException
	 */
	public void wirteExcel(List<Job> jobList, String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream inputStream = new FileInputStream(file);
		
		@SuppressWarnings("resource")
		Workbook wb = new XSSFWorkbook(inputStream);
		Sheet sheet = wb.createSheet();
		createHeaderRow(sheet);
		int count = 0;
		for (Job job : jobList) {
			Row row = sheet.createRow(++count);
			writeJob(job, row);
			log.info("Adding value to row " + count);
		}
		try {
			FileOutputStream outStream = new FileOutputStream(file);
			wb.write(outStream);
			log.info("Successfully added to excel sheet");
		} catch (Exception e) {
			log.warn("Failed to wrote excel sheet", e);
		}
	}
	
	/**
	 * Function for writing job object to excel sheet
	 * 
	 * @param ob {@link pass Job object}
	 * @param row {@link pass row number}
	 */
	private void writeJob(Job ob, Row row) {
		Cell cell = row.createCell(0);
		cell.setCellValue(ob.getTitle());
		cell = row.createCell(1);
		cell.setCellValue(ob.getCategory());
		cell = row.createCell(2);
		cell.setCellValue(ob.getType());
		cell = row.createCell(3);
		cell.setCellValue(ob.getRefId());
		cell = row.createCell(4);
		cell.setCellValue(ob.getJobUrl());
	}
	
	/**
	 * Function of creating header of row
	 * 
	 * @param sheet {@link pass Sheet object}
	 */
	private void createHeaderRow(Sheet sheet) {
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	    Font font = sheet.getWorkbook().createFont();
	    font.setBold(true);
	    font.setFontHeightInPoints((short) 12);
	    cellStyle.setFont(font);
	    
	    Row row = sheet.createRow(0);
	    
	    Cell cellTitle = row.createCell(0);	 
	    cellTitle.setCellStyle(cellStyle);
	    cellTitle.setCellValue("Job Title");
	 
	    Cell cellAuthor = row.createCell(1);
	    cellAuthor.setCellStyle(cellStyle);
	    cellAuthor.setCellValue("Job Category");
	 
	    Cell cellType = row.createCell(2);
	    cellType.setCellStyle(cellStyle);
	    cellType.setCellValue("Job Type");
	    
	    Cell cellRefId = row.createCell(3);
	    cellRefId.setCellStyle(cellStyle);
	    cellRefId.setCellValue("Reference Id");
	    
	    Cell cellJobUrl = row.createCell(4);
	    cellJobUrl.setCellStyle(cellStyle);
	    cellJobUrl.setCellValue("Job URL");
	}
	
	public Workbook getExcel(String xlFilePath) {
		Workbook wb = null;
		if (xlFilePath.endsWith("xlsx")) {
			wb = new XSSFWorkbook();
		}
		else if (xlFilePath.endsWith("xls")) {
			wb = new HSSFWorkbook();
		} else {
			throw new IllegalArgumentException("Your given file is not Excel file");
		}
		return wb;
	}
}
