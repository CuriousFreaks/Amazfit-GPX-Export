import java.sql.Timestamp;

public class sport_data {
private long track_id;
private long point_index;
private double latitude;
private double longitude;
private Timestamp timestamp;
private long bar; 
private double altitude;
private double accuracy;
private double speed;
private long algo_point_type;
private long course;
private String extra;
private long point_type;
public long getTrack_id() {
	return track_id;
}
public void setTrack_id(long track_id) {
	this.track_id = track_id;
}
public long getPoint_index() {
	return point_index;
}
public void setPoint_index(long point_index) {
	this.point_index = point_index;
}
public double getLatitude() {
	return latitude;
}
public void setLatitude(double latitude) {
	this.latitude = latitude;
}
public double getLongitude() {
	return longitude;
}
public void setLongitude(double longitude) {
	this.longitude = longitude;
}
public Timestamp getTimestamp() {
	return timestamp;
}
public void setTimestamp(Timestamp timestamp) {
	this.timestamp = timestamp;
}
public long getBar() {
	return bar;
}
public void setBar(long bar) {
	this.bar = bar;
}
public double getAltitude() {
	return altitude;
}
public void setAltitude(double altitude) {
	this.altitude = altitude;
}
public double getAccuracy() {
	return accuracy;
}
public void setAccuracy(double accuracy) {
	this.accuracy = accuracy;
}
public double getSpeed() {
	return speed;
}
public void setSpeed(double speed) {
	this.speed = speed;
}
public long getAlgo_point_type() {
	return algo_point_type;
}
public void setAlgo_point_type(long algo_point_type) {
	this.algo_point_type = algo_point_type;
}
public long getCourse() {
	return course;
}
public void setCourse(long course) {
	this.course = course;
}
public String getExtra() {
	return extra;
}
public void setExtra(String extra) {
	this.extra = extra;
}
public long getPoint_type() {
	return point_type;
}
public void setPoint_type(long point_type) {
	this.point_type = point_type;
}
}
