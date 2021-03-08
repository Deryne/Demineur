
// Cette classe permet de définir chaque case du démineur
// Chaque case est un bouton cliquable avec des options graphiques

package demineur4;

// Initialisation des imports
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;
import demineur4.Grilletableau;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;


public class CaseGraph extends JButton{

    public int nbmines = 0; // Une case au départ n'a pas de mine à côté
    public int visible; // paramètre pour savoir si la case est visible 
    public int status;
    public boolean case_decouverte; // booleén pour savoir si la case est découverte (après un clic)
    public boolean mine; // Une case a une mine ou non
    public boolean drapeau; // Un drapeau peut etre sur la case non découverte ou non
    public boolean doubt; // Un point vert peut etre sur la case non découverte ou non
    // C'est utilisé quand on est pas vraiment sur d'avoir affaire à une mine
        
    // constructeur
    public CaseGraph() 
    {
        visible = 0; // non visible par défaut
        case_decouverte = false; // non découverte
        mine = false; // sans mine
        drapeau = false; // sans drapeau
        nbmines = 0; // Pas de mines autour de la case
        doubt = false; // pas de doute par défaut
    }
    
    @Override
    public void paint(Graphics G) 
{
        super.paint(G);
        // Si la case est cachée (non cliquée, visible = 0)
        if(this.visible == 0)
        {
            drapeau = false; // on enlève l'attribut présence d'un drapeau
            doubt = false; // on enlève l'attribut présence d'un doubt  
            this.setIcon(new javax.swing.ImageIcon(getClass().getResource("/demineur4/Cache.png")));
            // Cette image est celle de la case cachée
        }
        
        if (this.visible == 1) // quand on veut devoiler une case qui n'est pas encore affichée
        {   
            // si on a pas cliqué sur une mine
            if (this.mine == false)
            {
            // si la case est dévoilée, on enlève le drapeau et/ou le doute
            this.drapeau  = false;
            this.doubt = false;
            
            // Maintenant qu'on a découvert la case et que c'est pas une mine
            // on récupère le nombre de mine à côté
            int nb = this.nbmines;
            
            // Si 0 mines à côté
            if(nb == 0){
            this.setIcon(new javax.swing.ImageIcon(getClass().getResource("/demineur4/Mine0.png")));
            }
            // Si 1 mines à côté
            if((nb == 1)){
            this.setIcon(new javax.swing.ImageIcon(getClass().getResource("/demineur4/Mine1.png")));
            }
            // Si 2 mines à côté
            if(nb == 2){
            this.setIcon(new javax.swing.ImageIcon(getClass().getResource("/demineur4/Mine2.png")));
            }
            // Si 3 mines à côté
            if(nb == 3){
            this.setIcon(new javax.swing.ImageIcon(getClass().getResource("/demineur4/Mine3.png")));
            }
            // Si 4 mines à côté (rare)
            if(nb == 4){
            this.setIcon(new javax.swing.ImageIcon(getClass().getResource("/demineur4/Mine4.png")));
            }
            }
            
            // Si on a une mine
            if((case_decouverte == true)&&(mine == true)){
            // on affiche la mine
            this.setIcon(new javax.swing.ImageIcon(getClass().getResource("/demineur4/Mine.png")));
            }
            
        } // fin de la condition dévoiler une case
        
        if(this.visible == 2) // on indique que la case affiche un drapeau
        {
            this.setIcon(new javax.swing.ImageIcon(getClass().getResource("/demineur4/Drapeau.png")));
            // on affiche l'image du drapeau
            this.drapeau = true;
        }
        
        if(this.visible == 3)
        {
            this.setIcon(new javax.swing.ImageIcon(getClass().getResource("/demineur4/Doute.png")));
            doubt = true;
        }

}
    
    
}
