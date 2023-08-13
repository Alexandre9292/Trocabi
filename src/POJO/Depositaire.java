package POJO;

import java.util.*;
import java.io.*;

public class Depositaire implements Serializable{
private int NumDepositaire = 0;
private String NumCarteIdentite = "";
private String Nom = "";
private String Prenom = "";
private String Rue = "";
private String Ville = "";
private String CodePostal = "";
private String NumTelFixe = "";
private String NumTelPortable = "";
private ArrayList<Article> article;
public static final int NBarticle = 100 ;
 
public Depositaire(){
    NumDepositaire = 0;
    NumCarteIdentite = "";
    Nom = "";
    Prenom = "";
    Rue = "";
    Ville = "";
    CodePostal = "";
    NumTelFixe = "";
    NumTelPortable = "";
    article = new ArrayList<Article>();
    }
 
public Depositaire(int aNumDepositaire, String aNumCarteIdentite, String aNom, String aPrenom, String aRue, String aVille, String aCodePostal, String aNumTelFixe, String aNumTelPortable){
    NumDepositaire = aNumDepositaire;
    NumCarteIdentite = aNumCarteIdentite;
    Nom = aNom;
    Prenom = aPrenom;
    Rue = aRue;
    Ville = aVille;
    CodePostal = aCodePostal;
    NumTelFixe = aNumTelFixe;
    NumTelPortable = aNumTelPortable;
    article = new ArrayList<Article>();
    } 

public void setLigne(Article tmp){
    article.add((Article) tmp);    
    }
 
public ArrayList<Article> getArticle(){
    return article;
    }

public int getNumDepositaire(){
    return NumDepositaire;
    }
public String getNumCarteIdentite(){
    return NumCarteIdentite;
    }
public String getNom(){
    return Nom;
    }
public String getPrenom(){
    return Prenom;
    }
public String getRue(){
    return Rue;   
    }
public String getVille(){
    return Ville;
    }
public String getCodePostal(){
    return CodePostal;
    }
public String getNumTelFixe(){
    return NumTelFixe;
    }
public String getNumTelPortable(){
    return NumTelPortable;
    }
}
