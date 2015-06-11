/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package m2105_ihm.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import m2105_ihm.nf.Contact;
import m2105_ihm.nf.Evenement;
import m2105_ihm.nf.GroupeContacts;

/**
 *
 * @author laurillau
 */
public class BoiteDialogUI {
    public static boolean afficherConfirmation(JFrame fenetre, String titre, String message) {
        boolean res = false;

        if (titre != null && message != null) {
            String [] choix = new String[] { "Supprimer", "Annuler" }; 
            
            Object selectedValue = JOptionPane.showOptionDialog(fenetre,
                  message, 
                  titre,
                  JOptionPane.DEFAULT_OPTION,
                  JOptionPane.QUESTION_MESSAGE, 
                  null,
                  choix,
                  choix[1]
            );
            res = (((Integer) selectedValue) == 0);
        }
        
        return res;
    }

    /**
     * Boîte de dialogue choisir un groupe auquel ajouter un contact
     * @param titre titre de la fenêtre
     * @param groupes liste des groupes existants
     * @return groupe choisi sinon valeur null
     */    
    public static GroupeContacts afficherChoixGroupe(JFrame fenetre, String titre, GroupeContacts [] groupes) {
        GroupeContacts res = null;
        if (titre == null) { titre = ""; }
        
        /** TP5 : à compléter **/
        res = (GroupeContacts) JOptionPane.showInputDialog(fenetre, "Selectionner le groupe : ", titre, JOptionPane.QUESTION_MESSAGE, null, groupes, groupes[0]);
        return res;
    }    

    public static Contact afficherChoixContact(FenetreUI fenetre, String titre, Contact[] contacts) {
        Contact res = null;
        if (titre == null) { titre = ""; }
        
        /** TP5 : à compléter **/
        res = (Contact) JOptionPane.showInputDialog(fenetre, "Selectionner le contact : ", titre, JOptionPane.QUESTION_MESSAGE, null, contacts, contacts[0]);
        return res;
    }
}
