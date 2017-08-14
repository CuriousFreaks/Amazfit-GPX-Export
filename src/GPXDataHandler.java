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
	Logger logger=null;
	FileHandler filehandler = null;
	
	public GPXDataHandler(String filename, String creationTime, int counter) throws IOException {
		//logging intialize
		 logger =Logger.getLogger(GPXExport.class.getName());
		 try {
				filehandler = new FileHandler("./log/log.txt", true);
				logger.addHandler(filehandler);
				filehandler.setFormatter(new SimpleFormatter());
			} catch (SecurityException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 //constructor code
		 String finalName= "./gpxdata/"+filename+"_"+counter+".xml";
		 logger.info(finalName);;
		 gpxFile = new File(finalName);
		 if(gpxFile.exists())
		 {
			 gpxFile.delete();
		 }
		 gpxFile.createNewFile();
		 fw = new FileWriter(gpxFile);
		 bw = new BufferedWriter(fw);
		 String locationHeader="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
				 locationHeader += "<gpx creator=\"CuriousFreaks\" version=\"1.1\" xmlns=\"http://www.topografix.com/GPX/1/1\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd http://www.garmin.com/xmlschemas/GpxExtensions/v3 http://www.garmin.com/xmlschemas/GpxExtensionsv3.xsd http://www.garmin.com/xmlschemas/TrackPointExtension/v1 http://www.garmin.com/xmlschemas/TrackPointExtensionv1.xsd\" xmlns:gpxtpx=\"http://www.garmin.com/xmlschemas/TrackPointExtension/v1\" xmlns:gpxx=\"http://www.garmin.com/xmlschemas/GpxExtensions/v3\">\n";
				 locationHeader += "<metadata>\n\t<time>"+creationTime+"</time>\n</metadata>\n<trk>\n\t<name>My activity "+counter+" - "+creationTime+" </name>\n\t<trkseg>";
		 bw.write(locationHeader);
		 logger.info(locationHeader);
	 
	}
	public void writeLocationData(double latitude, double longitude, String timestampFormatted) {
		// TODO Auto-generated method stub
		try {
			/* <trkpt lat="17.4486120" lon="78.3741750">
			    <time>2017-08-09T13:35:51Z</time>
			   </trkpt>*/
			String str="\n\t\t\t<trkpt lat=\""+latitude+"\" lon=\""+longitude+"\">"
					   +"\n\t\t\t\t<time>"+timestampFormatted+"</time>"
					    +"\n\t\t\t</trkpt>";
			 bw.write(str);
			 logger.info("location data :"+str);
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
