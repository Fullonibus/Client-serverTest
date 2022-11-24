package com.app.server;


import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataControll {
    public static String url = "jdbc:mysql://localhost:3306/amogus";
    public static String username = "root";
    public static String password = "Cthljkbr17#";
    public void saveFile(String table, File file) throws SQLException, ClassNotFoundException, IOException {
        String INSERT_FILE = "INSERT INTO " + table + "(new_tablecol) values (?)";
        FileInputStream fis = new FileInputStream(file);

        try {
            //Connection connection = getDbConn();
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println(file.length());
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(INSERT_FILE);
            ps.setBinaryStream(1, fis, (int) file.length());
            ps.executeUpdate();
            connection.commit();
            connection.close();
            fis.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void saveMap(String table, String name, String description, File file) throws SQLException, FileNotFoundException {

        try {
            PreparedStatement ps;
            String INSERTMAP = "INSERT INTO " + table + "(lot_name, lot_description, new_tablecol) values (?, ?, ?)";
            FileInputStream fis = new FileInputStream(file);
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(INSERTMAP);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setBinaryStream(3, fis, (int) file.length());

            ps.executeUpdate();
            connection.commit();
            connection.close();
            fis.close();
        }
        catch (Exception e){e.printStackTrace();}
    }

    public List<Map<String, Object>> requestAllData() throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("DB Connected1");
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            ResultSet rs;
            Statement st = connection.createStatement();
            int id = 0;
            rs = st.executeQuery("SELECT t.idnew_table, t.lot_name, t.lot_description from new_table as t");
            while (rs.next()) {
                //System.out.println("Inserting...");
                HashMap dbRow = new HashMap<String, Object>();
                dbRow.put("idnew_table", (Integer)rs.getInt("idnew_table"));
                dbRow.put("lot_name", rs.getString("lot_name"));
                dbRow.put("lot_description", rs.getString("lot_description"));
                result.add(dbRow);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("DB Connected11");
        return result;
    }

    public File getImageById(int id) throws SQLException {
        File result = null;

        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("DB Connected");

        try {
            ResultSet rs;
            PreparedStatement ps = connection.prepareStatement("SELECT t.new_tablecol from new_table as t where t.idnew_table = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            byte buff[] = new byte[1024];
           if(rs.next()) {
               InputStream is = rs.getBinaryStream(1);

               Blob blob = rs.getBlob(1);
               InputStream in = blob.getBinaryStream();


               result = new File("newimage.jpg");

               FileOutputStream fos =
                       new FileOutputStream(result);

               for (int b = is.read(buff); b != -1; b = is.read(buff)) {
                   fos.write(buff, 0, b);
               }

               in.close();
               fos.close();
           }
            while (rs.next()) {
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
