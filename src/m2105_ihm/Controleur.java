/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm;

import m2105_ihm.nf.Contact;
import m2105_ihm.nf.GroupeContacts;
import m2105_ihm.nf.NoyauFonctionnel;
import m2105_ihm.nf.Evenement;

import m2105_ihm.ui.CarnetUI;
import m2105_ihm.ui.FenetreUI;
import m2105_ihm.ui.PlanningUI;
import m2105_ihm.ui.BoiteDialogUI;

/**
 *
 * @author IUT2
 */
public class Controleur {

    /*
     * Noyau Fonctionnel
     */
    NoyauFonctionnel nf;

    /*
     * Composants
     */
    private CarnetUI carnetUI;
    private FenetreUI fenetre;
    private PlanningUI planningUI;

    /**
     * Constructeur de la fenêtre principale
     */
    public Controleur() {
        initUI();
        initContent();
    }

    /**
     * Action créer un nouveau contact
     */
    public void creerContact() {

        /**
         * TP5 : à compléter *
         */
        Contact contact = new Contact();
        nf.addContact(contact);
        fenetre.setMenuContactSelected(true);
        carnetUI.ajouterContact(contact);
        carnetUI.setSelectedItem(contact);
    }

    /**
     * Action supprimer contact
     */
    public void supprimerContact() {

        /**
         * TP5 : à compléter *
         */
        Contact contact = carnetUI.getSelectedContact();
        if (BoiteDialogUI.afficherConfirmation(fenetre, contact)) {
            carnetUI.retirerContact(contact);
            nf.removeContact(contact);
        }

    }

    /**
     * Action créer un groupe de contacts
     */
    public void creerGroupe() {

        /**
         * TP5 : à compléter *
         */
        GroupeContacts groupe = new GroupeContacts();
        nf.addGroupe(groupe);
        fenetre.setMenuContactSelected(true);
        carnetUI.ajouterGroupe(groupe);
        carnetUI.setSelectedItem(groupe);

    }

    /**
     * Action supprimer un groupe de contacts
     */
    public void supprimerGroupe() {

        /**
         * TP5 : à compléter *
         */
        GroupeContacts groupe = carnetUI.getSelectedGroupe();
        if (BoiteDialogUI.afficherConfirmation(fenetre, groupe)) {
            carnetUI.retirerGroupe(groupe);
            nf.removeGroupe(groupe);
        }

    }

    /**
     * Action ajouter un contact a un groupe
     */
    public void ajouterContactGroupe() {

        Contact contact = carnetUI.getSelectedContact();
        GroupeContacts groupe = BoiteDialogUI.afficherChoixGroupe(fenetre, "Ajouter a un groupe", nf.getGroupes());
        if (groupe != null) {
            groupe.addContact(contact);
        }
    }

    /**
     * Action supprimer un contact d'un groupe
     */
    public void supprimerContactGroupe() {

        Contact contact = carnetUI.getSelectedContact();
        GroupeContacts groupe = BoiteDialogUI.afficherChoixGroupe(fenetre, "Supprimer d'un groupe", nf.getGroupes());
        if (groupe != null) {
            groupe.removeContact(contact);
        }
    }

    /**
     * Crée un nouvel événement
     */
    public void creerEvenement() {
        Evenement e = new Evenement();
        nf.addEvenement(e);
        planningUI.ajouterEvt(e);
        this.showEvenement(e);
        /**
         * Projet *
         */
    }

    /**
     * Supprime un événement existant
     */
    public void supprimerEvenement() {

        /**
         * Projet *
         */
    }

    /**
     * Ajouter un participant à un événement
     */
    public void ajouterParticipantEvenement() {

        /**
         * Projet *
         */
        Evenement evt = planningUI.getSelectedEvt();
        Contact contact = BoiteDialogUI.afficherChoixContact(fenetre, "Ajouter participant", nf.getContacts());
        if (contact != null) {
            evt.addParticipant(contact);
            planningUI.setSelectedEvt(evt);
        }

    }

    /**
     * Retire un participant d'un événement
     */
    public void retirerParticipantEvenement() {

        /**
         * Projet *
         */
        /**
         * Projet *
         */
        Evenement evt = planningUI.getSelectedEvt();
        Contact contact = BoiteDialogUI.afficherChoixContact(fenetre, "Retirer participant", evt.getParticipants());
        if (contact != null) {
            evt.removeParticipant(contact);
            planningUI.setSelectedEvt(evt);
        }
    }

    /**
     * Met à jour la base de données
     */
    public void enregistrer() {
        nf.updateDB();
    }

    /**
     * Quitter l'application sans enregistrer les modifications
     */
    public void quitter() {
        System.exit(0);
    }

    /**
     * Création des composants constituant la fenêtre principale
     */
    private void initUI() {
        /* Onglets */
        carnetUI = new CarnetUI(this);
        planningUI = new PlanningUI(this);

        /* Fenêtre principale */
        fenetre = new FenetreUI(this);
        fenetre.addTab(carnetUI, "Carnet");     // onglet carnet
        fenetre.addTab(planningUI, "Planning"); // onglet planning
        fenetre.afficher();
    }

    /**
     * Alimente la liste avec la liste des contacts existants
     */
    private void initContent() {
        nf = new NoyauFonctionnel();

        for (Contact c : nf.getContacts()) {
            carnetUI.ajouterContact(c);
        }

        for (GroupeContacts g : nf.getGroupes()) {
            carnetUI.ajouterGroupe(g);
        }

        for (Evenement e : nf.getEvenements()) {
            planningUI.ajouterEvt(e);
        }
        if (nf.getEvenements().length>0) { planningUI.setSelectedEvt(nf.getEvenements()[0]); }

        carnetUI.show();
    }

    public void setContactSelected(boolean selected) {
        fenetre.setMenuContactSelected(selected);

    }

    public void setEvtSelected(boolean selected) {
        fenetre.setMenuEvenementSelected(selected);
    }

    public void showContact(Contact c) {
        fenetre.showtab("Carnet");
        carnetUI.setSelectedItem(c);
    }

    public void showGroupe(GroupeContacts g) {
        fenetre.showtab("Carnet");
        carnetUI.setSelectedItem(g);
    }

    public void showEvenement(Evenement e) {
        fenetre.showtab("Planning");
        planningUI.setSelectedEvt(e);
    }

    public void retirerParticipantEvenement(Contact contact) {
        Evenement evt = planningUI.getSelectedEvt();
        if (contact != null) {
            evt.removeParticipant(contact);
            planningUI.setSelectedEvt(evt);
        }        
    }
}
