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
public class LoginDBAdapter {
    public boolean isUser(String userName,String password) throws SQLException, ClassNotFoundException{
        
        String sql = "select * from user_login where user_name = ? and password = password(?)";
        Object[] dataE = {userName,password};
        ResultSet data = DBHandle.getData(DBConnection.getConnectionToDB(), sql , dataE);
        
        if(data.first()){
            return true;
        }else{
            return false;
        }
    }
}
