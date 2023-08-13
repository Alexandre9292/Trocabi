package DAO;

/*Gestion de la connexion à la base de données et l'exécution des requêtes SQL*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBManager {
    
    private static Connection connect;
    private static Statement state;

    /*Constructeur privé*/
    private DBManager(){}

    /*Execute les requêtes de type SELECT*/
    public static ResultSet executeQuery(String sqlQuery) throws SQLException{

        try{
            if (!(connect instanceof Connection))
                initConnexion("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/mysql","root","root");
                //initConnexion("org.apache.derby.jdbc.EmbeddedDriver","jdbc:derby://localhost:1527/sample","root","pifou69");    

            state = connect.createStatement();
            ResultSet rs = state.executeQuery(sqlQuery);
            return rs;
        }
        catch(SQLException e)
        {
            throw e;
        }
    }

    /*Execute les requêtes de type INSERT, DELETE, UPDATE*/
    public static int updateQuery(String sqlQuery) throws SQLException{

        try{
            if (connect == null)
                initConnexion("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/mysql","root","root");
            //initConnexion("org.apache.derby.jdbc.EmbeddedDriver","jdbc:derby://localhost:1527/sample","root","pifou69");    
            DBManager.state = connect.createStatement();
            int result = state.executeUpdate(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            connect.commit();

            return result;
            
        }
        catch(SQLException e)
        {
            throw e;
        }
    }

    /*Instantiation de la connexion à la base de données*/
    private static void initConnexion(String driver,String url, String login, String password) throws SQLException{

        try {
            Class.forName(driver);
            connect = DriverManager.getConnection(url,login,password);
            //connect = DriverManager.getConnection(url);
            connect.setAutoCommit(false);
            System.out.println("Connexion reussie");
        }
        catch (ClassNotFoundException e){
            System.out.println("erreur chargement pilote JDBC \n");
            System.exit(0);
        }
        catch (SQLException e){
            System.out.println("erreur connexion base de données \n"+e.getMessage());
            System.exit(0);
        }
        }

    public static Connection getConnect() {
        return connect;
    }

}


