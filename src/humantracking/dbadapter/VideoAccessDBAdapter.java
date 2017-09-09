/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package humantracking.dbadapter;

import humantracking.dbaccess.DBConnection;
import humantracking.dbaccess.DBHandle;
import humantracking.model.SurvelianceVideo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Darshana Priyasad
 */
public class VideoAccessDBAdapter {
    
    
    public List<SurvelianceVideo> getDetails() throws SQLException, ClassNotFoundException{
        
        String sql = "select * from video_detail";
        ResultSet data = DBHandle.getData(DBConnection.getConnectionToDB(), sql);
        List<SurvelianceVideo> video_list = new ArrayList<>();
        while(data.next()){
            
            String id = data.getString(1);
            String video_name = data.getString(2);
            String video_location = data.getString(3);
            Date date = data.getDate(4);
            Date time = data.getTime(5);
            boolean analized = data.getBoolean(6);
            boolean detected = data.getBoolean(6);
            SurvelianceVideo temp_video = new SurvelianceVideo(id, video_name, video_location, date, time,analized,detected);
            video_list.add(temp_video);
        }
        return video_list;
    }
    
    public boolean setDetails(Object[] data) throws SQLException, ClassNotFoundException{
        
        String sql = "insert into video_detail values(?,?,?,?,?,?,?)";
        boolean setData = DBHandle.setData(DBConnection.getConnectionToDB(), sql,data);
        
        return setData;
    }
    
    
    public int getCount() throws SQLException, ClassNotFoundException{
        
        String sql = "select count(*) from video_detail";
        ResultSet data = DBHandle.getData(DBConnection.getConnectionToDB(), sql);
        int count = 0;
       while(data.next()){
            
            count = data.getInt(1);
        }
        
        return count;
    }
    
    
    public boolean getAvailable(String videoName) throws SQLException, ClassNotFoundException{
        
        String sql = "select * from video_detail where video_name=?";
        Object[] datas = {videoName};
        ResultSet data = DBHandle.getData(DBConnection.getConnectionToDB(), sql,datas);
        
        return data.first();
        
    }
    
    
    
    
    
    
    public List<SurvelianceVideo> getSuspDetails() throws SQLException, ClassNotFoundException{
        
        String sql = "select * from video_detail where analyzed = true and human_detected = true";
        ResultSet data = DBHandle.getData(DBConnection.getConnectionToDB(), sql);
        List<SurvelianceVideo> video_list = new ArrayList<>();
        while(data.next()){
            
            String id = data.getString(1);
            String video_name = data.getString(2);
            String video_location = data.getString(3);
            Date date = data.getDate(4);
            Date time = data.getTime(5);
            boolean analized = data.getBoolean(6);
            boolean detected = data.getBoolean(6);
            SurvelianceVideo temp_video = new SurvelianceVideo(id, video_name, video_location, date, time,analized,detected);
            video_list.add(temp_video);
        }
        return video_list;
    }
    
    
    public boolean updateDetails(Object[] data) throws SQLException, ClassNotFoundException{
        
        String sql = "update video_detail set analyzed = 1 ,human_detected = 1 where id=? ";
        boolean setData = DBHandle.setData(DBConnection.getConnectionToDB(), sql,data);
        
        return setData;
    }
}
