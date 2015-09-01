package org.sdrc.evm.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.sdrc.devinfo.domain.UtAreaEn;
import org.sdrc.devinfo.domain.UtData;
import org.sdrc.evm.domain.SamikshyaMonitoringForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * @author kamal
 * The Utility class to create dynamic excel form 
 */
@Component
public class SamikshyaUtils {
	
	private static final Logger LOGGER = Logger.getLogger(SamikshyaUtils.class);
	
	@Autowired
	ResourceBundleMessageSource workspaceMessageSource;

	private CellStyle getAreaCellStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setRotation((short) 90);
		cellStyle.setWrapText(true);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);

		// setting Font
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 8);
		font.setFontName(workspaceMessageSource.getMessage(Constants.FILE_FONT_NAME, null, null));
		cellStyle.setFont(font);

		return cellStyle;
	}
	private CellStyle getDataCellStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		// setting Font
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName(workspaceMessageSource.getMessage(Constants.FILE_FONT_NAME, null, null));
		font.getBoldweight();
		cellStyle.setFont(font);
		//Unlock the cell
		cellStyle.setLocked(false);

		return cellStyle;
	}

	private CellStyle getTotalCellStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// cellStyle.setRotation((short)90);
		cellStyle.setWrapText(true);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);

		// setting Font
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName(workspaceMessageSource.getMessage(Constants.FILE_FONT_NAME, null, null));
		font.getBoldweight();
		cellStyle.setFont(font);

		return cellStyle;
	}
	
	private CellStyle getDpcDataCellStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		// setting Font
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName(workspaceMessageSource.getMessage(Constants.FILE_FONT_NAME, null, null));
		font.getBoldweight();
		cellStyle.setFont(font);

		return cellStyle;
	}

	/**
	 * @param areaEns
	 * @param monitoringForm
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public Workbook makeMonitoringForm(List<UtAreaEn> areaEns,SamikshyaMonitoringForm monitoringForm,String filePath) throws Exception {
		
		Calendar calendar=Calendar.getInstance();
		
		File file = new File(filePath);
		Workbook wb=WorkbookFactory.create(new ByteArrayInputStream(FileUtils.readFileToByteArray(file)));
		Sheet sheet = wb.getSheetAt(0);
		
		CellReference cellReference = new CellReference(workspaceMessageSource.getMessage(Constants.FILE_MONTH_CELL,null, null));
		sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).setCellValue(Months.values()[calendar.get(Calendar.MONTH)].toString());

		cellReference = new CellReference(workspaceMessageSource.getMessage(Constants.FILE_YEAR_CELL, null, null));
		sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).setCellValue(calendar.get(Calendar.YEAR));

		cellReference = new CellReference(workspaceMessageSource.getMessage(Constants.FILE_BLOCK_CELL, null, null));
		sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).setCellValue(monitoringForm.getSamikshyaBlock().getBlockName());

		cellReference = new CellReference(workspaceMessageSource.getMessage(Constants.FILE_DISTRICT_CELL, null, null));
		sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).setCellValue(monitoringForm.getSamikshyaDistrict().getDistrictName());
		
		cellReference = new CellReference(workspaceMessageSource.getMessage(Constants.FILE_CLUSTERS_NUMBER_CELL, null, null));
		sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).setCellValue(areaEns.size());
		/**
		 * Getting First row number
		 */
		int rowNum = new CellReference(workspaceMessageSource.getMessage(Constants.FILE_BRCC_FIRST_LOCATION, null, null)).getRow();
		/**
		 * Getting First cell number
		 */
		int cellNum = new CellReference(workspaceMessageSource.getMessage(Constants.FILE_BRCC_FIRST_LOCATION, null, null)).getCol();
		/**
		 * Getting Last row number
		 */
		int lastRowNum = new CellReference(workspaceMessageSource.getMessage(Constants.FILE_BRCC_LAST_LOCATION, null, null)).getRow();
		int firstCellNum = cellNum;
		
		Row row = null;
		Cell cell = null;
		/**
		 * Create row by validating its existence
		 */
		if (sheet.getRow(rowNum) == null) {
			row = sheet.createRow(rowNum);
		} else {
			row = sheet.getRow(rowNum);
		}
		/**
		 * Fill Area into First row
		 */
		for (UtAreaEn area : areaEns) {
			/**
			 * Create cell by validating its existence
			 */
			if (row.getCell(cellNum) == null) {
				cell = row.createCell(cellNum);
			} else {
				cell = row.getCell(cellNum);
			}
			cell.setCellValue(area.getArea_Name());
			cell.setCellStyle(getAreaCellStyle(wb));
			/**
			 * Increment cell number
			 */
			cellNum++;
		}
		/**
		 * Finally add Total cell at the end
		 */
		if (row.getCell(cellNum) == null) {
			cell = row.createCell(cellNum);
		} else {
			cell = row.getCell(cellNum);
		}
		cell.setCellValue("Total");
		cell.setCellStyle(getTotalCellStyle(wb));
		
		LOGGER.info("new cell-->"+ firstCellNum);
		LOGGER.info("last cell no-->"+cellNum);
		/**
		 * Create empty cells from the next row to last row
		 */
		for (int r = rowNum + 1; r <= lastRowNum; r++) {
			/**
			 * Create row by validating its existence
			 */
			if (sheet.getRow(r) == null) {
				row = sheet.createRow(rowNum);
			} else {
				row = sheet.getRow(r);
			}
			/**
			 * create cells from first cell location to last cell location
			 */
			for (int c = firstCellNum; c <= cellNum; c++) {
				
				if(row.getCell(new CellReference(workspaceMessageSource.getMessage(Constants.FILE_FEATURES_COLUMN_CELL, null, null)).getCol()).getStringCellValue().trim().length()>1){
					
					if (row.getCell(c) == null) {
						cell = row.createCell(c);
						cell.setCellStyle(getDataCellStyle(wb));
					} else {
						cell = row.getCell(c);
						cell.setCellStyle(getDataCellStyle(wb));
					}
					
				}else{
					/*
					 * Merge the sub heading cell up to last cell
					 */
					sheet.addMergedRegion(new CellRangeAddress(
				            r, //first row (0-based)
				            r, //last row  (0-based)
				            new CellReference(workspaceMessageSource.getMessage(Constants.FILE_FEATURES_COLUMN_CELL, null, null)).getCol()-1, //first column (0-based)
				            cellNum  //last column  (0-based)
				    ));
					break;
				}
			}
			
		}
		
		/*
		 * Merge the Heading cell up to last cell
		 */
		sheet.addMergedRegion(new CellRangeAddress(
		            rowNum-1, 
		            rowNum-1, 
		            new CellReference(workspaceMessageSource.getMessage(Constants.FILE_HEADING_CELL, null, null)).getCol(), 
		            cellNum  
		 ));
		
		/*
		 * Giving a top border to Heading cell
		 */
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		for(int c=new CellReference(workspaceMessageSource.getMessage(Constants.FILE_HEADING_CELL, null, null)).getCol()+1;c<cellNum;c++){
			if (sheet.getRow(new CellReference(workspaceMessageSource.getMessage(Constants.FILE_HEADING_CELL, null, null)).getRow()).getCell(c) == null) {
				cell = sheet.getRow(new CellReference(workspaceMessageSource.getMessage(Constants.FILE_HEADING_CELL, null, null)).getRow()).createCell(c);
				cell.setCellStyle(cellStyle);
			} else {
				cell = sheet.getRow(new CellReference(workspaceMessageSource.getMessage(Constants.FILE_HEADING_CELL, null, null)).getRow()).getCell(c);
				cell.setCellStyle(cellStyle);
			}
		}
		 
		cellStyle = wb.createCellStyle();
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		sheet.getRow(new CellReference(workspaceMessageSource.getMessage(Constants.FILE_HEADING_CELL, null, null)).getRow()).createCell(cellNum).setCellStyle(cellStyle);
		
		return wb;
	}
	public Workbook makeDpcMonitoringForm(List<UtAreaEn> clusters,List<UtAreaEn> areaEns,List<List<UtData>> dataLists,SamikshyaMonitoringForm monitoringForm,String filePath) throws Exception {
		
		Calendar calendar=Calendar.getInstance();
		
		File file = new File(filePath);
		Workbook wb=WorkbookFactory.create(new ByteArrayInputStream(FileUtils.readFileToByteArray(file)));
		Sheet sheet = wb.getSheetAt(0);
		
		CellReference cellReference = new CellReference(workspaceMessageSource.getMessage(Constants.DPC_FILE_MONTH_CELL,null, null));
		sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).setCellValue(Months.values()[calendar.get(Calendar.MONTH)].toString());

		cellReference = new CellReference(workspaceMessageSource.getMessage(Constants.DPC_FILE_YEAR_CELL, null, null));
		sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).setCellValue(calendar.get(Calendar.YEAR));
		
		cellReference = new CellReference(workspaceMessageSource.getMessage(Constants.DPC_FILE_BRCC_NUMBER_CELL, null, null));
		sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).setCellValue(areaEns.size());
		
		cellReference = new CellReference(workspaceMessageSource.getMessage(Constants.DPC_FILE_CRCC_NUMBER_CELL, null, null));
		sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).setCellValue(clusters.size());

		cellReference = new CellReference(workspaceMessageSource.getMessage(Constants.DPC_FILE_DISTRICT_CELL, null, null));
		sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).setCellValue(monitoringForm.getSamikshyaDistrict().getDistrictName());
		
		cellReference = new CellReference(workspaceMessageSource.getMessage(Constants.DPC_FILE_TOTAL_SCHOOL_LOCATION, null, null));
		if(dataLists.get(0).get(dataLists.get(0).size()-1)!=null)
			sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).setCellValue(dataLists.get(0).get(dataLists.get(0).size()-1).getData_Value());
		else
			sheet.getRow(cellReference.getRow()).getCell(cellReference.getCol()).setCellValue("No data");
		
		/**
		 * Getting First row number
		 */
		int rowNum = new CellReference("F34").getRow();
		/**
		 * Getting First cell number
		 */
		int cellNum = new CellReference("F34").getCol();
		/**
		 * Getting Last row number
		 */
//		int lastRowNum = new CellReference("F97").getRow();
		int firstCellNum = cellNum;
		
		Row row = null;
		Cell cell = null;
		/**
		 * Create row by validating its existence
		 */
		if (sheet.getRow(rowNum) == null) {
			row = sheet.createRow(rowNum);
		} else {
			row = sheet.getRow(rowNum);
		}
		/**
		 * Fill Area into First row
		 */
		for (UtAreaEn area : areaEns) {
			/**
			 * Create cell by validating its existence
			 */
			if (row.getCell(cellNum) == null) {
				cell = row.createCell(cellNum);
				if (firstCellNum == 0) {
					firstCellNum = cellNum;
//					System.out.println("new cell"+ newCellNum);
				}
//				System.out.println("new cell created");
			} else {
				cell = row.getCell(cellNum);
//				System.out.println("cell exist");
			}
			cell.setCellValue(area.getArea_Name());
			cell.setCellStyle(getAreaCellStyle(wb));
			/**
			 * Increment cell number
			 */
			cellNum++;
		}
		/**
		 * Finally add Total cell at the end
		 */
		if (row.getCell(cellNum) == null) {
			cell = row.createCell(cellNum);
		} else {
			cell = row.getCell(cellNum);
		}
		cell.setCellValue("Total");
		cell.setCellStyle(getTotalCellStyle(wb));
		
		LOGGER.info("new cell-->"+ firstCellNum);
		LOGGER.info("last cell no-->"+cellNum);
		/**
		 * Create empty cells from the next row to last row
		 */
		int r = rowNum + 1;
		for(List<UtData> datas:dataLists){
			/**
			 * Create row by validating its existence
			 */
			if (sheet.getRow(r) == null) {
				row = sheet.createRow(rowNum);
			} else {
				row = sheet.getRow(r);
			}
			int c = firstCellNum;
			for(UtData data:datas){
				
				if(row.getCell(new CellReference("B35").getCol()).getStringCellValue().trim().length()>1){
					
					if (row.getCell(c) == null) {
						cell = row.createCell(c);
						cell.setCellStyle(getDpcDataCellStyle(wb));
						if(data!=null)
							cell.setCellValue(data.getData_Value());
						else
							cell.setCellValue("No data");
					} else {
						cell = row.getCell(c);
						cell.setCellStyle(getDpcDataCellStyle(wb));
						if(data!=null)
							cell.setCellValue(data.getData_Value());
						else
							cell.setCellValue("No data");
					}
					
				}else{
					/*
					 * Merge the sub heading cell up to last cell
					 */
					sheet.addMergedRegion(new CellRangeAddress(
				            r, //first row (0-based)
				            r, //last row  (0-based)
				            new CellReference("B35").getCol()-1, //first column (0-based)
				            cellNum  //last column  (0-based)
				    ));
					r++;
					/**
					 * Create row by validating its existence
					 */
					if (sheet.getRow(r) == null) {
						row = sheet.createRow(rowNum);
					} else {
						row = sheet.getRow(r);
					}
					c = firstCellNum;
					for(UtData data1:datas){
						if (row.getCell(c) == null) {
							cell = row.createCell(c);
							cell.setCellStyle(getDpcDataCellStyle(wb));
							if(data1!=null)
								cell.setCellValue(data1.getData_Value());
							else
								cell.setCellValue("No data");
						} else {
							cell = row.getCell(c);
							cell.setCellStyle(getDpcDataCellStyle(wb));
							if(data1!=null)
								cell.setCellValue(data1.getData_Value());
							else
								cell.setCellValue("No data");
						}
						c++;
					}
					break;
				}
				/**
				 * Incrementing 1 cell.
				 */
				c++;
			}
			/**
			 * Incrementing 1 row.
			 */
			r++;
		}
		
		/*
		 * Merge the Heading cell up to last cell
		 */
//		sheet.addMergedRegion(new CellRangeAddress(
//		            rowNum-1, 
//		            rowNum-1, 
//		            new CellReference(workspaceMessageSource.getMessage(Constants.FILE_HEADING_CELL, null, null)).getCol(), 
//		            cellNum  
//		 ));
		
		/*
		 * Giving a top border to Heading cell
		 */
//		CellStyle cellStyle = wb.createCellStyle();
//		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
//		for(int c=new CellReference(workspaceMessageSource.getMessage(Constants.FILE_HEADING_CELL, null, null)).getCol()+1;c<cellNum;c++){
//			if (sheet.getRow(new CellReference(workspaceMessageSource.getMessage(Constants.FILE_HEADING_CELL, null, null)).getRow()).getCell(c) == null) {
//				cell = sheet.getRow(new CellReference(workspaceMessageSource.getMessage(Constants.FILE_HEADING_CELL, null, null)).getRow()).createCell(c);
//				cell.setCellStyle(cellStyle);
//			} else {
//				cell = sheet.getRow(new CellReference(workspaceMessageSource.getMessage(Constants.FILE_HEADING_CELL, null, null)).getRow()).getCell(c);
//				cell.setCellStyle(cellStyle);
//			}
//		}
//		 
//		cellStyle = wb.createCellStyle();
//		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
//		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
//		sheet.getRow(new CellReference(workspaceMessageSource.getMessage(Constants.FILE_HEADING_CELL, null, null)).getRow()).createCell(cellNum).setCellStyle(cellStyle);
		
		return wb;
	}
}
