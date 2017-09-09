/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package humantracking.dbadapter;

import humantracking.dbaccess.DBConnection;
import humantracking.dbaccess.DBHandle;
import humantracking.model.CameraDetail;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Darshana Priyasad
 */
public class CameraDBAdapter {
    
    public List<CameraDetail> getCameraDetails() throws SQLException, ClassNotFoundException{
        
        String sql = "select * from camera_detail";
        ResultSet data = DBHandle.getData(DBConnection.getConnectionToDB(), sql);
        List<CameraDetail> video_list = new ArrayList<>();
        while(data.next()){
            
            String id = data.getString(1);
            
            String video_location = data.getString(2);
            
            int device_id = data.getInt(3);
            
            CameraDetail detail = new CameraDetail(id, video_location, device_id);
            
            video_list.add(detail);
        }
        return video_list;
    }
}
