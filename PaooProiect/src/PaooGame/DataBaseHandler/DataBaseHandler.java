package PaooGame.DataBaseHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
//clasa care se ocupa de executare query urilor
public class DataBaseHandler {
    public DataBaseHandler(){}
    public ArrayList<DataBaseData> GetDatabaseData()
    {
        Connection c = null;
        Statement stmt = null;
        ArrayList<DataBaseData> data = new ArrayList<>();
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DataBaseGame.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String result = "SELECT * FROM PrincipalTable ORDER BY ID DESC limit 5;";
            ResultSet rs = stmt.executeQuery(result);
            while(rs.next()){
                    data.add(new DataBaseData(rs.getInt("ID"),
                                            rs.getString("PlayerName"),
                                            rs.getInt("PositionX"),
                                            rs.getInt("PositionY"),
                                            rs.getInt("Map"),
                                            rs.getInt("Score"),
                                            rs.getInt("NumberLifes")));
            }
            rs.close();
            stmt.close();
            c.close();
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return data;
    }
    public void SaveLoad(DataBaseData dbData)
    {
        Connection c = null;
        Statement stmt = null;
        ArrayList<DataBaseData> data = new ArrayList<>();
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DataBaseGame.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String insertData = "INSERT INTO PrincipalTable (PlayerName,PositionX,PositionY,Map,Score,NumberLifes) VALUES('" + dbData.name + "'," +
                                 dbData.positionX + "," + dbData.positionY + "," + dbData.mapID + "," + dbData.score + "," + dbData.numLifes + ")";
            stmt.executeUpdate(insertData);
            stmt.close();
            c.commit();
            c.close();
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


}
