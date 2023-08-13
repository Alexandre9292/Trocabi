package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Inventaire {
    private int NumArticle = 0;
    private int NumDepositaire = 0;
    private String Situation = "";
    private String DateChangementSituation = "";
    
    //Appel de cette fonction pour modifier la situation de l'article
    public void SituationArticle(int aNumArticle, String aSituation, String aDateChangementSituation) throws SQLException{
    
    NumArticle = aNumArticle;
    String sqlQuerySelectArticle = "SELECT NumDepositaire FROM mysql.articles WHERE NumArticle='"+NumArticle+"'";
    ResultSet rs = (ResultSet) DBManager.executeQuery(sqlQuerySelectArticle);  
    while(rs.next()){NumDepositaire = rs.getInt("NumDepositaire");}
    Situation = aSituation;
    DateChangementSituation = aDateChangementSituation;
    
    String sqlQuery = "UPDATE mysql.articles SET Situation='"+Situation+"', DateChangementSituation='"+DateChangementSituation+"' WHERE NumArticle='"+NumArticle+"'";
    DBManager.updateQuery(sqlQuery);
    
    POJO.Depositaire NewDepositaire = new POJO.Depositaire();
    POJO.Article NewArticle2 = new POJO.Article();
    int i, k;
    int articleamodifier = -1;
    for(i=0; i<trocabi.Trocabi.ListeDepositaires.size(); i++)
        {
        NewDepositaire = trocabi.Trocabi.ListeDepositaires.get(i);
        if(NewDepositaire.getNumDepositaire() == NumDepositaire)
            {
            //On créé un dépositaire avec les bonnes caracteristiques
            POJO.Depositaire NewDepositaire2 = new POJO.Depositaire(NewDepositaire.getNumDepositaire(), NewDepositaire.getNumCarteIdentite(), NewDepositaire.getNom(), NewDepositaire.getPrenom(), NewDepositaire.getRue(), NewDepositaire.getVille(), NewDepositaire.getCodePostal(), NewDepositaire.getNumTelFixe(), NewDepositaire.getNumTelPortable());
            for(k=0; k<NewDepositaire.getArticle().size(); k++)
                {
                NewArticle2 = NewDepositaire.getArticle().get(k);
                if(NewArticle2.getNumArticle() == NumArticle)
                    {
                    articleamodifier = k;
                    k = NewDepositaire.getArticle().size() - 1;
                    }
                }
            //On copie la liste d'article sauf k
            //Et on ajoute l'article modifié
            for(k=0; k<NewDepositaire.getArticle().size(); k++)
                {
                if(k != articleamodifier)
                    NewDepositaire2.setLigne(NewDepositaire.getArticle().get(k));
                else if(k == articleamodifier)
                    {
                    POJO.Article NewArticle = new POJO.Article(NumArticle, Situation, trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getDateDepot(), DateChangementSituation, trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getType(), trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getAspect(), trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getPour(), trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getCouleur(), trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getDescriptionArticle(), trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getPrixAchat());
                    NewDepositaire2.setLigne(NewArticle);  
                    }
                }
            //On supprime le depositaire
            trocabi.Trocabi.ListeDepositaires.remove(i);
            //On recopie le depositaire modifié
            trocabi.Trocabi.ListeDepositaires.add(i, NewDepositaire2);       
            }
        } 
    }
    
    //Appel de cette fonction pour récupérer les informations de l'article lorsque l'on appuie sur valider
    public POJO.Article RechercherArticle(int aNumArticle) throws SQLException{
    NumArticle = aNumArticle;
    
    String sqlQuerySelectArticle = "SELECT * FROM mysql.articles WHERE NumArticle='"+NumArticle+"'";
    ResultSet rs = (ResultSet) DBManager.executeQuery(sqlQuerySelectArticle);  
  
    POJO.Article NewArticle = new POJO.Article() ;
    while(rs.next()){NewArticle = new POJO.Article(rs.getInt("NumArticle"), rs.getString("Situation"), rs.getString("DateDepot"), rs.getString("DateChangementSituation"), rs.getString("Type"), rs.getString("Aspect"), rs.getString("Pour"), rs.getString("Couleur"), rs.getString("DescriptionArticle"), rs.getDouble("PrixAchat"));} 
    return NewArticle;
    }
}
