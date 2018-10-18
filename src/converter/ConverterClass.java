package converter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;

public class ConverterClass {
	public static void main(String[] args) throws IOException {
		System.out.println(new ConverterClass().convertImageToText(new File("src/images/eurotext_unlv.png")));
	}

	public String convertImageToText(File f)   {
		String imgText ="";
		try	{
			ITesseract instance = new Tesseract();
			instance.setDatapath("src/tessdata");
			BufferedImage orgin = ImageIO.read(f);
			BufferedImage textImage = ImageHelper.convertImageToGrayscale(orgin);
			int _width = textImage.getWidth();
			int _multiply = (_width * 5);
			int _height = textImage.getHeight();
			int _multiply_1 = (_height * 5);
			BufferedImage _scaledInstance = ImageHelper.getScaledInstance(textImage, _multiply, _multiply_1);
			textImage = _scaledInstance;
			imgText	 = instance.doOCR(textImage);
		}catch (Exception e){
			imgText = "";
		}
		return imgText;
	}
	
	public boolean writeFile(File f,String text)   {
		try	{
			PrintWriter pw = new PrintWriter(new FileWriter(f));
			pw.print(text);
			pw.close();
			return true;
		}catch (Exception e){
			return false;
		}
	}
}
