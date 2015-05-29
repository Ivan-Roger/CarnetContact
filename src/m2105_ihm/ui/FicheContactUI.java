/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import m2105_ihm.nf.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author IUT2
 */
public class FicheContactUI extends JPanel {

    private CarnetUI         carnet;
    
    private JTextField      champNom;
    private JTextField      champPrenom;
    private JTextField      champEmail;
    private JComboBox      champDateJour;
    private JComboBox      champDateMois;
    private JComboBox      champDateAnnee;
    private HashMap<DispoSortie,JRadioButton> sorties;
    private ButtonGroup     groupDispo;
    private JTextField      champTel;
    private JComboBox      champRegion;
    private JButton         btnSave;
    private JButton         btnCancel;
    
    /**
     * Formulaire pour saisir les informations relatives à un contact
     */
    public FicheContactUI(CarnetUI carnet) {
        super();
        
        this.carnet      = carnet;
              
        initUIComponents();
        initListeners();
    }
    
    /**
     * Crée et positionne les composants graphiques constituant l'interface
     */
    private void initUIComponents() {        
        this.setLayout(new BorderLayout());
        this.add(new javax.swing.JLabel("Fiche Contact", SwingConstants.CENTER),BorderLayout.NORTH);
        
        /*
         * Ajoute un label associé au champ pour la saisie du nom du contact
         */
        JPanel contenu = new JPanel();
        contenu.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.ipadx = 5;
            gbc.ipady = 5;        
            gbc.insets = new Insets(10,0,0,25);
        contenu.add(new JLabel("Nom :", SwingConstants.RIGHT), gbc);
        //Ajoute dans l'IHM un champ pour la saisie/Affichage du nom
        champNom = new JTextField(30);
        gbc.gridx++;
        contenu.add(champNom, gbc);
        
        // Prenom
        gbc.gridx=0; gbc.gridy++;
        contenu.add(new JLabel("Prénom :", SwingConstants.RIGHT), gbc);
        champPrenom = new JTextField(30);
        gbc.gridx++;
        contenu.add(champPrenom, gbc);
        
        // Email
        gbc.gridx=0; gbc.gridy++;
        contenu.add(new JLabel("E-Mail :", SwingConstants.RIGHT), gbc);
        champEmail = new JTextField(30);
        gbc.gridx++;
        contenu.add(champEmail, gbc);
        
        // Tel
        gbc.gridx=0; gbc.gridy++;
        contenu.add(new JLabel("Numéro de tél :", SwingConstants.RIGHT), gbc);
        champTel = new JTextField(14);
        gbc.gridx++;
        contenu.add(champTel, gbc);
        
        // Date de naissance
        gbc.gridx=0; gbc.gridy++;
        contenu.add(new JLabel("Date de naissance :", SwingConstants.RIGHT), gbc);
        JPanel dateP = new JPanel();
        champDateJour = new JComboBox();
        for (Integer i=1; i<=31; i++) {
            champDateJour.addItem(i);
        }
        dateP.add(champDateJour);
        
        champDateMois = new JComboBox(Mois.values());
        dateP.add(champDateMois);
        
        champDateAnnee = new JComboBox();
        for (Integer i=1905; i<=2015; i++) {
            champDateAnnee.addItem(i);
        }
        dateP.add(champDateAnnee);
        gbc.gridx++;
        contenu.add(dateP,gbc);
        
        // Dispo
        gbc.gridx=0; gbc.gridy++;
        JLabel dispoTxt = new JLabel("Disponibilité :", SwingConstants.RIGHT);
        dispoTxt.setVerticalAlignment(SwingConstants.TOP);
        contenu.add(dispoTxt, gbc);
        sorties = new HashMap<DispoSortie,JRadioButton>();
        groupDispo = new ButtonGroup();
        JPanel dispoP = new JPanel();
        dispoP.setLayout(new BoxLayout(dispoP, BoxLayout.X_AXIS));
        for (DispoSortie s : DispoSortie.values()) {
            sorties.put(s,new JRadioButton(s.toString()));
            groupDispo.add(sorties.get(s));
            dispoP.add(sorties.get(s));
        }
        gbc.gridx++;
        contenu.add(dispoP, gbc);
        
        // Region
        gbc.gridx=0; gbc.gridy++;
        contenu.add(new JLabel("Région :", SwingConstants.RIGHT), gbc);
        champRegion = new JComboBox();
        for (Region r : Region.values()) {
            champRegion.addItem(r);
        }
        gbc.gridx++;
        contenu.add(champRegion, gbc);
        
        // Fin du formulaire
        this.add(contenu, BorderLayout.CENTER);
        
        // Boutons
        JPanel btnP = new JPanel();
        btnP.setLayout(new GridLayout(1,2));
        JButton cancel = new JButton("Annuler");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carnet.setContactModified(false);
            }            
        });
        btnP.add(cancel);
        JButton save = new JButton("Enregistrer");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carnet.setContactModified(true);
            }            
        });
        btnP.add(save);
        this.add(btnP, BorderLayout.SOUTH);
    }
    
    /**
     * Affecte des valeurs au formulaire de fiche contact
     * @param contact un contact pour mettre à jour à l'IHM
     * @return vrai si ok
     */
    public boolean setValues(Contact contact) {
        if (contact == null) { return false; }
        
        /** TP 1 : à compléter **/
        
        /*
         * Nom du contact
         */
        champNom.setText(contact.getNom());        
        
        champPrenom.setText(contact.getPrenom());  
        
        champEmail.setText(contact.getEmail());
        
        champTel.setText(contact.getNumeroTelephone());
        
        champRegion.setSelectedItem(contact.getRegion());
        
        champDateJour.setSelectedItem((Integer)contact.getDateNaissanceJour());
        champDateMois.setSelectedItem(contact.getDateNaissanceMois());
        champDateAnnee.setSelectedItem((Integer)contact.getDateNaissanceAnnee());
        
        groupDispo.setSelected(sorties.get(contact.getDisponibilite()).getModel(),true);
        
        return true;
    }
    
    /**
     * Retourne les valeurs associées au formulaire de fiche contact
     * @param contact un contact à mettre à jour à partir de l'IHM
     * @return vrai si ok
     */
    public boolean getValues(Contact contact) {
        if (contact == null) { return false; }
        
        /** TP 1 : à compléter **/
          
        /*
         * Affecte une valeur à l'attribut Nom avec le nom saisi dans le champ
         * correspondant de l'IHM
         */
        contact.setNom(champNom.getText());      
        
        contact.setPrenom(champPrenom.getText());
        
        contact.setEmail(champEmail.getText());
        
        for (DispoSortie d : DispoSortie.values()) {
            if (sorties.get(d).isSelected()) {
                contact.setDisponibilite(d);
            }
        }
        
        contact.setDateNaissance((Integer) champDateJour.getSelectedItem(), (Mois)champDateMois.getSelectedItem(), (Integer) champDateAnnee.getSelectedItem());
        
        contact.setRegion((Region)champRegion.getSelectedItem());
        
        contact.setNumeroTelephone(champTel.getText());

        return true;
    }
    
    /**
     * Initialise la gestion des événements
     */
    private void initListeners() {
        /** TP 5 : à compléter **/ 
    }    
}