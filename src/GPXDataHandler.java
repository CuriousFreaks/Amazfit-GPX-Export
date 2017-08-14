import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class GPXDataHandler {
	private File gpxFile;
	private FileWriter fw;
	private BufferedWriter bw;
	File logFile = null;
	Logger logger=null;
	FileHandler filehandler = null;
	
	public GPXDataHandler(String fileName) throws IOException {
		//logging intialize
		 logFile = new File(String.valueOf("./log.txt"));
		 logger =Logger.getLogger(GPXExport.class.getName());
		 try {
				filehandler = new FileHandler("./log.txt", true);
				logger.addHandler(filehandler);
				filehandler.setFormatter(new SimpleFormatter());
			} catch (SecurityException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 //constructor code
		 String finalName= "./gpxdata/"+fileName+".xml";
		 logger.info(finalName);;
		 gpxFile = new File(finalName);
		 if(gpxFile.exists())
		 {
			 gpxFile.delete();
		 }
		 gpxFile.createNewFile();
		 fw = new FileWriter(gpxFile);
		 bw = new BufferedWriter(fw);
		 String locationHeader="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<gpx version=\"1.1\" creator=\"Curious Freaks\">\n\t<trk>\n\t\t<trkseg>";
		 bw.write(locationHeader);
		 logger.info(locationHeader);
	 
	}
	public void writeLattitude(double latitude) {
		try {
			 //<trkpt lat="17.47027" 
			 bw.write("\n\t\t\t<trkpt lat=\""+latitude+"\" ");
			 logger.info("Latitude:"+latitude);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void writeLongitude(double longitude) {
		try {
			 //lon="78.36706"></trkpt>
			 bw.write("lon=\""+longitude+"\"></trkpt>");
			 logger.info("Longitude :"+longitude);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void closeFile() {
		/* </trkseg>
		</trk>
		</gpx>*/
		try {
			String footer="\n\t\t</trkseg>\n\t</trk>\n</gpx>";
			 bw.write(footer);
			 logger.info(footer);
			 bw.flush();
			 bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
}
