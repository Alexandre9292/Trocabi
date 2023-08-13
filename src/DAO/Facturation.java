package DAO;

import POJO.Article;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Facturation {
    private int NumArticle = 0;
    private int NumDepositaire = 0;
    private String DateDepot = "";
    private String Situation = "";
    private String DateChangementSituation = "";
    
    //Appel de cette fonction pour modifier la situation de l'article
    public void SituationArticle(int aNumArticle, String aSituation, String aDateChangementSituation) throws SQLException{
    
    NumArticle = aNumArticle;
    String sqlQuerySelectArticle = "SELECT * FROM mysql.articles WHERE NumArticle='"+aNumArticle+"'";
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
    
    //numero de compte, date de depot ou NULL
    public ArrayList<Article> RechercherArticle(int aNumDepositaire, String aDateDepot) throws SQLException{
    NumDepositaire = aNumDepositaire;
    DateDepot = aDateDepot;
        
    int i, j;
    int var = 0;
    int numdepot = 1;
    ArrayList<Article> ListeArticlesADateDonnee = new ArrayList<Article>();
    
    if(DateDepot.equals("")==true || DateDepot.equals(" ")==true || DateDepot.equals("/0")==true)
        {
        for(i=0; i<trocabi.Trocabi.ListeDepositaires.size(); i++)
            {
            if(trocabi.Trocabi.ListeDepositaires.get(i).getNumDepositaire() == NumDepositaire)
                {
                DateDepot = trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(trocabi.Trocabi.ListeDepositaires.get(i).getArticle().size()-1).getDateDepot();
                for(j=trocabi.Trocabi.ListeDepositaires.get(i).getArticle().size()-1; j>=0; j--)     
                    {
                    if((trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(j).getDateDepot()).equals(DateDepot) == true)
                        {
                        ListeArticlesADateDonnee.add(trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(j)); 
                        }
                    else{
                        numdepot++;
                        if(numdepot >= 4) return ListeArticlesADateDonnee;
                        ListeArticlesADateDonnee.add(trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(j));
                        DateDepot = trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(j).getDateDepot();
                        }
                    }
                }
            var = 1;
            }
            if(var == 1) return ListeArticlesADateDonnee;
        }
    
    else{
        for(i=0; i<trocabi.Trocabi.ListeDepositaires.size(); i++)
            {
            if(trocabi.Trocabi.ListeDepositaires.get(i).getNumDepositaire() == NumDepositaire)
                {
                for(j=trocabi.Trocabi.ListeDepositaires.get(i).getArticle().size()-1; j>=0; j--)     
                    {
                    if((trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(j).getDateDepot()).equals(DateDepot) == true)
                        {
                        ListeArticlesADateDonnee.add(trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(j)); 
                        }
                    }
                var = 1;
                }
            if(var == 1) return ListeArticlesADateDonnee;
            }
        }
    return ListeArticlesADateDonnee;
    }
}
