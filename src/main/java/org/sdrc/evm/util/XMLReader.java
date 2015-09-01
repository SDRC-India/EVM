package org.sdrc.evm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReader {
	public void readXml() {
		try {
			File xmlFile = new File("D:/xform.xml");
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			document.getDocumentElement().normalize();
			System.out.println("Root element :->"+document.getDocumentElement().getNodeName());
			NodeList nodeList=document.getDocumentElement().getChildNodes();
			System.out.println("Node length :->"+nodeList.getLength());
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				  Node node = nodeList.item(i);
				  if (node instanceof Element) {
				    Element childElement = (Element) node;
				   // System.out.println("tag name: " + childElement.getTagName());
				    String tagName=childElement.getTagName();
				    switch (tagName) {
					case "E1":
						
						break;
					case "E2":
						NodeList nodes=childElement.getChildNodes();
						System.out.println("Node length :->"+nodes.getLength());
						calculateNode_indicatorValue(nodes);
						break;
					default:
						break;
					}
				  }
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void calculateNode_indicatorValue(NodeList nodeList){
		ResourceBundle bundle=ResourceBundle.getBundle("indicatorinfo");
		Double scoreSum=0.0;
		Double maxSum=0.0;
		Double percentVal=0.0;
		/**
		 * Iterate for number of questions
		 */
		for(int j=0;j<nodeList.getLength();j++){
			
			/**
			 * Getting each node
			 */
			Node n = nodeList.item(j);
			 
			if (n instanceof Element) {
				int ycount=0;
				int tcount=0;
				Element child = (Element) n;
				String tagName=child.getTagName();
				System.out.println("Tag Name : "+tagName);
				String questionInfoString=bundle.getString(tagName);
				Map<String, String[]> questionInfo= getQuestionInfo(questionInfoString);
				Double maxVal=Double.parseDouble(questionInfo.get("max")[0]);
				for(String s:questionInfo.get("Q")){
//					System.out.println(s+"<>"+questionInfo.get("max")[0]);
					String val=child.getElementsByTagName(s).item(0).getNodeValue();
//					System.out.println("val : "+val);
					if(val.equalsIgnoreCase("yes"))
						ycount++;
					tcount++;
				}
				scoreSum=scoreSum+(maxVal*ycount/tcount);
				maxSum=maxSum+maxVal;
			}//end if
			
		}
		percentVal=scoreSum*100/maxSum;
		System.out.println(percentVal);
		
	}
	public Map<String, String[]> getQuestionInfo(String infoString){
		 Map<String, String[]> questionInfo= new HashMap<>();
		 String maxVal=infoString.split("@")[1].split(":")[1];
		 String[] questions=infoString.split("@")[0].split("#");
		 questionInfo.put("Q",questions);
		 questionInfo.put("max",new String[]{maxVal});
		 return questionInfo;
	}
	
	public static void writeDataToExcel(String fileName,List<Map<String, Object>> data) throws Exception{
//		FileInputStream file = new FileInputStream(new File("D:/EVM_Aggregation_report.xls"));
//	    Workbook workbook = new HSSFWorkbook(file);
	    Workbook wb = new HSSFWorkbook();
	    
	    Sheet sheet = wb.createSheet();
	    Cell cell = null;
	    Row row=null;
	    int rno = 1;
	    for(int r=0;r<data.size();r++){
	    	
	    	Map<String, Object> map = data.get(r);
		    String districtName = map.get("district").toString();
		    String facilityName = map.get("facility").toString();
		    String formType = map.get("formtype").toString();
		    Map<String ,Double> dataValues = (Map<String, Double>) map.get("data");
		    
		    
		    row = sheet.getRow(rno)!=null ? sheet.getRow(rno) : sheet.createRow(rno);
		    
		    row.createCell(1).setCellValue(formType);
		    row.createCell(2).setCellValue(districtName);
		    row.createCell(3).setCellValue(facilityName);
		    
		    if(fileName.contains("HealthFacility")){
		    	//=======FOR BIHAR
//		    	 if(dataValues.get("Bihar_HF_E1")!=null)
//				    	row.createCell(4).setCellValue(dataValues.get("Bihar_HF_E1"));
//				    row.createCell(5).setCellValue(dataValues.get("Bihar_HF_E2"));
//				    row.createCell(6).setCellValue(dataValues.get("Bihar_HF_E3"));
//				    row.createCell(7).setCellValue(dataValues.get("Bihar_HF_E4"));
//				    row.createCell(8).setCellValue(dataValues.get("Bihar_HF_E5"));
//				    row.createCell(9).setCellValue(dataValues.get("Bihar_HF_E6"));
//				    row.createCell(10).setCellValue(dataValues.get("Bihar_HF_E7"));
//				    row.createCell(11).setCellValue(dataValues.get("Bihar_HF_E8"));
//				    if(dataValues.get("Bihar_HF_E9")!=null)
//				    	row.createCell(12).setCellValue(dataValues.get("Bihar_HF_E9"));
		    	
		    	//======FOR ASSAM
		    	 if(dataValues.get("HF_E1")!=null)
				    	row.createCell(4).setCellValue(dataValues.get("HF_E1"));
				    row.createCell(5).setCellValue(dataValues.get("HF_E2"));
				    row.createCell(6).setCellValue(dataValues.get("HF_E3"));
				    row.createCell(7).setCellValue(dataValues.get("HF_E4"));
				    row.createCell(8).setCellValue(dataValues.get("HF_E5"));
				    row.createCell(9).setCellValue(dataValues.get("HF_E6"));
				    row.createCell(10).setCellValue(dataValues.get("HF_E7"));
				    row.createCell(11).setCellValue(dataValues.get("HF_E8"));
				    if(dataValues.get("HF_E9")!=null)
				    	row.createCell(12).setCellValue(dataValues.get("HF_E9"));
		    }
		   
		    if(fileName.contains("SVS")){
		    	//=======FOR BIHAR
//		    	if(dataValues.get("Bihar_SVS_E1")!=null)
//			    	row.createCell(4).setCellValue(dataValues.get("Bihar_SVS_E1"));
//			    row.createCell(5).setCellValue(dataValues.get("Bihar_SVS_E2"));
//			    row.createCell(6).setCellValue(dataValues.get("Bihar_SVS_E3"));
//			    row.createCell(7).setCellValue(dataValues.get("Bihar_SVS_E4"));
//			    row.createCell(8).setCellValue(dataValues.get("Bihar_SVS_E5"));
//			    row.createCell(9).setCellValue(dataValues.get("Bihar_SVS_E6"));
//			    row.createCell(10).setCellValue(dataValues.get("Bihar_SVS_E7"));
//			    row.createCell(11).setCellValue(dataValues.get("Bihar_SVS_E8"));
//			    if(dataValues.get("Bihar_SVS_E9")!=null)
//			    	row.createCell(12).setCellValue(dataValues.get("Bihar_SVS_E9"));
		    	
		    	//======FOR ASSAM
		    	if(dataValues.get("SVS_E1")!=null)
			    	row.createCell(4).setCellValue(dataValues.get("SVS_E1"));
			    row.createCell(5).setCellValue(dataValues.get("SVS_E2"));
			    row.createCell(6).setCellValue(dataValues.get("SVS_E3"));
			    row.createCell(7).setCellValue(dataValues.get("SVS_E4"));
			    row.createCell(8).setCellValue(dataValues.get("SVS_E5"));
			    row.createCell(9).setCellValue(dataValues.get("SVS_E6"));
			    row.createCell(10).setCellValue(dataValues.get("SVS_E7"));
			    row.createCell(11).setCellValue(dataValues.get("SVS_E8"));
			    if(dataValues.get("SVS_E9")!=null)
			    	row.createCell(12).setCellValue(dataValues.get("SVS_E9"));
		    }
		    
		    
		    if(fileName.contains("DVS")){
		    	//=======FOR BIHAR
//		    	 if(dataValues.get("Bihar_DVS_E1")!=null)
//				    	row.createCell(4).setCellValue(dataValues.get("Bihar_DVS_E1"));
//				    row.createCell(5).setCellValue(dataValues.get("Bihar_DVS_E2"));
//				    row.createCell(6).setCellValue(dataValues.get("Bihar_DVS_E3"));
//				    row.createCell(7).setCellValue(dataValues.get("Bihar_DVS_E4"));
//				    row.createCell(8).setCellValue(dataValues.get("Bihar_DVS_E5"));
//				    row.createCell(9).setCellValue(dataValues.get("Bihar_DVS_E6"));
//				    row.createCell(10).setCellValue(dataValues.get("Bihar_DVS_E7"));
//				    row.createCell(11).setCellValue(dataValues.get("Bihar_DVS_E8"));
//				    if(dataValues.get("Bihar_DVS_E9")!=null)
//				    	row.createCell(12).setCellValue(dataValues.get("Bihar_DVS_E9"));
		    	
		    	//======FOR ASSAM
		    	if(dataValues.get("DVS_E1")!=null)
			    	row.createCell(4).setCellValue(dataValues.get("DVS_E1"));
			    row.createCell(5).setCellValue(dataValues.get("DVS_E2"));
			    row.createCell(6).setCellValue(dataValues.get("DVS_E3"));
			    row.createCell(7).setCellValue(dataValues.get("DVS_E4"));
			    row.createCell(8).setCellValue(dataValues.get("DVS_E5"));
			    row.createCell(9).setCellValue(dataValues.get("DVS_E6"));
			    row.createCell(10).setCellValue(dataValues.get("DVS_E7"));
			    row.createCell(11).setCellValue(dataValues.get("DVS_E8"));
			    if(dataValues.get("DVS_E9")!=null)
			    	row.createCell(12).setCellValue(dataValues.get("DVS_E9"));
		    }
		    
		    row.createCell(13).setCellValue(dataValues.get("C1"));
		    row.createCell(14).setCellValue(dataValues.get("C2"));
		    row.createCell(15).setCellValue(dataValues.get("C3"));
		    row.createCell(16).setCellValue(dataValues.get("C4"));
		    row.createCell(17).setCellValue(dataValues.get("C5"));
		    row.createCell(18).setCellValue(dataValues.get("C6"));
		    if(dataValues.get("C7")!=null)
		    	row.createCell(19).setCellValue(dataValues.get("C7"));
		    
		    rno ++;
	    }
	    
	    FileOutputStream fileOut = new FileOutputStream("D:/"+fileName+".xls");
	    wb.write(fileOut);
	    fileOut.close();
	    
	}
	
}
