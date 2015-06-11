/*
 * Default License :
 * ...
 */
package m2105_ihm.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import m2105_ihm.nf.Contact;
import m2105_ihm.nf.Evenement;
import m2105_ihm.nf.Mois;

/**
 * class : FicheEvenement by : rogeri
 *
 * @author rogeri
 */
public class FicheEvenementUI extends JPanel {

    private PlanningUI planning;
    private JTextField champIntitule;
    private JComboBox champDateJour;
    private JComboBox champDateMois;
    private JComboBox champDateAnnee;
    private ArrayList<Contact> participants;
    private DefaultTableModel modelParticipants;
    private JTable tableParticipants;
    private JButton btnView;
    private JButton btnRem;
    private JButton btnAdd;
    private JButton btnPrev;
    private JButton btnNext;

    public FicheEvenementUI(PlanningUI p) {
        super();
        this.planning = p;
        participants = new ArrayList<Contact>();
        initUIComponents();
    }

    private void initUIComponents() {
        this.setLayout(new BorderLayout());
        JPanel titre = new JPanel();
        titre.setLayout(new GridLayout(3, 3));
        titre.add(new JLabel(""));
        titre.add(new JLabel("Fiche Evenement", SwingConstants.CENTER));
        titre.add(new JLabel(""));
        titre.add(new JLabel(""));
        titre.add(new JLabel(""));
        titre.add(new JLabel(""));
        btnPrev = new JButton("< Precedent");
        btnPrev.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Evenement evt;
                if ((evt = planning.getPrevEvt()) != null) {
                    planning.setSelectedEvt(evt);
                }
            }
        });
        titre.add(btnPrev);
        JButton btnNearest = new JButton("Prochain");
        btnNearest.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Evenement evt;
                if ((evt = planning.getNextEvtFromNow()) != null) {
                    planning.setSelectedEvt(evt);
                }
            }
        });
        titre.add(btnNearest);
        btnNext = new JButton("Suivant >");
        btnNext.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Evenement evt;
                if ((evt = planning.getNextEvt()) != null) {
                    planning.setSelectedEvt(evt);
                }
            }
        });
        titre.add(btnNext);

        this.add(titre, BorderLayout.NORTH);

        JPanel contenu = new JPanel();

        contenu.setLayout(
                new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 5;
        gbc.ipady = 5;
        gbc.insets = new Insets(10, 0, 0, 25);

        // Intitulé
        contenu.add(
                new JLabel("Intitulé :", SwingConstants.RIGHT), gbc);
        gbc.gridx++;
        champIntitule = new JTextField(30);

        contenu.add(champIntitule, gbc);

        // Date
        gbc.gridx = 0;
        gbc.gridy++;
        contenu.add(
        new JLabel("Date :", SwingConstants.RIGHT), gbc);
        JPanel dateP = new JPanel();
        champDateJour = new JComboBox();
        for (Integer i = 1;
                i <= 31; i++) {
            champDateJour.addItem(i);
        }

        dateP.add(champDateJour);

        champDateMois = new JComboBox();
        for (Integer i = 0;
                i <= 11; i++) {
            champDateMois.addItem(Mois.valueOf(i));
        }

        dateP.add(champDateMois);

        champDateAnnee = new JComboBox();
        for (Integer i = 1905;
                i <= 2100; i++) {
            champDateAnnee.addItem(i);
        }

        dateP.add(champDateAnnee);
        gbc.gridx++;
        contenu.add(dateP, gbc);

        // Participants
        gbc.gridx = 0;
        gbc.gridy++;
        JPanel participantsP = new JPanel();

        participantsP.setLayout(
                new BorderLayout());
        participantsP.add(
                new JLabel("Participants :", SwingConstants.RIGHT), BorderLayout.NORTH);
        JPanel participantsP2 = new JPanel();

        participantsP2.setLayout(
                new GridLayout(3, 1));
        btnView = new JButton("Voir fiche");

        btnView.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e
                    ) {
                        int index = tableParticipants.getSelectedRow();
                        planning.controleur.showContact(participants.get(index));
                    }
                }
        );
        btnView.setEnabled(
                false);
        participantsP2.add(btnView);
        btnRem = new JButton("Retirer");

        btnRem.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        planning.controleur.retirerParticipantEvenement(participants.get(tableParticipants.getSelectedRow()));
                    }
                }
        );
        btnRem.setEnabled(
                false);
        participantsP2.add(btnRem);
        btnAdd = new JButton("Ajouter");

        btnAdd.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e
                    ) {
                        planning.controleur.ajouterParticipantEvenement();
                    }
                }
        );
        participantsP2.add(btnAdd);

        participantsP.add(participantsP2, BorderLayout.CENTER);

        contenu.add(participantsP, gbc);
        JPanel tableauP = new JPanel();

        tableauP.setLayout(
                new BorderLayout());
        modelParticipants = new DefaultTableModel();
        String[] colonnes = {"Nom", "Prénom"};

        modelParticipants.setColumnIdentifiers(colonnes);
        tableParticipants = new JTable();

        tableParticipants.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent event) {
                btnView.setEnabled(true);
                btnRem.setEnabled(true);
            }
        }
        );
        tableParticipants.setModel(modelParticipants);
        tableauP.add(tableParticipants.getTableHeader(), BorderLayout.NORTH);
        tableauP.add(tableParticipants, BorderLayout.CENTER);
        gbc.gridx++;
        contenu.add(tableauP, gbc);

        // Fin du formulaire
        this.add(contenu, BorderLayout.CENTER);

        // Boutons
        JPanel btnP = new JPanel();
        btnP.setLayout(new GridLayout(1, 2));
        JButton cancel = new JButton("Annuler");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                planning.setEvenementModified(false);
            }
        });
        btnP.add(cancel);
        JButton save = new JButton("Enregistrer");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                planning.setEvenementModified(true);
            }
        });
        btnP.add(save);
        this.add(btnP, BorderLayout.SOUTH);
    }

    public boolean setValues(Evenement evt, int index) {
        btnPrev.setEnabled(planning.getPrevEvt()!=null);
        btnNext.setEnabled(planning.getNextEvt()!=null);
        if (evt == null) {
            return false;
        } else {
            champIntitule.setText(evt.getIntitule());

            champDateJour.setSelectedItem(evt.getDateJour());
            champDateMois.setSelectedItem(Mois.valueOf(evt.getDateMois()));
            champDateAnnee.setSelectedItem(evt.getDateAnnee());

            modelParticipants.setRowCount(0);
            participants.clear();
            for (Contact c : evt.getParticipants()) {
                String[] ligne = {c.getNom(), c.getPrenom()};
                modelParticipants.addRow(ligne);
                participants.add(c);
            }
            btnView.setEnabled(false);
            btnRem.setEnabled(false);

            return true;
        }
    }

    public boolean getValues(Evenement evt) {
        if (evt == null) {
            return false;
        } else {
            evt.setIntitule(champIntitule.getText());

            evt.setDate((Integer) champDateJour.getSelectedItem(), (Integer) ((Mois)champDateMois.getSelectedItem()).ordinal() , (Integer) champDateAnnee.getSelectedItem());
            return true;
        }
    }
}
