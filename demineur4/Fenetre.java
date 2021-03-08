

package demineur4;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import demineur4.Grilletableau;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.*;


public abstract class Fenetre extends javax.swing.JFrame implements ActionListener{

    static int taille;
    Grilletableau grilletab; // tableau rempli
    boolean select_drapeau = false; // attribut qui indique si le bouton drapeau est activé ou non
    boolean select_doubt = false; // attribut qui indique si le bouton doubt est activé ou non
    boolean victoire;
    boolean defaite;
    int comptemine;
    

    public Fenetre(Grilletableau g) {
         // constructeur
        initComponents();
        victoire = false; // par défaut dans le lancement de la partie, la victoire n'existe pas
        comptemine = 0;
        defaite = false; // ni la défaite
        comptemine = 0;
        grilletab = g; // tableau rempli
        taille = g.taille;
        panel.setLayout(new java.awt.GridLayout(g.taille,g.taille));
        Message.setText(" Neutre "); // on affiche
        Etat.setFont(new java.awt.Font(Font.SERIF,Font.BOLD,15));
        Etat.setText("Etat de la Partie");
        Titre.setFont(new java.awt.Font(Font.SERIF,Font.BOLD,25));

       
        //Instanciation d'un objet JPanel
        //Définition de sa couleur de fond
        panel.setBackground(Color.lightGray);
        
        Drapeau.setText("Drapeau"); // on indique la fonction du Jtoggle
        Doubt.setText("Doute"); // de meme pour doubt
        Drapeau.addActionListener(this); // on écoute si on a cliqué sur le bouton
        Doubt.addActionListener(this); // pareil
        Clique(); // fonction qui va ecouter si on a cliqué sur quelque chose d'activable
        
    } // fin du constructeur

    public void Clique(){
                
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) 
            {
                panel.add(grilletab.grille[i][j]); // on ajoute chaque bouton du tableau dans le panel
                grilletab.grille[i][j].addActionListener(this); // et on sera attention à chaque clic sur chaque bouton
            }
        }
    }
    // Fonction qui s'active quand on clique sur un élément cliquable
    public void actionPerformed(ActionEvent evt) {
    
    if((victoire == false)&&(defaite == false)){ // Si l'état de la partie est neutre, le clic a un effet
        // Comme cela, en termes de victoire ou de défaite, cliquer ne sert plus à rien
        for (int i = 0; i < taille; i++) 
        {
        for (int j = 0; j < taille; j++) // on parcoure le tableau
        {
            if(evt.getSource() == grilletab.grille[i][j]) // si on vient de cliquer sur une case du tableau
            {
            // on verifie si on ne vient pas de cliquer sur une mine
            Perdu(grilletab.grille[i][j]);
            grilletab.nbcases -- ; // le nombre de cases cachées diminue
            Montrons(i,j); // fonction qui permet d'afficher la case
            Gagne(); // fonction qui permet de savoir si toutes les cases restantes et cachées sont des mines
            repaint(); // On affiche l'aspect des boutons modifiés par la fonction Montrons
            }
        }
        }
        
        if(evt.getSource() == Drapeau)// si on a cliqué sur le drapeau
        {
            if(Drapeau.isSelected()) // Si le bouton est actif (en bleu)
            {
            select_drapeau = true; // On l'indique sur l'attribut
            } else { select_drapeau = false; // Si le drapeau est déselectionné, on l'indique avec l'attribut
            }
        }
        // Meme schéma pour doubt
        if(evt.getSource() == Doubt)// si on a cliqué sur le bouton doubt
        {
            if(Doubt.isSelected()){
            select_doubt = true;
            } else { select_doubt = false;
            }
        }

    }

    }; // fin du action Listener
    
   public void Montrons(int i, int j)
   {
        if(((grilletab.grille[i][j].visible == 0)||(grilletab.grille[i][j].visible == 2)||(grilletab.grille[i][j].visible == 3))&&(select_drapeau == false)&&(select_doubt == false))
        // si la case était non visible ou avec un drapeau ou avec un doubt et qu'on n'était ni en mode drapeau ni en mode doubt
        // on change ses paramètres (pour l'afficher)
        {
        // on modifie les attributs de la case pour l'afficher
        grilletab.grille[i][j].visible = 1;
        grilletab.grille[i][j].status++;
        grilletab.grille[i][j].case_decouverte = true;
        repaint();
        }
                    
        // si le bouton drapeau est activé, que la case est cachée et qu'elle n'avait pas de drapeau
        if((select_drapeau == true)&&(grilletab.grille[i][j].visible == 0)&&(grilletab.grille[i][j].drapeau == false)&&(grilletab.grille[i][j].doubt == false))
        {
        // on lui affecte un attribut qui lui en mettre un
        grilletab.grille[i][j].visible = 2;
        }
                    
        // si le bouton drapeau est activé, que la case est visible et qu'elle a deja un drapeau
        if((select_drapeau == true)&&(grilletab.grille[i][j].visible == 2)&&(grilletab.grille[i][j].drapeau == true))
        {
        // on lui enlève le drapeau
        grilletab.grille[i][j].visible = 0;
        }

        // si le bouton doubt est activé, que la case est cachée et qu'elle n'avait pas de doubt
        if((select_doubt == true)&&(grilletab.grille[i][j].visible == 0)&&(grilletab.grille[i][j].doubt == false)&&(grilletab.grille[i][j].drapeau == false))
        {
        grilletab.grille[i][j].visible = 3;

        }
        // si le bouton doubt est activé, que la case est cachée et qu'elle a deja un doubt
        if((select_doubt == true)&&(grilletab.grille[i][j].visible == 3)&&(grilletab.grille[i][j].doubt == true))
        {
        grilletab.grille[i][j].visible = 0;
        }
        
        Devoile(i,j); // on verifie si on a un 0
                   
   }
    
    public void Devoile(int i, int j)
    {
        // si on a cliqué sur une case 0 sans mines et a present visible
        if ((grilletab.grille[i][j].nbmines == 0)&&(grilletab.grille[i][j].mine == false)&&(grilletab.grille[i][j].visible == 1)&&(grilletab.grille[i][j].case_decouverte == true))
        {   
                grilletab.grille[i][j].case_decouverte = false; // Ce bidouillage permet de ne pas rentrer dans une boucle infinie où le 0 serait
                // recompté
                // On dévoile les cases d'à côté suivant la position i j de la case cliquée
                if((j ==0)&&(i==0)) // coin 1
                    {
                    Montrons(i,j+1);
                    Montrons(i+1,j);
                    Montrons(i+1,j+1);
                    }
                    if((j== taille-1)&&(i==0)) // coin 2
                    {
                    Montrons(i,j-1);
                    Montrons(i+1,j-1);
                    Montrons(i+1,j);
                    }
                    if((j== taille-1)&&(i==taille-1)) // coin 3
                    {
                    Montrons(i,j-1);
                    Montrons(i-1,j);
                    Montrons(i-1,j-1);
                    }
                    if((j==0)&&(i==taille-1)) // coin 4
                    {
                    Montrons(i-1,j+1);
                    Montrons(i,j+1);
                    Montrons(i-1,j);
                    }
            
                    // cas bord 1
                    if(  ((i==0)&&((j!=0)&&j!=taille-1)) )
                    {
                        Montrons(i,j+1);
                        Montrons(i+1,j+1);
                        Montrons(i+1,j-1);
                        Montrons(i,j-1);
                        Montrons(i+1,j);
                    }
                    
                    // cas bord 2
                    if(((j==taille-1)&&((i!=0)&&(i!=taille-1))))
                    {
                        Montrons(i,j-1);
                        Montrons(i-1,j);
                        Montrons(i+1,j);
                        Montrons(i-1,j-1);
                        Montrons(i+1,j-1);
                    }
                    
                    // cas bord 3
                    if(  ((i==taille-1)&&((j!=0)&&(j!=taille-1))) )
                    {
                        Montrons(i,j-1);
                        Montrons(i,j+1);
                        Montrons(i-1,j-1);
                        Montrons(i-1,j+1);
                        Montrons(i-1,j);
                    }
                    
                    // cas bord 4
                    if(  ((j==0)&&((i!=0)&&(i!=taille-1))) )
                    {
                        Montrons(i-1,j);
                        Montrons(i+1,j);
                        Montrons(i-1,j+1);
                        Montrons(i+1,j+1);
                        Montrons(i,j+1);
                    }
                    
                    //cas normal
                    if((j!=0)&&(j!=taille-1)&&(i!=0)&&(i!=taille-1))
                    {
                        Montrons(i-1,j+1);
                        Montrons(i+1,j);
                        Montrons(i-1,j);
                        Montrons(i,j+1);
                        Montrons(i,j-1);
                        Montrons(i+1,j+1);
                        Montrons(i-1,j-1);
                        Montrons(i+1,j-1);
                    }
        }
    }
    
    public void Gagne(){
        // si le nombre de cases qui reste est egal au nb de bombes
        comptemine = 0;
        int compte = 0;
        for (int i = 0; i < taille; i++) 
        {
            for (int j = 0; j < taille; j++) 
            {
                if((grilletab.grille[i][j].visible == 0)||(grilletab.grille[i][j].visible == 3)||(grilletab.grille[i][j].visible ==  2)) //on compte le nombre de cases cachées
                {
                    compte ++; // ça ne compte pas les cases avec les drapeaux
                }
                if(((grilletab.grille[i][j].visible ==0)||(grilletab.grille[i][j].drapeau == true)||(grilletab.grille[i][j].doubt == true))&&(grilletab.grille[i][j].mine == true)){
                    comptemine++; // pareil
                }
            }
        }
        if ((comptemine == grilletab.nbmines)&&(comptemine == compte)){ // Si le nombre de mine est égal au nombre de cases cachées restantes
            // ET que le nombre de mines est bien celui de départ
            Message.setText("Victoire !"); 
            for (int i = 0; i < taille; i++) 
        {
            for (int j = 0; j < taille; j++) 
            {
                // On affiche toutes les cases
                if(grilletab.grille[i][j].mine == true){
                grilletab.grille[i][j].case_decouverte = true;
                grilletab.grille[i][j].visible = 1;
                repaint();}
            }
        }
            victoire = true; // Permet d'afficher le message de victoire à gauche
        }
        
    }
    
    public void Perdu(CaseGraph c)
    {
        if((c.mine == true)&&(c.case_decouverte == false)&&(select_drapeau == false)&&(select_doubt == false))
        {
        defaite = true;
        Message.setText("Perdu"); // Permet d'afficher le message de défaite à gauche

        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        panel = new javax.swing.JPanel();
        Titre = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Drapeau = new javax.swing.JToggleButton();
        Doubt = new javax.swing.JToggleButton();
        Message = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        Etat = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setLayout(new java.awt.GridLayout(5, 6));

        Titre.setText("Jeu du Demineur");

        jLabel2.setText("Objets");

        Drapeau.setText("jToggleButton1");

        Doubt.setText("jToggleButton1");

        Message.setText("jLabel4");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        Etat.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Etat))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(Message, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(58, 58, 58))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(Doubt, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(Drapeau, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(20, 20, 20))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel4))))
            .addGroup(layout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(Titre, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Titre, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Drapeau, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(51, 51, 51)
                        .addComponent(Doubt)
                        .addGap(34, 34, 34)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(Etat)
                        .addGap(45, 45, 45)
                        .addComponent(Message)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton Doubt;
    private javax.swing.JToggleButton Drapeau;
    private javax.swing.JLabel Etat;
    private javax.swing.JLabel Message;
    private javax.swing.JLabel Titre;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panel2;
    // End of variables declaration//GEN-END:variables
}
