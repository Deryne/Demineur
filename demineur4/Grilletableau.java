

// Cette classe permet de remplir une première version du Démineur
// Elle remplit les cases de mines et cremplit les attributs des cases
// Elle permet de fournir un tableau de mines et de cases

package demineur4;

import static demineur4.Fenetre.taille;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

public class Grilletableau {
    // attributs
    int taille; // on intiliase la taille
    CaseGraph grille[][]; // on cree un tableau de case
    int compteur;
    int nbcases;
    int nbmines;  // attributs qui permet de compter les mines à côté
    // constructeur
    public Grilletableau(int ptaille) // quand on crée un tableau on a juste besoin de la taille
    {        
        taille = ptaille;
        compteur = taille/3; // on a choisit le ratio de mine dans un tableau de 10
        nbcases = taille*taille;
        nbmines = 0 ; // on initialise le nombre de mines
        grille = new CaseGraph[taille][taille]; // on initialise le tableau de CaseGraoh (boutons cliquables)
        remplir(); // on remplit le tableau
        nombre_place(); // cette fonction sert à trouver le nombre de mines autour de chaque case
        //afficher(); // on l'affiche dans la console pour savoir où et quoi (oui on l'enlève pour jouer sinon c'est tricher)
        
    }
    // fin du constructeur
    
    
    // methode
    public void afficher(){
    // Cette fonction affiche le tableau rempli dans la console avec des X pour les mines
    // et les autres cases, on affiche le nombre de mines à côté
        for(int i = 0; i<taille; i++)
        {
            for(int j= 0; j<taille; j++)
            {
                if (grille[i][j].mine == true)
                {
                    System.out.print("\tX"); // on a une mine
                }
                else{
                System.out.print("\t"+grille[i][j].nbmines); // il n'y a pas de mines
                }
            }
        System.out.println("");// on revient à la ligne
        }
    }
    
    
    public void remplir(){
        for(int i = 0; i< taille; i++) // i represente les lignes
        {
            for(int j = 0; j< taille ; j++)// j represente les colonnes
            {
                // on veut creer une case graph (nouveau bouton) à qui on va donner les caractéristiques de la case 'brute'
                CaseGraph caseg = new CaseGraph();
                grille[i][j] = caseg; // on affecte la case à un bouton caseGraph
                
                int nb = (int) (Math.random() * taille/2 ); // entre 0 et taille/2
                // On a ainsi un nombre de mines
                //System.out.println(""+nb);
                if((nb==0)&&(compteur>0)){
                    grille[i][j].mine = true; // on a une mine
                    nbmines++;
                    compteur--; // on décompte les mines
                }
            }
            compteur = taille/5;
        }
        
    }
    
    public void nombre_place(){
        for(int i = 0; i< taille; i++) // i represente les lignes
        // on va creer le tableau rempli
        {
            for(int j = 0; j< taille ; j++)// j represente les colonnes
            {
              if (grille[i][j].mine == true) // si on a une mine
                {
                    // On définit plusieurs cas particuliers où une mine peut se trouver
                    // puis on incrémente nbmines aux cases adjacentes
                    // la méthode est un peu barbare mais elle permet d'éviter les null point exception
                    
                    // On définit notre tableau comme un carré avec (dans le sens des aiguilles) le coin 1 en haut à gauche
                    // Toujours dans le sens des aiguilles, le bord 1 et celui tout en haut et ainsi de suite
                    
                    if((j ==0)&&(i==0)) // coin 1
                    {
                    grille[i][j+1].nbmines ++;
                    grille[i+1][j].nbmines ++;
                    grille[i+1][j+1].nbmines ++;
                    }
                    if((j== taille-1)&&(i==0)) // coin 2
                    {
                    grille[i][j-1].nbmines ++;
                    grille[i+1][j-1].nbmines ++;
                    grille[i+1][j].nbmines ++;
                    }
                    
                    if((j== taille-1)&&(i==taille-1)) // coin 3
                    {
                    grille[i][j-1].nbmines ++;
                    grille[i-1][j].nbmines ++;
                    grille[i-1][j-1].nbmines ++;
                    }
                    
                    if((j==0)&&(i==taille-1)) // coin 4
                    {
                    grille[i-1][j+1].nbmines ++;
                    grille[i][j+1].nbmines ++;
                    grille[i-1][j].nbmines ++;
                    }
                    
                    // cas bord 1
                    if(  ((i==0)&&((j!=0)&&j!=taille-1)) )
                    {
                        grille[i][j+1].nbmines ++;
                        grille[i+1][j+1].nbmines ++;
                        grille[i+1][j-1].nbmines ++;
                        grille[i][j-1].nbmines ++;
                        grille[i+1][j].nbmines ++;
                    }
                    
                    // cas bord 2
                    if(((j==taille-1)&&((i!=0)&&(i!=taille-1))))
                    {
                        grille[i][j-1].nbmines ++;
                        grille[i-1][j].nbmines ++;
                        grille[i+1][j].nbmines ++;
                        grille[i-1][j-1].nbmines ++;
                        grille[i+1][j-1].nbmines ++;
                    }
                    
                    // cas bord 3
                    if(  ((i==taille-1)&&((j!=0)&&(j!=taille-1))) )
                    {
                        grille[i][j-1].nbmines ++;
                        grille[i][j+1].nbmines ++;
                        grille[i-1][j-1].nbmines ++;
                        grille[i-1][j+1].nbmines ++;
                        grille[i-1][j].nbmines ++;
                    }
                    
                    // cas bord 4
                    if(  ((j==0)&&((i!=0)&&(i!=taille-1))) )
                    {
                        grille[i-1][j].nbmines ++;
                        grille[i+1][j].nbmines ++;
                        grille[i-1][j+1].nbmines ++;
                        grille[i+1][j+1].nbmines ++;
                        grille[i][j+1].nbmines ++;
                    }
                    
                    //cas normal (la case n'est ni sur un bord ni sur un coin)
                    if((j!=0)&&(j!=taille-1)&&(i!=0)&&(i!=taille-1))
                    {
                        grille[i-1][j+1].nbmines ++;
                        grille[i+1][j].nbmines ++;
                        grille[i-1][j].nbmines ++;
                        grille[i][j+1].nbmines ++;
                        grille[i][j-1].nbmines ++;
                        grille[i+1][j+1].nbmines ++;
                        grille[i-1][j-1].nbmines ++;
                        grille[i+1][j-1].nbmines ++;
                    }
                    
                }
            }
        }
    }

    

 


    
}
