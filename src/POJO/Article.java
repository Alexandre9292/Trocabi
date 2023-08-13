package POJO;

import java.io.*;

public class Article implements Serializable{
private int NumArticle = 0;
private String Situation = "";
private String DateDepot = "";
private String DateChangementSituation = "";
private String Type = "";
private String Aspect = "";
private String Pour = "";
private String Couleur = "";
private String DescriptionArticle = "";
private double PrixAchat = 0.0;
    
public Article(){
    NumArticle = 0;
    Situation = "";
    DateDepot = "";
    DateChangementSituation = "";
    Type = "";
    Aspect = "";
    Pour = "";
    Couleur = "";
    DescriptionArticle = "";
    PrixAchat = 0.0; 
    }
    
public Article(int aNumArticle, String aSituation, String aDateDepot, String aDateChangementSituation, String aType, String aAspect, String aPour, String aCouleur, String aDescriptionArticle, double aPrixAchat){
    NumArticle = aNumArticle;
    Situation = aSituation;
    DateDepot = aDateDepot;
    DateChangementSituation = aDateChangementSituation;
    Type = aType;
    Aspect = aAspect;
    Pour = aPour;
    Couleur = aCouleur;
    DescriptionArticle = aDescriptionArticle;
    PrixAchat = aPrixAchat;
    }
    
public int getNumArticle(){
    return NumArticle;
    }
public String getSituation(){
    return Situation;
    }
public String getDateDepot(){
    return DateDepot;
    }
public String getDateChangementSituation(){
    return DateChangementSituation;
    }
public String getType(){
    return Type;
    }
public String getAspect(){
    return Aspect;
    }
public String getPour(){
    return Pour;
    }
public String getCouleur(){
    return Couleur;
    }
public String getDescriptionArticle(){
    return DescriptionArticle;
    }
public double getPrixAchat(){
    return PrixAchat;
    }   
}
