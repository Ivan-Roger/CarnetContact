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
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import m2105_ihm.nf.Contact;
import m2105_ihm.nf.GroupeContacts;
import m2105_ihm.nf.Symbole;

/**
 *
 * @author IUT2
 */
public class FicheGroupeUI extends javax.swing.JPanel {
    /*
     * Composants graphiques constituants l'interface
     */
    private CarnetUI     carnet;
    private ZoneDessinUI zoneDessin;
    private JTextField   nomGroupe;
    private JButton      effacer;
    private JList        symboles;
    private DefaultTableModel contacts;
    private JTable       contactsT;
    private ArrayList<Contact> contactsList;
    private JButton btnView;
    
    /**
     * Creates new form CarnetUI
     */
    public FicheGroupeUI(CarnetUI carnet) { 
        super();
       
        this.carnet = carnet;
        contactsList = new ArrayList<Contact>();
        initUIComponents();    
        initListeners();
    }

    /**
     * Crée et positionne les composants graphiques constituant l'interface
     */
    private void initUIComponents() {
        
        /** TP 2 : à compléter **/
        this.setLayout(new BorderLayout());
                
        // Titre
        this.add(new JLabel("Fiche groupe",SwingConstants.CENTER), BorderLayout.NORTH);
        
        JPanel contenu = new JPanel();
        contenu.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.ipadx = 5;
            gbc.ipady = 5;        
            gbc.insets = new Insets(10,0,0,25);
            
        // Nom du groupe
        contenu.add(new JLabel("Nom du Groupe :",SwingConstants.RIGHT), gbc);
        nomGroupe = new JTextField(30);
        gbc.gridx++;
        contenu.add(nomGroupe, gbc);
        
        // Logo
        gbc.gridx=0; gbc.gridy++;
        JPanel logoP = new JPanel();
        logoP.setLayout(new BorderLayout());
        logoP.add(new JLabel("Logo : ",SwingConstants.RIGHT), BorderLayout.NORTH);
        effacer = new JButton("Effacer");
        effacer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zoneDessin.effacer();
            }
        });
        logoP.add(effacer, BorderLayout.SOUTH);
        contenu.add(logoP, gbc);
        
        zoneDessin = new ZoneDessinUI();
        gbc.gridx++;
        contenu.add(zoneDessin, gbc);
        
        // Contacts
        gbc.gridx=0; gbc.gridy++;
        JPanel contactsLblP = new JPanel();
        contactsLblP.setLayout(new BorderLayout());
        contactsLblP.add(new JLabel("Contacts :",SwingConstants.RIGHT), BorderLayout.NORTH);
        btnView = new JButton("Voir Fiche");
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = contactsT.getSelectedRow();
                carnet.controleur.showContact(contactsList.get(index));
            }            
        });
        contactsLblP.add(btnView, BorderLayout.SOUTH);
        contenu.add(contactsLblP, gbc);
        JPanel contactsP = new JPanel();
        contactsP.setLayout(new BorderLayout());
        String[] colonnes = {"Nom","Prénom"};
        contacts = new DefaultTableModel();
        contacts.setColumnIdentifiers(colonnes);
        contactsT = new JTable(contacts);
        contactsT.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                btnView.setEnabled(true);
            }
        });
        contactsP.add(contactsT.getTableHeader(), BorderLayout.NORTH);
        contactsP.add(contactsT, BorderLayout.CENTER);
        gbc.gridx++;
        contenu.add(contactsP, gbc);
        
        // Symboles
        gbc.gridx=0; gbc.gridy++;
        contenu.add(new JLabel("Symboles :",SwingConstants.RIGHT), gbc);
        symboles = new JList(Symbole.values());
        gbc.gridx++;
        contenu.add(symboles, gbc);
        
        this.add(contenu, BorderLayout.CENTER);
        
        // Boutons
        JPanel btnP = new JPanel();
        btnP.setLayout(new GridLayout(1,2));
        JButton cancel = new JButton("Annuler");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carnet.setGroupeModified(false);
            }            
        });
        btnP.add(cancel);
        JButton save = new JButton("Enregistrer");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carnet.setGroupeModified(true);
            }            
        });
        btnP.add(save);
        this.add(btnP, BorderLayout.SOUTH);
    }

    /**
     * Affecte des valeurs au formulaire de fiche groupe de contacts
     * @param groupe groupe de contacts
     * @return
    */    
    public boolean setValues(GroupeContacts groupe) {
        if (groupe == null) { return false; }
        
        /** TP 2 : à compléter **/
        nomGroupe.setText(groupe.getNom());
        contacts.setRowCount(0);
        contactsList.clear();
        btnView.setEnabled(false);
        for (Contact c : groupe.getContacts()) {
            String [] val = { c.getNom(), c.getPrenom() };
            contacts.addRow(val);
            contactsList.add(c);
        }
        contactsT.setModel(contacts);
        int[] selected = new int[groupe.getSymboles().length];
        int i = 0;
        for (Symbole s : groupe.getSymboles()) {
            selected[i] = s.ordinal();
            i++;
        }
        symboles.removeSelectionInterval(0, Symbole.values().length);
        symboles.setSelectedIndices(selected);
        
        zoneDessin.setPoints(groupe.getPoints());
        
        return true;
    }
    
    /**
     * Retourne les valeurs associées au formulaire de fiche groupe de contacts
     * @return
     */    
    public boolean getValues(GroupeContacts groupe) {
        if (groupe == null) { return false; }
        
        /** TP 2 : à compléter **/
        groupe.setNom(nomGroupe.getText());
        groupe.clearPoints();
        groupe.setPoints(zoneDessin.getPoints());
        for (Symbole s : groupe.getSymboles()) {
            groupe.removeSymbole(s);
        }
        for (Object s : symboles.getSelectedValuesList().toArray()) {
            groupe.addSymbole((Symbole)s);
            System.out.println("DEBUG : "+s.getClass().getSimpleName()+" - "+s);
        }
        /*
         * Ne pas s'occuper des membres du groupe car traité via des
         * commandes du menu qui appelera setValues
         */
        
        return true;
    }
    
    /**
     * Initialise la gestion des événements
     */
    public void initListeners() {        
        /*
         * Réagit aux évènements produits par le bouton effacer
         */
        
        /** TP 2 : à compléter **/
    }    
}
