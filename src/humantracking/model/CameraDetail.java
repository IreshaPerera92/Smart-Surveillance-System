/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package humantracking.model;

/**
 *
 * @author Darshana Priyasad
 */
public class CameraDetail {
    
    private String id;
    private String location;
    private int device_id;

    public CameraDetail() {
    }

    public CameraDetail(String id, String location, int device_id) {
        this.id = id;
        this.location = location;
        this.device_id = device_id;
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
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the device_id
     */
    public int getDevice_id() {
        return device_id;
    }

    /**
     * @param device_id the device_id to set
     */
    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }
    
    
    
}
