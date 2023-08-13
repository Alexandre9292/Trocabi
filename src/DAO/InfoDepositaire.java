package DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class InfoDepositaire {
    private String Nom = "";
    private String Prenom = "";
    private String Rue = "";
    private String Ville = "";
    private String NumTelFixe = "";
    private String NumTelPortable = "";
    private String NumCarteIdentite = "";
    private String CodePostal= "";
    private int NumDepositaire = 0;
    
 

    
    public void Ajouter(String dNumCarteIdentite, String dNom, String dPrenom, String dRue, String dVille, String dCodePostal, String dNumTelFixe, String dNumTelPortable) throws SQLException
    {  
     NumCarteIdentite=dNumCarteIdentite;
     Nom = dNom;
     Prenom = dPrenom;
     Rue = dRue;
     Ville = dVille;
     CodePostal = dCodePostal;
     NumTelFixe = dNumTelFixe;
     NumTelPortable = dNumTelPortable;
     //Création de comptes
     trocabi.Trocabi.nbcomptes++;
     NumDepositaire = trocabi.Trocabi.nbcomptes;
     
     //Insertion de la requête et ajout du nouveau dépositaire
     String sqlQuery = "INSERT INTO mysql.depositaires (NumDepositaire, NumCarteIdentite, Nom, Prenom, Rue, Ville, CodePostal, NumTelFixe, NumTelPortable) VALUES ('"+NumDepositaire+"','"+NumCarteIdentite+"', '"+Nom+"', '"+Prenom+"', '"+Rue+"', '"+Ville+"', '"+CodePostal+"', '"+NumTelFixe+"', '"+NumTelPortable+"')";
     //On met à jour la base de données
     DBManager.updateQuery(sqlQuery); 
     
     POJO.Depositaire NewDepositaire = new POJO.Depositaire(NumDepositaire, NumCarteIdentite, Nom, Prenom, Rue, Ville, CodePostal, NumTelFixe, NumTelPortable);
     trocabi.Trocabi.ListeDepositaires.add(NewDepositaire);     
    }
    
    

    public void Modifier(int dNumDepositaire, String dNumCarteIdentite, String dNom, String dPrenom, String dRue, String dVille, String dCodePostal, String dNumTelFixe, String dNumTelPortable) throws SQLException
    {
     NumDepositaire=dNumDepositaire;
     NumCarteIdentite=dNumCarteIdentite;
     Nom = dNom;
     Prenom = dPrenom;
     Rue = dRue;
     Ville = dVille;
     CodePostal = dCodePostal;
     NumTelFixe = dNumTelFixe;
     NumTelPortable = dNumTelPortable;
     
     String sqlQuery = "UPDATE mysql.depositaires SET NumDepositaire='"+NumDepositaire+"', NumCarteIdentite='"+NumCarteIdentite+"', Nom='"+Nom+"', Prenom='"+Prenom+"', Rue='"+Rue+"', Ville='"+Ville+"', CodePostal='"+CodePostal+"', NumTelFixe='"+NumTelFixe+"', NumTelPortable ='"+NumTelPortable+"' WHERE NumDepositaire='"+NumDepositaire+"'";
     DBManager.updateQuery(sqlQuery);
     
     POJO.Depositaire NewDepositaire = new POJO.Depositaire(NumDepositaire, NumCarteIdentite, Nom, Prenom, Rue, Ville, CodePostal, NumTelFixe, NumTelPortable);
     POJO.Depositaire NewDepositaire2 = new POJO.Depositaire(NumDepositaire, NumCarteIdentite, Nom, Prenom, Rue, Ville, CodePostal, NumTelFixe, NumTelPortable);
     
     int i;
     int indiceSuppr = -1;
     for(i=0; i<trocabi.Trocabi.ListeDepositaires.size(); i++)
        {
        NewDepositaire = trocabi.Trocabi.ListeDepositaires.get(i);
        if(NewDepositaire.getNumDepositaire() == NumDepositaire)
            {
            indiceSuppr = i;
            NewDepositaire2 = new POJO.Depositaire(NumDepositaire, NumCarteIdentite, Nom, Prenom, Rue, Ville, CodePostal, NumTelFixe, NumTelPortable);
            i=trocabi.Trocabi.ListeDepositaires.size()-1;
            }
        }
     if(indiceSuppr != -1)
        {
        //Suppression du dépositaire
        trocabi.Trocabi.ListeDepositaires.remove(indiceSuppr);
        //Ajout du dépositaire modifié dans la liste à l'indice correspondant
        trocabi.Trocabi.ListeDepositaires.add(indiceSuppr,NewDepositaire2);
        }
     
    }
    
    public ArrayList Rechercher(String dNom, String dPrenom) throws SQLException
    {
    Nom = dNom;
    Prenom = dPrenom;
    ArrayList<POJO.Depositaire> RechercheDepositaire = new ArrayList<POJO.Depositaire>();
    // Conditions de recherche sur nom et prénom
    if((Nom.equals("") == true || Nom.equals(" ") == true || Nom.equals("/0") == true) && (Prenom.equals("") == true || Prenom.equals(" ") == true || Prenom.equals("/0") == true))
        {
        String sqlQuery = "SELECT * FROM mysql.depositaires";
        ResultSet rs = (ResultSet) DBManager.executeQuery(sqlQuery);
         while(rs.next())
            {
            POJO.Depositaire NewDepositaire = new POJO.Depositaire(rs.getInt("NumDepositaire"), rs.getString("NumCarteIdentite"), rs.getString("Nom"), rs.getString("Prenom"), rs.getString("Rue"), rs.getString("Ville"), rs.getString("CodePostal"), rs.getString("NumTelFixe"), rs.getString("NumTelPortable"));
            RechercheDepositaire.add(NewDepositaire); 
            }
        }
    else if((Nom.equals("") == false && Nom.equals(" ") == false && Nom.equals("/0") == false) && (Prenom.equals("") == true || Prenom.equals(" ") == true || Prenom.equals("/0") == true))
        {
        String sqlQuery = "SELECT * FROM mysql.depositaires WHERE Nom ='"+Nom+"'";
        ResultSet rs = (ResultSet) DBManager.executeQuery(sqlQuery);
         while(rs.next())
        {
        POJO.Depositaire NewDepositaire = new POJO.Depositaire(rs.getInt("NumDepositaire"), rs.getString("NumCarteIdentite"), rs.getString("Nom"), rs.getString("Prenom"), rs.getString("Rue"), rs.getString("Ville"), rs.getString("CodePostal"), rs.getString("NumTelFixe"), rs.getString("NumTelPortable"));
        RechercheDepositaire.add(NewDepositaire); 
        }
      }
    else if((Nom.equals("") == true || Nom.equals(" ") == true || Nom.equals("/0") == true) && (Prenom.equals("") == false && Prenom.equals(" ") == false && Prenom.equals("/0") == false))
        {
        String sqlQuery = "SELECT * FROM mysql.depositaires WHERE Prenom ='"+Prenom+"'";
        ResultSet rs = (ResultSet) DBManager.executeQuery(sqlQuery);
        while(rs.next())
            {
            POJO.Depositaire NewDepositaire = new POJO.Depositaire(rs.getInt("NumDepositaire"), rs.getString("NumCarteIdentite"), rs.getString("Nom"), rs.getString("Prenom"), rs.getString("Rue"), rs.getString("Ville"), rs.getString("CodePostal"), rs.getString("NumTelFixe"), rs.getString("NumTelPortable"));
            RechercheDepositaire.add(NewDepositaire); 
            }
        }
    else
        {
        String sqlQuery = "SELECT * FROM mysql.depositaires WHERE Nom='"+Nom+"' && Prenom='"+Prenom+"'"; 
        ResultSet rs = (ResultSet) DBManager.executeQuery(sqlQuery); 
        while(rs.next())
            { 
            POJO.Depositaire NewDepositaire = new POJO.Depositaire(rs.getInt("NumDepositaire"), rs.getString("NumCarteIdentite"), rs.getString("Nom"), rs.getString("Prenom"), rs.getString("Rue"), rs.getString("Ville"), rs.getString("CodePostal"), rs.getString("NumTelFixe"), rs.getString("NumTelPortable"));
            RechercheDepositaire.add(NewDepositaire);  
            }
        }
      return RechercheDepositaire;          
    }
}
