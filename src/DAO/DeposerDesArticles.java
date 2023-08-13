package DAO;

import POJO.Article;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeposerDesArticles {
    private int NumArticle = 0;
    private int NumDepositaire = 0;
    private String Situation = "";
    private String DateDepot = "";
    private String DateChangementSituation = "";
    private String Type = "";
    private String Aspect = "";
    private String Pour = "";
    private String Couleur = "";
    private String DescriptionArticle = "";
    private double PrixAchat = 0.0;
    private int Quantite = 1;
    
    //Fonction permettant d'ajouter un nouvel article à la base de données et à la liste d'articles
    public void Ajouter(int aNumDepositaire, String aDateDepot, String aType, String aAspect, String aPour, String aCouleur, String aDescriptionArticle, double aPrixAchat, int aQuantite) throws SQLException{
    NumDepositaire = aNumDepositaire;
    Situation = "Dépôt";
    DateDepot = aDateDepot;
    DateChangementSituation = "Aucune";
    Type = aType;
    Aspect = aAspect;
    Pour = aPour;
    Couleur = aCouleur;
    DescriptionArticle = aDescriptionArticle;
    PrixAchat = aPrixAchat;
    Quantite = aQuantite;
    
    int i,j;
    for(j=0; j<aQuantite; j++)
        {
        //On récupère le nombre d'articles pour savoir quel sera le numéro d'articles du prochain
        trocabi.Trocabi.nbarticles++;
        NumArticle = trocabi.Trocabi.nbarticles;
    
        //Requête permettant d'insérer le nouvel article avec ses caractéristiques dans la base de données
        String sqlQuery = "INSERT INTO mysql.articles (NumArticle, NumDepositaire, Situation, DateDepot, DateChangementSituation, Type, Aspect, Pour, Couleur, DescriptionArticle, PrixAchat) VALUES ('"+NumArticle+"', '"+NumDepositaire+"', '"+Situation+"', '"+DateDepot+"', '"+DateChangementSituation+"', '"+Type+"', '"+Aspect+"', '"+Pour+"', '"+Couleur+"', '"+DescriptionArticle+"', '"+PrixAchat+"')";   
        //On met à jour la base de données
        DBManager.updateQuery(sqlQuery); 
    
        //On créé un nouvel article
        POJO.Article NewArticle = new POJO.Article(NumArticle, Situation, DateDepot, DateChangementSituation, Type, Aspect, Pour, Couleur, DescriptionArticle, PrixAchat); 
        //On les assignes à leur compte
        POJO.Depositaire NewDepositaire = new POJO.Depositaire();
        //On ajoute ce nouvel article à la liste d'article
        for(i=0; i<trocabi.Trocabi.ListeDepositaires.size(); i++)
            {
            //Le compte prend les caractéristiques du compte à l'indice i
            NewDepositaire = trocabi.Trocabi.ListeDepositaires.get(i);
            //Si le numéro du compte est égal au numéro du compte inscrit par l'utilisateur
            if(NewDepositaire.getNumDepositaire() == NumDepositaire)
                {
                trocabi.Trocabi.ListeDepositaires.get(i).setLigne(NewArticle);
                i = trocabi.Trocabi.ListeDepositaires.size() - 1;         
                }
            }
        }
    }
    
    //Fonction permettant de modifier un article dans la base de données et à la liste d'articles
    public void Modifier(int aNumDepositaire, int aNumArticle, String aDateDepot, String aType, String aAspect, String aPour, String aCouleur, String aDescriptionArticle, double aPrixAchat) throws SQLException{
    NumDepositaire = aNumDepositaire;
    NumArticle = aNumArticle;
    DateDepot = aDateDepot;
    Type = aType;
    Aspect = aAspect;
    Pour = aPour;
    Couleur = aCouleur;
    DescriptionArticle = aDescriptionArticle;
    PrixAchat = aPrixAchat;

    //Requête permettant de modifier l'article ciblé  
    String sqlQuery = "UPDATE mysql.articles SET DateDepot='"+DateDepot+"', Type='"+Type+"', Aspect='"+Aspect+"', Pour='"+Pour+"', Couleur='"+Couleur+"', DescriptionArticle='"+DescriptionArticle+"', PrixAchat='"+PrixAchat+"' WHERE NumArticle='"+NumArticle+"'";
    DBManager.updateQuery(sqlQuery);
    
    POJO.Article NewArticle = new POJO.Article(NumArticle, Situation, DateDepot, DateChangementSituation, Type, Aspect, Pour, Couleur, DescriptionArticle, PrixAchat); 
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
     
    //Fonction permettant de supprimer un article dans la base de données et à la liste d'articles
    public void Supprimer(int aNumDepositaire, int aNumArticle) throws SQLException{
    NumArticle = aNumArticle;
    NumDepositaire = aNumDepositaire;
    
    String sqlQuery ="DELETE FROM mysql.articles WHERE NumArticle='"+NumArticle+"'";
    DBManager.updateQuery(sqlQuery);
    
    //On récupère les différentes informations concernant les lignes comptables dans la base de données.          
    POJO.Article NewArticle = new POJO.Article(NumArticle, Situation, DateDepot, DateChangementSituation, Type, Aspect, Pour, Couleur, DescriptionArticle, PrixAchat);
    POJO.Article NewArticle2 = new POJO.Article();
    //On les assignes à leur compte
    POJO.Depositaire NewDepositaire = new POJO.Depositaire();
    
    int i, k;
    int articleasupr = -1;
    
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
                    articleasupr = k;
                    k = NewDepositaire.getArticle().size() - 1;     
                    }
                }
            //On supprime le depositaire
            trocabi.Trocabi.ListeDepositaires.remove(i);
            //On copie la liste d'article sauf k
            //Et on ajoute l'article modifié
            for(k=0; k<NewDepositaire.getArticle().size(); k++)
                {
                if(k != articleasupr)
                    NewDepositaire2.setLigne(NewDepositaire.getArticle().get(k)); 
                }
            //On recopie le depositaire modifié
            trocabi.Trocabi.ListeDepositaires.add(i, NewDepositaire2);
            }
        }
    }
    
    //Fonction permettant d'afficher tous les articles en DEPOT du dépositaire
    public ArrayList<Article> ArticlesEnDepot(int aNumDepositaire){
    NumDepositaire = aNumDepositaire;
    
    int i, j;
    int var=0;

    ArrayList<Article> ListeArticlesEnDepot = new ArrayList<Article>();
    for(i=0; i<trocabi.Trocabi.ListeDepositaires.size(); i++)
        {
        if(trocabi.Trocabi.ListeDepositaires.get(i).getNumDepositaire() == NumDepositaire)
            {
            for(j=trocabi.Trocabi.ListeDepositaires.get(i).getArticle().size()-1; j>=0; j--)     
                {
                if((trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(j).getSituation()).equals("Dépôt") == true)
                    {
                    ListeArticlesEnDepot.add(trocabi.Trocabi.ListeDepositaires.get(i).getArticle().get(j)); 
                    }
                }
            var = 1;
            }
        if(var == 1) return ListeArticlesEnDepot;
        }
    return ListeArticlesEnDepot;
    }
}
