/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package humantracking.model;

import java.util.Date;

/**
 *
 * @author Darshana Priyasad
 */
public class SurvelianceVideo {
    
    private String id;
    private String video_name;
    private String video_location;
    private Date date;
    private Date time;
    private boolean analyzed;
    private boolean detected;

    public SurvelianceVideo() {
    }

    public SurvelianceVideo(String id, String video_name, String video_location, Date date, Date time, boolean analyzed, boolean detected) {
        this.id = id;
        this.video_name = video_name;
        this.video_location = video_location;
        this.date = date;
        this.time = time;
        this.analyzed = analyzed;
        this.detected = detected;
    }

    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the video_name
     */
    public String getVideo_name() {
        return video_name;
    }

    /**
     * @param video_name the video_name to set
     */
    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    /**
     * @return the video_location
     */
    public String getVideo_location() {
        return video_location;
    }

    /**
     * @param video_location the video_location to set
     */
    public void setVideo_location(String video_location) {
        this.video_location = video_location;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return the analyzed
     */
    public boolean isAnalyzed() {
        return analyzed;
    }

    /**
     * @param analyzed the analyzed to set
     */
    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }

    /**
     * @return the detected
     */
    public boolean isDetected() {
        return detected;
    }

    /**
     * @param detected the detected to set
     */
    public void setDetected(boolean detected) {
        this.detected = detected;
    }

    
    
}
