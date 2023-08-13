package trocabi;

import DAO.DBManager;
import POJO.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Trocabi {
    public static int nbarticles = 0;
    public static int nbcomptes = 0;
    //On créé une liste de dépositaires
    public static ArrayList<POJO.Depositaire> ListeDepositaires = new ArrayList<POJO.Depositaire>();
    //Fonction permettant de mettre toute la BD(depositaires et articles) sous forme de listes
    public static void LectureDB()throws SQLException{    
    String sqlQuery1 = "SELECT * FROM mysql.depositaires";
    String sqlQuery2 = "SELECT * FROM mysql.articles";
    
    int i;
    IHM.FenetrePrincipal Fenetre = new IHM.FenetrePrincipal();
    Fenetre.setLocationRelativeTo(null);
    Fenetre.setVisible(true);
    
    //On commence par la liste de dépositaires
    ResultSet rs = (ResultSet) DBManager.executeQuery(sqlQuery1);  
    while(rs.next())
        {
        //On incrémente le nombre de comptes
        nbcomptes++;
        //On récupère les différentes informations concernant les dépositaires dans la base de données.
        POJO.Depositaire NewDepositaire = new POJO.Depositaire(rs.getInt("NumDepositaire"), rs.getString("NumCarteIdentite"), rs.getString("Nom"), rs.getString("Prenom"), rs.getString("Rue"), rs.getString("Ville"), rs.getString("CodePostal"), rs.getString("NumTelFixe"), rs.getString("NumTelPortable"));
        ListeDepositaires.add(NewDepositaire);  
        //On affiche les informations récupérées.
        System.out.println("Depositaire->Numero depositaire:"+NewDepositaire.getNumDepositaire()+" NumCarteIdentite:"+NewDepositaire.getNumCarteIdentite()+" Nom/Prenom:"+NewDepositaire.getNom()+" "+NewDepositaire.getPrenom()+" Adresse:"+NewDepositaire.getRue()+" "+NewDepositaire.getCodePostal()+" "+NewDepositaire.getVille()+" Num Tel. Fixe:"+NewDepositaire.getNumTelFixe()+" Num Tel. Portable:"+NewDepositaire.getNumTelPortable());    
        }
    //On passe a la liste des articles associés aux dépositaires    
    rs = (ResultSet) DBManager.executeQuery(sqlQuery2);  
    while(rs.next())
        {         
        POJO.Article NewArticle = new POJO.Article(rs.getInt("NumArticle"), rs.getString("Situation"), rs.getString("DateDepot"), rs.getString("DateChangementSituation"), rs.getString("Type"), rs.getString("Aspect"), rs.getString("Pour"), rs.getString("Couleur"), rs.getString("DescriptionArticle"), rs.getDouble("PrixAchat")); 
        POJO.Depositaire NewDepositaire = new Depositaire();
        for(i=0; i<nbcomptes; i++)
            {
            NewDepositaire = ListeDepositaires.get(i);
            if(NewDepositaire.getNumDepositaire() == rs.getInt("NumDepositaire"))
                {
                NewDepositaire.getArticle().add(NewArticle);
                ListeDepositaires.remove(i);
                ListeDepositaires.add(i, NewDepositaire);
                i = nbcomptes - 1;
                }
            }
        nbarticles = NewArticle.getNumArticle();
        //On affiche les informations récupérées.
        System.out.println("Articles->Num article:"+NewArticle.getNumArticle()+" Num depositaire:"+rs.getInt("NumDepositaire") +" Situation:"+NewArticle.getSituation()+" Date depot:"+NewArticle.getDateDepot()+" Date chgmt situation:"+NewArticle.getDateChangementSituation()+" Caractéristiques article:"+NewArticle.getType()+" "+NewArticle.getAspect()+" "+NewArticle.getPour()+" "+NewArticle.getCouleur()+" Description:"+NewArticle.getDescriptionArticle()+" Prix achat:"+NewArticle.getPrixAchat());    
        }
    }
    
public static void main(String[] args) throws SQLException {
    LectureDB();
    
    //Test de DAO.Info dépositaire
    DAO.InfoDepositaire NewDep = new DAO.InfoDepositaire();
    //Probleme : Ne garde pas les zeros si placé au début comme 000233 devient 233
    //NewDep.Ajouter("10345", "Fernandez", "Martin", "21 rue des deportes", "Liege", 58000, "", "");
    //NewDep.Ajouter("84456", "Leanz", "Luc", "63 impasse des miositisses", "Paris", 53000, "0243221234", "");
    //NewDep.Ajouter("23461", "Griand", "Sara", "233 rue des mocassins", "Laval", 58000, "", "0615238546");
    //NewDep.Modifier(3, "23461", "GriandLOL", "Sara", "233 rue des mocassins", "Laval", 58000, "", "0615238546");
    /*int k;
    ArrayList<POJO.Depositaire> listetest =  NewDep.Rechercher("Griand", "Sara");; 
    for(k=0; k<listetest.size(); k++)
        {
        System.out.println("a"+listetest.get(k).getNumCarteIdentite());
        }*/
    
    //Test de DAO.DeposerDesArticles
    DAO.DeposerDesArticles NewArticle = new DAO.DeposerDesArticles();
    //NewArticle.Ajouter(2, "19/02/2015", "Chaussons", "Neuf", "Femme barbue", "Bleu marine", "Que neni pouet pouet", 100.89, 2);
    //NewArticle.Ajouter(3, "22/03/2015", "Pommes", "Neuf", "Femme barbue", "Bleu marine", "Que neni pouet pouet", 100.89, 1);
    //NewArticle.Ajouter(2, "22/03/2015", "Poulet", "Neuf", "Femme barbue", "Bleu marine", "Que neni pouet pouet", 100.89, 2);
    //NewArticle.Ajouter(1, "3/04/2016", "Banzai", "Neuf", "Femme barbue", "Bleu marine", "Que neni pouet pouet", 100.89, 2);
    //NewArticle.Modifier(1, 3, "18/07/2013", "Tshirt moulant", "Use", "Homme viril", "Beige", "Que neni", 20.89);
    //NewArticle.Supprimer(1, 4);    
    /*int k;
    ArrayList<Article> ListeArticlesEnDepot = new ArrayList<Article>();
    ListeArticlesEnDepot = NewArticle.ArticlesEnDepot(1);
    for(k=0; k<ListeArticlesEnDepot.size(); k++)
        {
        System.out.println("a--> "+ListeArticlesEnDepot.get(k).getNumArticle());
        }*/
    
    //Test de DAO.Inventaire
    DAO.Inventaire Inv = new DAO.Inventaire();
    //Inv.SituationArticle(5, "Perdu!!", "29/09/2019");
    /*POJO.Article Articletest = new POJO.Article();
    Articletest = Inv.RechercherArticle(2);
    System.out.println(Articletest.getCouleur());*/
    
    //Test de DAO.Facturation
    DAO.Facturation Fac = new DAO.Facturation();
    //Fac.SituationArticle(2,"Donné" ,"10/12/3333" );
    /*int k;
    ArrayList<POJO.Article> listetest =  Fac.RechercherArticle(1, ""); 
    for(k=0; k<listetest.size(); k++)
        {
        System.out.println("b--> "+listetest.get(k).getNumArticle());
        }*/
    
    //Test de DAO.Vente
    DAO.Vente Ven = new DAO.Vente();
    //Ven.SituationArticle(3, 2.99);
    /*POJO.Article art = Ven.RechercherArticle(5);
    System.out.println(art.getPrixAchat()+art.getAspect()+art.getSituation());*/
    
    System.out.println("");
    int i, j;
    for(i=0; i<ListeDepositaires.size(); i++)
        { 
        System.out.println("Depositaire->Numero depositaire:"+ListeDepositaires.get(i).getNumDepositaire()+" NumCarteIdentite:"+ListeDepositaires.get(i).getNumCarteIdentite()+" Nom/Prenom:"+ListeDepositaires.get(i).getNom()+" "+ListeDepositaires.get(i).getPrenom()+" Adresse:"+ListeDepositaires.get(i).getRue()+" "+ListeDepositaires.get(i).getCodePostal()+" "+ListeDepositaires.get(i).getVille()+" Num Tel. Fixe:"+ListeDepositaires.get(i).getNumTelFixe()+" Num Tel. Portable:"+ListeDepositaires.get(i).getNumTelPortable());    
        for(j=0; j<ListeDepositaires.get(i).getArticle().size(); j++)
            {
                   System.out.println("     Articles->Num article:"+ListeDepositaires.get(i).getArticle().get(j).getNumArticle()+" Situation:"+ListeDepositaires.get(i).getArticle().get(j).getSituation()+" Date depot:"+ListeDepositaires.get(i).getArticle().get(j).getDateDepot()+" Date chgmt situation:"+ListeDepositaires.get(i).getArticle().get(j).getDateChangementSituation()+" Caractéristiques article:"+ListeDepositaires.get(i).getArticle().get(j).getType()+" "+ListeDepositaires.get(i).getArticle().get(j).getAspect()+" "+ListeDepositaires.get(i).getArticle().get(j).getPour()+" "+ListeDepositaires.get(i).getArticle().get(j).getCouleur()+" Description:"+ListeDepositaires.get(i).getArticle().get(j).getDescriptionArticle()+" Prix achat:"+ListeDepositaires.get(i).getArticle().get(j).getPrixAchat());          
            }
        }
    }
}
