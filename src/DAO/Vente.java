package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Vente {
    private int NumArticle = 0;
    private int NumDepositaire = 0;
    private double PrixAchat = 0.0;
    
    //Braderie: Fonction permettant de modifier un article
    public void SituationArticle(int aNumArticle, double aPrixAchat) throws SQLException{
    
    NumArticle = aNumArticle;
    PrixAchat = aPrixAchat;
    String sqlQuerySelectArticle = "SELECT * FROM mysql.articles WHERE NumArticle='"+aNumArticle+"'";
    ResultSet rs = (ResultSet) DBManager.executeQuery(sqlQuerySelectArticle);  
    while(rs.next()){NumDepositaire = rs.getInt("NumDepositaire");}
    
    String sqlQuery = "UPDATE mysql.articles SET PrixAchat='"+PrixAchat+"' WHERE NumArticle='"+NumArticle+"'";
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
                    POJO.Article NewArticle = new POJO.Article(NumArticle, trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getSituation(), trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getDateDepot(), trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getDateChangementSituation(), trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getType(), trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getAspect(), trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getPour(), trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getCouleur(), trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(articleamodifier).getDescriptionArticle(), PrixAchat);
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
    
    //Fonction permettant d'afficher un article en fonction de son cade barre
    public POJO.Article RechercherArticle(int aNumArticle) throws SQLException{
    String sqlQuerySelectArticle = "SELECT * FROM mysql.articles WHERE NumArticle='"+aNumArticle+"'";
    ResultSet rs = (ResultSet) DBManager.executeQuery(sqlQuerySelectArticle);  
    POJO.Article NewArticle = new POJO.Article();
    
    double prixvente = 0;
    String aspect = "";
    
    while(rs.next()){
                    aspect = rs.getString("Aspect");
                    if(aspect.equals("Neuf") == true)
                        prixvente = rs.getDouble("PrixAchat")*(3.0);
                    else if(aspect.equals("Petit article") == true)
                        prixvente = rs.getDouble("PrixAchat")*(0.5);
                    else if(aspect.equals("Gros article") == true)
                        prixvente = rs.getDouble("PrixAchat")*(0.2);
                    else{prixvente = rs.getDouble("PrixAchat");}
                    
                    NewArticle = new POJO.Article(rs.getInt("NumArticle"), rs.getString("Situation"), rs.getString("DateDepot"), rs.getString("DateChangementSituation"), rs.getString("Type"), aspect, rs.getString("Pour"), rs.getString("Couleur"), rs.getString("DescriptionArticle"), prixvente);
                    }
    return NewArticle;
    }

    public void ValiderVente(int aNumDepositaire, int aNumArticle, String DateChangementSituation) throws SQLException{
    NumArticle = aNumArticle;
    NumDepositaire = aNumDepositaire;
    
    //Requête permettant de modifier l'article ciblé  
    String sqlQuery = "UPDATE mysql.articles SET Situation='Vendu', DescriptionArticle='"+DateChangementSituation+"' WHERE NumArticle='"+NumArticle+"'";
    DBManager.updateQuery(sqlQuery);    
    
    //Modification de l'article dans la liste
    POJO.Article NewArticle = new POJO.Article(); 
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
                    NewArticle = new POJO.Article(NewArticle2.getNumArticle(),NewArticle2.getSituation(),NewArticle2.getDateDepot(),NewArticle2.getDateChangementSituation(),NewArticle2.getType(),NewArticle2.getAspect(),NewArticle2.getPour(),NewArticle2.getCouleur(),NewArticle2.getDescriptionArticle(),NewArticle2.getPrixAchat());
                    k = NewDepositaire.getArticle().size() - 1;
                    }
                }
            //On supprime le depositaire
            trocabi.Trocabi.ListeDepositaires.remove(i);
            //On copie la liste d'article sauf k
            //Et on ajoute l'article modifié
            for(k=0; k<NewDepositaire.getArticle().size(); k++)
                {
                if(k != articleamodifier)
                    NewDepositaire2.setLigne(NewDepositaire.getArticle().get(k));
                else if(k == articleamodifier)
                    NewDepositaire2.setLigne(NewArticle);    
                }
            //On recopie le depositaire modifié
            trocabi.Trocabi.ListeDepositaires.add(i, NewDepositaire2);       
            }
        }
    }
}

