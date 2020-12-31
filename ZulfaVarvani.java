import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.geom.*;
import java.lang.Math.*;
import java.nio.file.*;

public class ZulfaVarvani {
	
	private static BufferedImage inputImage;
	private static JLabel inputImg = new JLabel();
	private static ImageIcon icon = new ImageIcon();
	private static File file;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("ImageEditorGUI");
		JPanel panel = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu optionsMenu = new JMenu("Options");
		menuBar.add(fileMenu);
		menuBar.add(optionsMenu);
		//file menu items
		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openMenuItem.addActionListener(new SelectFile());
		JMenuItem saveAsMenuItem = new JMenuItem("Save As");
		saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveAsMenuItem.addActionListener(new SelectFile());
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		exitMenuItem.addActionListener(new SelectFile());
		//options menu items
		JMenuItem restoreMenuItem = new JMenuItem("Restore to Original");
		restoreMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		restoreMenuItem.addActionListener(new ImageOptions());
		JMenuItem hFlipMenuItem = new JMenuItem("Horizontal Flip");
		hFlipMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		hFlipMenuItem.addActionListener(new ImageOptions());
		JMenuItem vFlipMenuItem = new JMenuItem("Vertical Flip");
		vFlipMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		vFlipMenuItem.addActionListener(new ImageOptions());
		JMenuItem gScaleMenuItem = new JMenuItem("Gray Scale");
		gScaleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		gScaleMenuItem.addActionListener(new ImageOptions());
		JMenuItem sToneMenuItem = new JMenuItem("Sepia Tone");
		sToneMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		sToneMenuItem.addActionListener(new ImageOptions());
		JMenuItem invertColourMenuItem = new JMenuItem("Invert Colour");
		invertColourMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		invertColourMenuItem.addActionListener(new ImageOptions());
		JMenuItem gBlurMenuItem = new JMenuItem("Gaussian Blur");
		gBlurMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		gBlurMenuItem.addActionListener(new ImageOptions());
		JMenuItem bulgeMenuItem = new JMenuItem("Bulge Effect");
		bulgeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		bulgeMenuItem.addActionListener(new ImageOptions());
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		
		optionsMenu.setMnemonic(KeyEvent.VK_O);
		optionsMenu.add(restoreMenuItem);
		optionsMenu.addSeparator();
		optionsMenu.add(hFlipMenuItem);
		optionsMenu.add(vFlipMenuItem);
		optionsMenu.add(gScaleMenuItem);
		optionsMenu.add(sToneMenuItem);
		optionsMenu.add(invertColourMenuItem);
		optionsMenu.add(gBlurMenuItem);
		optionsMenu.add(bulgeMenuItem);
		
		panel.add(inputImg);
		frame.setJMenuBar(menuBar);
		frame.setVisible(true);
		frame.setSize(1000, 1000);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
	}
	
	public static void loadImage(JLabel input, File fileName) throws IOException {//loads image
		try {
			inputImage = ImageIO.read(fileName);
			int w = inputImage.getWidth();
			int h = inputImage.getHeight();
			if (w < 1000 && h < 1000) {
				ImageIcon inputImg = new ImageIcon(inputImage);
		        input.setIcon(inputImg);
			} else {
				input.setText("File too large");
			}
		} catch (FileNotFoundException exception){
			System.out.println("File can't load");
		}
	}
	
	private static class SelectFile implements ActionListener {
		
    	public void actionPerformed(ActionEvent event) {
    		JFileChooser fc = new JFileChooser();
 			if ((((JMenuItem)event.getSource()).getText()).equals("Open")) {
 				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                	file = fc.getSelectedFile();
                	try {
                		loadImage(inputImg, file);//opens the selected file
					} catch (IOException exception) {
					}
 				}
 			} else if ((((JMenuItem)event.getSource()).getText()).equals("Save As")) {
 				if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                	File f = fc.getSelectedFile();
                	try {
                		ImageIO.write(inputImage, "png", f);//saves image as png
                		Path temp = Files.move(Paths.get(file.getAbsolutePath()), Paths.get(f.getAbsolutePath()));//moves file or directory
                	} catch (IOException exception) {
                	}
 				}
 			} else if ((((JMenuItem)event.getSource()).getText()).equals("Exit")) {
 				System.exit(0);
 			} 
        }
    }
    
    private static class ImageOptions implements ActionListener {
    	
    	public void actionPerformed(ActionEvent event) {
    		if ((((JMenuItem)event.getSource()).getText()).equals("Restore to Original")) {
    			try {
               		loadImage(inputImg, file);//restores image by reloading file
				} catch (IOException exception) {
				}
    		} else if ((((JMenuItem)event.getSource()).getText()).equals("Horizontal Flip")) {//horizontal flip
    			inputImage = flipHorizontal(inputImage);
    			print(icon, inputImg, inputImage);
    		} else if ((((JMenuItem)event.getSource()).getText()).equals("Vertical Flip")) {//vertical flip
    			inputImage = flipVertical(inputImage);
    			print(icon, inputImg, inputImage);
    		} else if ((((JMenuItem)event.getSource()).getText()).equals("Gray Scale")) { //gray
    			inputImage = gray(inputImage);
    			print(icon, inputImg, inputImage);
    		} else if ((((JMenuItem)event.getSource()).getText()).equals("Sepia Tone")) {//sepia
    			inputImage = sepia(inputImage);
    			print(icon, inputImg, inputImage);
    		} else if ((((JMenuItem)event.getSource()).getText()).equals("Invert Colour")) {//invert
    			inputImage = invert(inputImage);
    			print(icon, inputImg, inputImage);
    		} else if ((((JMenuItem)event.getSource()).getText()).equals("Gaussian Blur")) {//blur
    			inputImage = gaussianBlur(inputImage, 5);
    			print(icon, inputImg, inputImage);
    		} else if ((((JMenuItem)event.getSource()).getText()).equals("Bulge Effect")) {//bulge
    			inputImage = bulgeEffect(inputImage);
    			print(icon, inputImg, inputImage);
    		}
    	}
    }
	
	public static BufferedImage flipHorizontal(BufferedImage src){
	    AffineTransform x = AffineTransform.getScaleInstance(-1, 1);
	    x.translate(-src.getWidth(null), 0);
	    AffineTransformOp op = new AffineTransformOp(x, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    src = op.filter(src, null);
	     
	    return src;
    }
    
    public static BufferedImage flipVertical(BufferedImage i) {
    	AffineTransform y = AffineTransform.getScaleInstance(1, -1);
    	y.translate(0, -i.getHeight(null));
    	AffineTransformOp o = new AffineTransformOp(y, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    	i = o.filter(i, null);
    	
    	return i;
    }
    
    public static BufferedImage gray(BufferedImage sourceImg) {
    	for(int w = 0; w < sourceImg.getWidth(); w++){
	      for(int h = 0; h < sourceImg.getHeight(); h++){
	        int p = sourceImg.getRGB(w, h); //gets pixel value at image coordinates
	        int a = (p >> 24);
	        int r = (p >> 16) & 255;
	        int g = (p >> 8) & 255;
	        int b = p & 255;
	
	        int gray = (r + g + b) / 3;//gray is average of rgb values
	        p = (a << 24) | (gray << 16) | (gray << 8) | gray;
	        sourceImg.setRGB(w, h, p);//sets new value
	      }
	    }
		return sourceImg;
    }
    
    public static BufferedImage invert(BufferedImage imageSource) {
    	for (int i = 0; i < imageSource.getWidth(); i++) {
    		for (int j = 0; j < imageSource.getHeight(); j++) {
				int p = imageSource.getRGB(i,j);
				int a = (p >> 24);
				int r = (p >> 16) & 255;
				int g = (p >> 8) & 255;
				int b = p & 255;
				//sets color pixel to negative by subtracting rgb from 255
				r = 255 - r;
				g = 255 - g;
				b = 255 - b;
				p = (a << 24) | (r << 16) | (g << 8) | b;//new pixel value
				imageSource.setRGB(i, j, p);//
    		}
    	}
    	return imageSource;
    }
    
    public static BufferedImage sepia(BufferedImage bImage) {
		for(int x = 0; x < bImage.getWidth(); x++){
			for(int y = 0; y < bImage.getHeight(); y++){
				int pixels = bImage.getRGB(x, y);
				int a = (pixels >> 24);
				int r = (pixels >> 16) & 255;
				int g = (pixels >> 8) & 255;
				int b = pixels & 255;
				//converts from color to sepia pixel
				int sr = (int)((0.393 * r) + (0.769 * g) + (0.189 * b));
				int sg = (int)((0.349 * r) + (0.686 * g) + (0.168 * b));
				int sb = (int)((0.272 * r) + (0.534 * g) + (0.131 * b));
				
				r = (sr > 255) ? 255 : sr;
				g = (sg > 255) ? 255 : sg;
				b = (sb > 255) ? 255 : sb;
				pixels = (a << 24) | (r << 16) | (g << 8) | b;//new pixel values
				bImage.setRGB(x, y, pixels);
			}
		}
		return bImage;
    }
    //applies gaussian math things to each r g b array
    public static int[][] arrayRGB(int[][] imgArrayRGB, BufferedImage image, int radius, double[][] weightMatrix) {
    	int[][] tempImageArray = new int[image.getWidth()][image.getHeight()];
		for(int i = 0; i < tempImageArray.length; i++) {
			for(int j = 0; j < tempImageArray[i].length; j++) {
				double[][] gaussian = new double[radius][radius];
				int x = i - radius;
				int s = 0;
				for(int m = 0; m < gaussian.length; m++) {
					int y = j - radius;
					for(int n = 0; n < gaussian[m].length; n++) {
						if(x < 0 || y < 0 || x >= imgArrayRGB.length || y >= imgArrayRGB[i].length) {
							gaussian[m][n] = 0;
						} else {
							gaussian[m][n] = imgArrayRGB[x][y] * weightMatrix[m][n];
						}
						s += gaussian[m][n];
						y++;
					}
					x++;
				}
				tempImageArray[i][j] = s;
				s = 0;
			}
		}
		return tempImageArray;
    }
    
    public static BufferedImage gaussianBlur(BufferedImage image, int radius) {
		int[][] imageArray = new int[image.getWidth()][image.getHeight()];
		int[][] imageArrayR = new int[image.getWidth()][image.getHeight()];//arrays for the separate r g b
		int[][] imageArrayG = new int[image.getWidth()][image.getHeight()];
		int[][] imageArrayB = new int[image.getWidth()][image.getHeight()];
		for(int i = 0; i < imageArray.length; i++) {
			for(int j = 0; j < imageArray[i].length; j++) {
				imageArray[i][j] = image.getRGB(i, j);
				Color color = new Color(image.getRGB(i, j));//separate into r g b and puts it in appropriate array
				imageArrayR[i][j] = color.getRed();
				imageArrayG[i][j] = color.getGreen();
				imageArrayB[i][j] = color.getBlue();
			}
		}

		double[][] weightMatrix = new double[radius][radius];
		double sum = 0;
		for(int i = 0; i < weightMatrix.length; i++) {
			for(int j = 0; j < weightMatrix[i].length; j++) {
				int x = i - (weightMatrix.length/2);
				int y = (weightMatrix.length/2) - j;
				weightMatrix[i][j] = (1 / (2 * Math.PI * Math.pow(1.5, 2)) * Math.exp(-(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(1.5, 2))));//eqn for weight
				sum += weightMatrix[i][j];
			}
		}
		for(int i = 0; i < weightMatrix.length; i++) {
			for(int j = 0; j < weightMatrix[i].length; j++) {
				weightMatrix[i][j] /= sum;
			}
		}
		//applies weight matrix on each r g b
		imageArrayR = arrayRGB(imageArrayR, image, radius, weightMatrix);
		imageArrayG = arrayRGB(imageArrayG, image, radius, weightMatrix);
		imageArrayB = arrayRGB(imageArrayB, image, radius, weightMatrix);

		for(int i = 0; i < imageArrayR.length; i++) {
			for(int j = 0; j < imageArrayR[i].length; j++) {
				Color color = new Color(imageArrayR[i][j], imageArrayG[i][j], imageArrayB[i][j]);//gets new color values for each rgb
				image.setRGB(i, j, color.getRGB());
			}
		}
		return image;
	}
	
	public static BufferedImage bulgeEffect(BufferedImage image) {
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        double cx = image.getWidth()/2;//centre of horizontal plane
        double cy = image.getHeight()/2; // centre of vertical plane
        double m = 20;
        double k = 1.5;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
            	//turns to polar
            	double r = Math.hypot(i - cx, j - cy);
            	double a = Math.atan2(j - cy, i - cx);
                double rn = Math.pow(r, k) / m;//applies transformation
                double sx = (rn * Math.cos(a)) + cx;//new x after applying eqn, shifting it by centre x 
                double sy = (rn * Math.sin(a)) + cy; //new y
                if (sx >= 0 && sx < image.getWidth() && sy >= 0 && sy < image.getHeight()) {
                	int p = image.getRGB((int)sx, (int)sy);
                	output.setRGB(i, j, p);//remaps new image coordinates
                }
            }
        }
		return output;
	}
    
    public static void print(ImageIcon ic, JLabel jLabel, BufferedImage bufImg) {
    	ic = new ImageIcon(bufImg);
    	jLabel.setIcon(ic);
    }
}