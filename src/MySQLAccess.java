import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLAccess
{
  private Connection conn = null;

  public void insert(String time, String ip, String request)
  {
    try
    {
      this.connect();

      String query = " INSERT INTO logs (time, ip, request)"
        + " VALUES (?, ?, ?";

      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setDate (1, Date.valueOf(time));
      preparedStmt.setString (2, ip);
      preparedStmt.setString   (3, request);

      preparedStmt.execute();
      
      conn.close();
    }
    catch (Exception e)
    {
      System.err.println(e.getMessage());
    }
  }

  public List<String> query(String startDate, String endDate, String threshold){

    List<String> results = new ArrayList<>();
    try
    {
      this.connect();

      // the mysql insert statement
      String query = "SELECT COUNT(*) AS count, ip" +
              "   FROM logs" +
              "   WHERE requestdate BETWEEN(? AND ?)" +
              "   GROUP BY ip" +
              "   HAVING (count > ?)";

      // create the mysql insert preparedstatement
      PreparedStatement ps = conn.prepareStatement(query);

      ps.setDate (1, Date.valueOf(startDate));
      ps.setDate (2, Date.valueOf(endDate));
      ps.setInt   (3, Integer.parseInt(threshold));

      ResultSet rs = ps.executeQuery();

      while(rs.next()){
        results.add(rs.getString("ip"));
      }

      conn.close();
    }
    catch (Exception e)
    {
      System.err.println(e.getMessage());
    }
    return results;
  }

  private void connect() throws ClassNotFoundException, SQLException {

    String myDriver = "org.gjt.mm.mysql.Driver";
    String myUrl = "jdbc:mysql://localhost/test";
    Class.forName(myDriver);

    conn = DriverManager.getConnection(myUrl, "root", "");
  }
}