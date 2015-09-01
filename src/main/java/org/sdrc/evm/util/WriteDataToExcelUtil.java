package org.sdrc.evm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.sdrc.evm.model.BlockDataValue;
import org.springframework.stereotype.Component;

@Component
public class WriteDataToExcelUtil implements WriteDataToExcel{

	public static final String APPLICATION_PROPERTY_FILE_PATH = "app/app.properties";
	
	@Override
	public InputStream writeToDCF(List<Map<String, List<BlockDataValue>>> mapList,String filePath,String district_name,String month,int year) {
		
		InputStream inputStream;
		Workbook workbook;
		CellReference cellreference = null;
		Row row = null;
		Cell cell = null;
		try {
			inputStream = new FileInputStream(new File(filePath));
			workbook = WorkbookFactory.create(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			loadProps(APPLICATION_PROPERTY_FILE_PATH);  // load application properties file to found specific form's properties file
			loadProps(configProp.getProperty("DCF-8"));
			
			String block_name_space_c = configProp.getProperty("Block_Name_Space_C");
			String block_name_space_n =	configProp.getProperty("Block_Name_Space_N");
			
			//month
			cellreference = new CellReference(configProp.getProperty("month"));
			row = firstSheet.getRow(cellreference.getRow());
					
			if(row!=null){
				cell = row.getCell(cellreference.getCol());
				cell.setCellValue(month);
			}
			//year
			cellreference = new CellReference(configProp.getProperty("year"));
			row = firstSheet.getRow(cellreference.getRow());
					
			if(row!=null){
				cell = row.getCell(cellreference.getCol());
				cell.setCellValue(year);
			}
			//district
			cellreference = new CellReference(configProp.getProperty("district"));
			row = firstSheet.getRow(cellreference.getRow());
					
			if(row!=null){
				cell = row.getCell(cellreference.getCol());
				cell.setCellValue(district_name);
			}
			
			
			
			
			for(int i=0;i<mapList.size();i++){
				Map<String, List<BlockDataValue>> map = mapList.get(i);
				String block = map.keySet().iterator().next();
				List<BlockDataValue> blockDataList = map.get(block);
				
//				System.out.println(block_name_space_c+""+block_name_space_n);
				cellreference = new CellReference(block_name_space_c+""+block_name_space_n);
				row = firstSheet.getRow(cellreference.getRow());
						
				if(row!=null){
					cell = row.getCell(cellreference.getCol());
					cell.setCellValue(block);
				}
				
				for(BlockDataValue blockdatavalue:blockDataList){
					System.out.println(blockdatavalue.getIndNumber());
					String indicator_cell_number = configProp.getProperty(blockdatavalue.getIndNumber());
					
//					System.out.println(block_name_space_c+""+indicator_cell_number);
					cellreference = new CellReference(block_name_space_c+""+indicator_cell_number);
					row = firstSheet.getRow(cellreference.getRow());
							
					if(row!=null){
						cell = row.getCell(cellreference.getCol());
						cell.setCellValue(blockdatavalue.getDataValue());
					}
				}
				block_name_space_c = nextCellChar(block_name_space_c);
			}
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    try {
		        workbook.write(bos);
		    } finally {
		        bos.close();
		    }
		    byte[] bytes = bos.toByteArray();
		    inputStream = new ByteArrayInputStream(bytes);
			
		    return inputStream;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static Properties configProp = new Properties();
	public void loadProps(String file) {
	try {
		InputStream in = getClass().getClassLoader().getResourceAsStream(file);
		configProp.load(in);
		} catch (IOException e) {
		System.out.println("Exception while loading properties files : "+e);
		e.printStackTrace();
		} catch(Exception e){
	        	System.out.println("Exception while loading properties files : "+e);
		}
	}
	public String nextCellChar(String cell){
		System.out.println(cell.length());
		String nextCell="";
		if(cell.length()==1){
			char currentCell = cell.charAt(0);
			if(currentCell=='Z' || currentCell=='z'){
				nextCell = "AA";
			}
			else
				nextCell = (++currentCell)+"";
		}//end of if 1
		if(cell.length()==2){
			char firstCell = cell.charAt(0);
			char secondCell = cell.charAt(1);
			if(secondCell =='Z' || secondCell =='Z'){
				nextCell = (++firstCell)+"A";
			}
			else
				nextCell = firstCell+""+(++secondCell);
		}// end of if 2
		return nextCell;
	}
	public static void main(String[] args) {
		System.out.println("Omm Namaha Sivaya");
		
		InputStream inputStream;
		Workbook workbook;
		CellReference cellreference = null;
		Row row = null;
		Cell cell = null;
		
		try {
			inputStream = new FileInputStream(new File("D:\\DCF-8_New.xls"));
			workbook = WorkbookFactory.create(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			
			cellreference = new CellReference("E34");
			row = firstSheet.getRow(cellreference.getRow());
					
			if(row!=null){
				cell = row.getCell(cellreference.getCol());
				System.out.println(cell.getStringCellValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
