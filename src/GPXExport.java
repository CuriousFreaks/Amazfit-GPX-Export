import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;

public class GPXExport {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File directory = new File(String.valueOf("./CuriousFreaks"));
		File gpxdata = new File(String.valueOf("./gpxdata"));
		File logDir = new File(String.valueOf("./log"));
		Logger logger =Logger.getLogger(GPXExport.class.getName());
		FileHandler filehandler = null;
		
		
		if(!logDir.exists())
			logDir.mkdir();
		else
		{
			deleteRecursive(logDir);
			logDir.mkdir();
		}
		try {
			filehandler = new FileHandler("./log/log.txt", true);
			logger.addHandler(filehandler);
			filehandler.setFormatter(new SimpleFormatter());
		} catch (SecurityException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(!directory.exists())
		{
			directory.mkdir();
			logger.warning("CuriousFreaks directory already exists.. deleting now");
		}else
		{
			deleteRecursive(directory);
			directory.mkdir();
		}
		
		if(!gpxdata.exists())
		{
			gpxdata.mkdir();
			logger.warning("gpxdata directory already exists.. deleting now");
		}else
		{
			deleteRecursive(gpxdata);
			gpxdata.mkdir();
		}
		//type is as command1 && command2 ...
		String commands="cd ./CuriousFreaks && echo %cd% && adb devices -l && adb backup -f ./app_data.ab -noapk com.huami.watch.sport && java -jar ../abe.jar unpack ./app_data.ab ./app_data.tar && \"../TarTool\" -x ./app_data.tar ./app_data";
		logger.info(commands);
		Process p = null;
	    String output = null;
		try {
			    logger.info("start command process");
				ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", commands);
			    builder.redirectErrorStream(true);
			    p = builder.start();
			    JOptionPane.showMessageDialog(null, "Please unlock your device and confirm the backup operation", "Curious Freaks", JOptionPane.INFORMATION_MESSAGE);
			    BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
			    while (true)
			    {
			    	output=r.readLine();
			    	if(output==null) break;
			    	logger.info(output);
			    }
			    logger.info("command process ends");
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		// extracting .db data
		logger.info("Starting db Connection");
		Connection con=null;
		try {
			logger.info("Current directory"+System.getProperty("user.dir"));
			Class.forName("org.sqlite.JDBC");
			con=DriverManager.getConnection("jdbc:sqlite:CuriousFreaks\\app_data\\apps\\com.huami.watch.sport\\db\\sport_data.db");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from location_data");
			ArrayList<sport_data> sport_data_list = new ArrayList<>();
			while(rs.next())
			{
				sport_data sport_data_item = new sport_data();
				sport_data_item.setTrack_id(rs.getLong("track_id"));
				sport_data_item.setLatitude(rs.getDouble("latitude"));
				sport_data_item.setLongitude(rs.getDouble("longitude"));
				//sport_data_item.setTimestamp(rs.getTimestamp("timestamp"));
				sport_data_list.add(sport_data_item);
			}
			rs=stmt.executeQuery("select * from sport_summary");
			ArrayList<sport_summary> sport_summary_list = new ArrayList<>();
			while(rs.next())
			{
				sport_summary sport_summary_item= new  sport_summary();
				sport_summary_item.setTrack_id(rs.getLong("track_id"));
				sport_summary_item.setStart_time(rs.getLong("start_time"));
				sport_summary_list.add(sport_summary_item);
			}
			GPXDataHandler gpxDataHandle = null;
			int i=1;
			for(sport_summary sport_summary_item:sport_summary_list)
			{
				Long summary_track_id=sport_summary_item.getTrack_id();
				String fname=timestampToDate(sport_summary_item.getStart_time(), "filename");
				String creationTime=timestampToDate(sport_summary_item.getStart_time(),"timeformate");
				System.out.println("File Name :"+creationTime);
				if(gpxDataHandle!=null)
				{
					gpxDataHandle.closeFile();
				}
				gpxDataHandle = new GPXDataHandler(fname,creationTime,i);i++;
				for(sport_data sport_data_item:sport_data_list)
				{
					long sport_track_id=sport_data_item.getTrack_id();
					if(sport_track_id==summary_track_id)
						gpxDataHandle.writeLocationData(sport_data_item.getLatitude(), sport_data_item.getLongitude(), timestampToDate(sport_data_item.getTimestamp(), "timeformate"));
				}					
			}
			gpxDataHandle.closeFile();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "GPX data export process has completed. Please check gpxdata folder for all gpx files.", "Curious Freaks", JOptionPane.INFORMATION_MESSAGE);
		logger.info("completed.. exit now");
		System.exit(0);	
	}
	private static String timestampToDate(long val, String ftype){
		System.out.println("Val:  "+val);
		Date d = new Date((long)val);
		DateFormat f = null;
		if(ftype.equals("filename"))
		{
			f = new SimpleDateFormat("dd-MMM-yyyy_H.mm_a");
			System.out.println(f.format(d));
		}
		if(ftype.equals("timeformate"))
		{
			f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'");
			System.out.println(f.format(d));
		}
		return f.format(d);
	}
	public static void deleteRecursive(File parent){
	    File[] files = parent.listFiles();
	    for (File f : files){
	        if (f.isDirectory()){
	            System.out.println("Deleting file:" + f.getName());
	            deleteRecursive(f);
	            f.delete();
	        } else {
	            f.delete();
	        }
	    }
	    parent.delete();
	}
}
