/*==============================================================*/
/* Nom de SGBD :  MySQL 5.0                                     */
/* Date de création :  24/05/2013 23:12:05                      */
/*==============================================================*/


drop table if exists depositaires;

drop table if exists articles;

/*==============================================================*/
/* Table : depositaires                                          */
/*==============================================================*/
create table depositaires
(
   NumDepositaire      	char(30) not null,
   NumCarteIdentite  	char(20) not null,
   Nom              	char(20) not null,
   Prenom              	char(20),
   Rue		        	char(50),
   Ville          		char(25),
   CodePostal     		char(15),
   NumTelFixe         	char(15),
   NumTelPortable     	char(20) not null,
   primary key (NumDepositaire)
);

/*==============================================================*/
/* Table : articles                                              */
/*==============================================================*/
create table articles
(
   NumArticle           	int not null,
   NumDepositaire       	char(20) not null,
   Situation            	char(50),
   DateDepot  				char(20),
   DateChangementSituation 	char(20),
   Type				        char(50),
   Aspect               	char(50),
   Pour                 	char(50),
   Couleur              	char(20),
   DescriptionArticle     	char(250),
   PrixAchat	 	        double,
   primary key (NumArticle)
);

alter table articles add constraint FK_DEPOSE foreign key (NumDepositaire)
      references depositaires (NumDepositaire) on delete restrict on update restrict;



