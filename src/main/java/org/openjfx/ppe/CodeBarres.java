package org.openjfx.ppe;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

public class CodeBarres {
	public static File SetcreateImage(String nbCode)  {

		try {
		      Code128Bean bean = new Code128Bean();
		      final int dpi = 200;

		      //Configure the barcode generator
		      bean.setModuleWidth(UnitConv.in2mm(2.8f / dpi));
		      bean.doQuietZone(false);

		      //Open output file
		      File outputFile = new File("IMG/" + nbCode + ".JPG");
		      FileOutputStream out = new FileOutputStream(outputFile);
		      BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);

		      //Generate the barcode
		      bean.generateBarcode(canvas, nbCode);
		   
		      //Signal end of generation
		      canvas.finish();
		    
		      System.out.println("Bar Code is generated successfully…");
		      
		      return outputFile;
		    }
		    catch (IOException ex) {
		      ex.printStackTrace();
		    }
			
		return null;
		
	}
	
	public static String SetCodeBarre() {
		String pattern = "ddMMyyHHmm";
		SimpleDateFormat dateTimeFormatCodebarres = new SimpleDateFormat(pattern);
		String dateEntree = dateTimeFormatCodebarres.format(new Date());
		int randomNb = (int) Math.floor( Math.random() * 90 ) + 10;
		String nbCodeBarres = randomNb + "" + dateEntree;
		
		return nbCodeBarres;
     }
}
