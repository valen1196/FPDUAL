package conector;
import lombok.Getter;
import lombok.Setter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*Contains the url to connect to MYSQL DDBB
* @return on URL*/



public class MySqlconnector {
    @Setter
    @Getter
    Properties prop=new Properties();

    public MySqlconnector() {
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("config properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates the connection object for a MySQL DDBB
     * @return a {@link Connection}
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
        try {
            //Indicates which driver is going to be used.
            Class.forName(prop.getProperty(MYSQLconstante.DRIVER));
            //Creates the connection based on the obtained URL.
            return  DriverManager.getConnection(getURL());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }





    /**
     * Obtains the URL to connect to a MySQL DDBB.
     * @return an URL
     */
    private String getURL() {
        //jdbc:mysql://localhost:3306/world?user=sa&password=12345678&useSSL=false;
        return new StringBuilder().append(prop.getProperty(MYSQLconstante.URL_PREFIX))
                .append(prop.getProperty(MYSQLconstante.URL_HOST)).append(":")
                .append(prop.getProperty(MYSQLconstante.URL_PORT)).append("/")
                .append(prop.getProperty(MYSQLconstante.URL_SCHEMA)).append("?user=")
                .append(prop.getProperty(MYSQLconstante.USER)).append("&password=")
                .append(prop.getProperty(MYSQLconstante.PASSWD)).append("&useSSL=")
                .append(prop.getProperty(MYSQLconstante.URL_SSL))
                .append(("&useJDBCCompliantTimezoneShift="))
                .append(prop.getProperty(MYSQLconstante.USE_JDBC_COMPLIANT_TIMEZONE_SHIFT)).append(("&useLegacyDatetimeCode="))
                .append(prop.getProperty(MYSQLconstante.USE_LEGACY_DATE_TIME_CODE)).append(("&serverTimezone="))
                .append(prop.getProperty(MYSQLconstante.SERVER_TIMEZONE)).toString();
    }



    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MySqlconnector connector = new MySqlconnector();
        Connection connection = connector.getMySQLConnection();
        System.out.println(connection.getSchema());
    }


}
