
package demineur4;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 *
 * @author derynefour
 */
        
public class Demineur4 {

    public static void main(String[] args) 
    
    {
    Grilletableau tableau = new Grilletableau(10); // on initialise un tableau de 100 cases nulles
    Fenetre f = new Fenetre(tableau) {};
    //Définit sa taille : 650 pixels de large et 490 pixels de haut
    f.setSize(650, 490);
    //Nous demandons maintenant à notre objet de se positionner au centre
    f.setLocationRelativeTo(null);
    //Termine le processus lorsqu'on clique sur la croix rouge
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // on ne veut pas la redimmensionner pour l'instant
    f.setResizable(false);    
    f.setVisible(true);    
    }
    
}
