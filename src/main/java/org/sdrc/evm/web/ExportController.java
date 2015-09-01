
/**
 * 
 */
package org.sdrc.evm.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.sdrc.evm.model.DashboardExport;
import org.sdrc.evm.model.GoogleMapData;
import org.sdrc.evm.model.SpiderDataCollection;
import org.sdrc.evm.model.SpiderDataModel;
import org.sdrc.evm.model.ValueObject;
import org.sdrc.evm.service.DashboardService;
import org.sdrc.evm.util.HeaderFooter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


/**
 * @author Vedprkash
 *
 */
@Controller
public class ExportController {
	
	@Autowired
	private DashboardController dashboardController;
	
	@Autowired
	private ServletContext context;
	
	private final DashboardService dashboardService;
	
	@Autowired
	public ExportController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	
	@RequestMapping(value = "/exportToPdf", method = RequestMethod.POST)
	public void exportToPdf(@RequestParam("imageBase64") String valueOn,@RequestParam("chartImageBase64") String chartvalueOn,
			DashboardExport dashboardExport,
			HttpServletResponse response) throws IOException {
		
		String areaId = dashboardExport.getAreaId();
		String indicatorId = dashboardExport.getIndicatorId();
		String timePeriodId = dashboardExport.getTimePeriodId();
		String area = dashboardExport.getArea();
		String parentAreaName = dashboardExport.getParentAreaName();
		String indicator = dashboardExport.getIndicator();
		String timePeriod = dashboardExport.getTimePeriod();
//		String sectorId = dashboardExport.getSectorId();
		String secondTimeperiodId = dashboardExport.getSecondTimeperiodId();
//		String secondTimeperiod = dashboardExport.getSecondTimeperiod();
		String sectorName = dashboardExport.getSectorName();
		String subSectorName = dashboardExport.getSubSectorName();
		String subSectorKey = dashboardExport.getSubSectorKey();
		String granularitySpiderKey = dashboardExport.getGranularitySpiderKey();
		String granularitySpiderValue = dashboardExport.getGranularitySpiderValue();
		// For Image 
//		System.out.println("Image ---:" + valueOn);
		valueOn = valueOn.split(",")[1];
		byte[] decodedImage = Base64.decodeBase64(valueOn);
//		System.out.println(decodedImage.length);
//		System.out.println("decodedImage :" + decodedImage);
		
//		chartvalueOn = chartvalueOn.split(",")[1];
//		byte[] chartDecodedImage = Base64.decodeBase64(chartvalueOn);
		//End of Image
		
		if(areaId == null && area == null || areaId.equals("") && area.equals("") ){
			areaId = granularitySpiderKey;
			area = granularitySpiderValue;
		}
		response.setContentType("application/force-download");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition",
				"attachment; filename=\""+area+".pdf\"");
		
		try {
		ValueObject sectorGranularity = dashboardService.fetchSelectedGranularity(sectorName,parentAreaName);
		List<GoogleMapData>	googleMapData = dashboardService.fetchGoogleMapData(sectorName,indicatorId,timePeriodId,sectorGranularity.getKey(),parentAreaName);
			
		SpiderDataCollection spiderDataCollection = fetchSpidertData(subSectorKey,areaId,timePeriodId,secondTimeperiodId);
		
		Font smallBold = new Font(Font.FontFamily.HELVETICA, 10,Font.BOLD);
		Font dataFont = new Font(Font.FontFamily.HELVETICA, 10);
		
		Document document = new Document(PageSize.A4.rotate());
		
		PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
			
			// wiring up header footer event
			HeaderFooter event = new HeaderFooter(context, dashboardExport);
			writer.setPageEvent(event); // wiring up header footer event
			
			document.open();
			
			BaseColor cellColor = WebColors.getRGBColor("#E8E3E2");
			
			Paragraph googleMapParagraph = new Paragraph();
			googleMapParagraph.setAlignment(Element.ALIGN_CENTER);
			googleMapParagraph.setSpacingAfter(10);
			Chunk mapchunk = new Chunk("Facility Level : "+sectorName +"    Indicator Name : "+indicator+"    Time Period : "+timePeriod);
			googleMapParagraph.add(mapchunk);
			
			Paragraph dashboardTitle = new Paragraph();
			dashboardTitle.setAlignment(Element.ALIGN_CENTER);
			dashboardTitle.setSpacingAfter(10);
			Chunk dashboardChunk = new Chunk( "EVM Score Card" );
			dashboardTitle.add(dashboardChunk);
			
			Paragraph blankSpace = new Paragraph();
			blankSpace.setAlignment(Element.ALIGN_CENTER);
			blankSpace.setSpacingAfter(10);
			Chunk blankSpaceChunk = new Chunk( "          " );
			blankSpace.add(blankSpaceChunk);
			
			Paragraph spiderDataParagraph = new Paragraph();
			spiderDataParagraph.setAlignment(Element.ALIGN_CENTER);
			spiderDataParagraph.setSpacingAfter(10);
			Chunk spiderChunk = new Chunk( area +" "+subSectorName);
			spiderDataParagraph.add(spiderChunk);

			//for Image
			Image image = Image.getInstance(decodedImage);
			int indentation = 0;
			float scaler = ((document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin() - indentation) / image
					.getWidth()) * 100;
			image.scalePercent(scaler); 
			
			 String css = "svg {" +
			            "shape-rendering: geometricPrecision;" +
			            "text-rendering:  geometricPrecision;" +
			            "color-rendering: optimizeQuality;" +
			            "image-rendering: optimizeQuality;" +
			            "}";
			    File cssFile = File.createTempFile("batik-default-override-", ".css");
			    FileUtils.writeStringToFile(cssFile, css);
			    
				    File svgFile = File.createTempFile("tempSvg", ".svg");
				    FileUtils.writeStringToFile(svgFile, "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +new String(decodedImage));
				    
				    String svg_URI_input = Paths.get(new File(context.getRealPath("")+"\\resources\\spider.svg").getPath()).toUri().toURL().toString();
				    TranscoderInput input_svg_image = new TranscoderInput(svg_URI_input);        
			        //Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
			        ByteArrayOutputStream png_ostream = new ByteArrayOutputStream();
			        TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);              
			        // Step-3: Create PNGTranscoder and define hints if required
			        PNGTranscoder my_converter = new PNGTranscoder();        
			        // Step-4: Convert and Write output
			        my_converter.transcode(input_svg_image, output_png_image);
			        png_ostream.flush();
				    
			Image image1 = Image.getInstance(png_ostream.toByteArray());
			System.out.println("Print image1 ==>" +image1);
			int indentation1 = 0;
			float scaler1 = ((document.getPageSize().getWidth()
					- document.leftMargin() - document.rightMargin() - indentation1) / image1
					.getWidth()) * 50;
			image1.scalePercent(scaler1);
			image1.setAbsolutePosition(40, 200);
			
			
			
			svg_URI_input = Paths.get(new File(context.getRealPath("")+"\\resources\\column.svg").getPath()).toUri().toURL().toString();
		    input_svg_image = new TranscoderInput(svg_URI_input);        
		        //Step-2: Define OutputStream to PNG Image and attach to TranscoderOutput
		    png_ostream = new ByteArrayOutputStream();
		    output_png_image = new TranscoderOutput(png_ostream);              
		        // Step-3: Create PNGTranscoder and define hints if required
		    my_converter = new PNGTranscoder();        
		        // Step-4: Convert and Write output
		    my_converter.transcode(input_svg_image, output_png_image);
		    png_ostream.flush();
			
			Image image2 = Image.getInstance(png_ostream.toByteArray());
			System.out.println("Print image2 ==>" +image2);
			int indentation2 = 0;
			float scaler2 = ((document.getPageSize().getWidth()
					 - document.rightMargin() - indentation2) / image2
					.getWidth()) * 42;
			image2.setAbsolutePosition(430, 210);
			image2.scalePercent(scaler2);
			
			//End of image

//			System.out.println("ParentArea name==>"+parentAreaName);
			PdfPTable googleMaptable = new PdfPTable(3);
			
			float[] googleMapColumnWidths = new float[] {8f,30f,30f};
			googleMaptable.setWidths(googleMapColumnWidths);
			 
			 PdfPCell googleMapCell0 = new PdfPCell(new Paragraph("Sl No",smallBold));
			 PdfPCell googleMapCell1 = new PdfPCell(new Paragraph("AREA NAME",smallBold));
//	         PdfPCell googleMapCell2 = new PdfPCell(new Paragraph("TIME PERIOD",smallBold));
	         PdfPCell googleMapCell3 = new PdfPCell(new Paragraph("DATA VALUE",smallBold));
	         
	         googleMapCell0.setBackgroundColor(BaseColor.LIGHT_GRAY);
	         googleMapCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
//	         googleMapCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
	         googleMapCell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
	         
	         googleMaptable.addCell(googleMapCell0);
	         googleMaptable.addCell(googleMapCell1);
//	         googleMaptable.addCell(googleMapCell2);
	         googleMaptable.addCell(googleMapCell3);
	         
	            if(googleMapData != null && googleMapData.size()>=1){
	            	int i = 1;
	    			for(GoogleMapData mapData : googleMapData){
	    				
	    				PdfPCell data0 = new PdfPCell(new Paragraph(Integer.toString(i),dataFont));
	    				PdfPCell data1 = new PdfPCell(new Paragraph(mapData.getTitle(),dataFont));
//		            	PdfPCell data2 = new PdfPCell(new Paragraph(timePeriod,dataFont));
		            	PdfPCell data3 = new PdfPCell(new Paragraph(Float.toString((float) mapData.getDataValue()),dataFont));
		            	
		            	data0.setBackgroundColor(cellColor);
		            	data1.setBackgroundColor(cellColor);
//		            	data2.setBackgroundColor(cellColor);
		            	data3.setBackgroundColor(cellColor);
		            	
		            	googleMaptable.addCell(data0);
		            	googleMaptable.addCell(data1);
//		            	googleMaptable.addCell(data2);
		            	googleMaptable.addCell(data3);
		            	
		            	i++;
	    			}
	            }
			
			PdfPTable spiderDataTable = new PdfPTable(3);
			
			   float[] spiderDatacolumnWidths = new float[] {8f,30f,30f};
			   spiderDataTable.setWidths(spiderDatacolumnWidths);
			
			 PdfPCell spiderDataCell0 = new PdfPCell(new Paragraph("Sl.No.",smallBold));
			 PdfPCell spiderDataCell1 = new PdfPCell(new Paragraph("INDICATOR",smallBold));
//	         PdfPCell spiderDataCell2 = new PdfPCell(new Paragraph("TIME PERIOD",smallBold));
	         PdfPCell spiderDataCell3 = new PdfPCell(new Paragraph("DATA VALUE",smallBold));
	         
	         spiderDataCell0.setBackgroundColor(BaseColor.LIGHT_GRAY);
	         spiderDataCell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
//	         spiderDataCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
	         spiderDataCell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
	         
	         spiderDataTable.addCell(spiderDataCell0);
	         spiderDataTable.addCell(spiderDataCell1);
//	         spiderDataTable.addCell(spiderDataCell2);
	         spiderDataTable.addCell(spiderDataCell3);
	         
	            if(spiderDataCollection.getDataCollection().size() > 0 && spiderDataCollection.getDataCollection().get(0)!=null && !spiderDataCollection.getDataCollection().get(0).isEmpty()){
	    			int i = 1;
	    			List<SpiderDataModel> listSpiderDataModel= spiderDataCollection.getDataCollection().get(0);
	    			for(SpiderDataModel spiderDataModel : listSpiderDataModel){
//	    				System.out.println("Indicator 1:"+spiderDataModel.getAxis());
//	    				System.out.println("  Value 1:"+spiderDataModel.getValue());
	    				
	    				PdfPCell data0 = new PdfPCell(new Paragraph(Integer.toString(i),dataFont));
	    				PdfPCell data1 = new PdfPCell(new Paragraph(spiderDataModel.getAxis(),dataFont));
//		            	PdfPCell data2 = new PdfPCell(new Paragraph(timePeriod,dataFont));
		            	PdfPCell data3 = new PdfPCell(new Paragraph(spiderDataModel.getValue(),dataFont));
		            	
		            	data0.setBackgroundColor(cellColor);
		            	data1.setBackgroundColor(cellColor);
//		            	data2.setBackgroundColor(cellColor);
		            	data3.setBackgroundColor(cellColor);
		            	
		            	spiderDataTable.addCell(data0);
		            	spiderDataTable.addCell(data1);
//		            	spiderDataTable.addCell(data2);
		            	spiderDataTable.addCell(data3);
	    				
			            if(spiderDataCollection.getDataCollection().size() > 1){
	    				if(spiderDataCollection.getDataCollection().get(1)!=null && !spiderDataCollection.getDataCollection().get(1).isEmpty()){
	    					
	    					List<SpiderDataModel> listSpiderDataModel1= spiderDataCollection.getDataCollection().get(1);
	    					for(SpiderDataModel spiderDataModel1 : listSpiderDataModel1){
	    						if(spiderDataModel.getAxis().equals(spiderDataModel1.getAxis())){
//	    							System.out.println("Indicator 2:"+spiderDataModel1.getAxis());
//	    							System.out.println("  Value 2:"+spiderDataModel1.getValue());
	    							
	    							PdfPCell data = new PdfPCell(new Paragraph("",dataFont));
	    							PdfPCell data4 = new PdfPCell(new Paragraph(spiderDataModel1.getAxis(),dataFont));
//	    			            	PdfPCell data5 = new PdfPCell(new Paragraph(secondTimeperiod,dataFont));
	    			            	PdfPCell data6 = new PdfPCell(new Paragraph(spiderDataModel1.getValue(),dataFont));
	    			            	
	    			            	spiderDataTable.addCell(data);
	    			            	spiderDataTable.addCell(data4);
//	    			            	spiderDataTable.addCell(data5);
	    			            	spiderDataTable.addCell(data6);
	    							
	    							break;
	    						}
	    						
	    					}
	    					
	    				}
			            
	    			}
	    		i++;	
	    		}
	    			
	    	}
	        document.add( blankSpace );
	        document.add( dashboardTitle );
	        document.add( googleMapParagraph);
	        document.add(image);
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add(googleMaptable);
	        document.newPage();
	        document.add(spiderDataParagraph);
	        document.add(image1);
	        document.add(image2);
			document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
	        document.add( Chunk.NEWLINE );
			document.add(spiderDataTable);
			
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.getOutputStream().flush();
	}

	@RequestMapping(value = "/exportToExcel", method = RequestMethod.POST)
	public String exportToExcel() {
		throw new RuntimeException("Still code to be written");
	}
	
	
	public SpiderDataCollection fetchSpidertData(String sector,String areaId,String timeperiodId,String secondTimeperiodId)
			throws Exception {

		//To do : Remove this hardcoded area Id
//		areaId = "IND021003020";

		//Populate the data collection.
		SpiderDataCollection spiderDataCollection = new SpiderDataCollection();
		
		//Fetch the data for the list of IUS Nids and the current TP
		spiderDataCollection= dashboardService.findSpiderDataByTimePeriod(
				sector, areaId, timeperiodId,secondTimeperiodId);

		return spiderDataCollection;
	}
	

	@RequestMapping(value = "/exportImage", method = RequestMethod.POST)
	@ResponseBody
	public String makeSvgToImage(@RequestParam("spider") String spiderChart,@RequestParam("column") String columnChart){
//		System.out.println("spider svg==>" +spiderChart);
//		System.out.println("column svg==>" +columnChart);
//		File file;
//		file = new File("D:/sarita_workspace");
		try {
			new FileOutputStream(new File(context.getRealPath("")+"\\resources\\spider.svg")).write(spiderChart.getBytes());
			new FileOutputStream(new File(context.getRealPath("")+"\\resources\\column.svg")).write(columnChart.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "imagePath";
		
	}
	
	
}

