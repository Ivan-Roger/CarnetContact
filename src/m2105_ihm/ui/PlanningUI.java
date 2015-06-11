/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm.ui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import m2105_ihm.Controleur;
import m2105_ihm.nf.Evenement;

/**
 *
 * @author IUT2
 */
public class PlanningUI extends JPanel {

    public Controleur controleur;
    private DefaultListModel modelListeEvt;
    private JList listeEvt;
    private ArrayList<Evenement> events;
    private FicheEvenementUI ficheEvenement;

    /**
     * Constructeur : initialise les composants de l'IHM pour les événements
     *
     * @param une instance du controleur
     */
    public PlanningUI(Controleur ctrl) {
        super();
        controleur = ctrl;
        events = new ArrayList<Evenement>();
        initUIComponents();
    }

    private void initUIComponents() {
        this.setLayout(new BorderLayout());
        modelListeEvt = new DefaultListModel();
        listeEvt = new JList<Evenement>(modelListeEvt);
        listeEvt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listeEvt.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                ficheEvenement.setValues(getSelectedEvt(), listeEvt.getSelectedIndex());
                controleur.setEvtSelected(true);
            }
        });
        JPanel panelListeEvt = new JPanel();
        panelListeEvt.setLayout(new BorderLayout());
        panelListeEvt.setBorder(BorderFactory.createTitledBorder("Evenements"));
        panelListeEvt.add(listeEvt, BorderLayout.CENTER);
        this.add(panelListeEvt, BorderLayout.WEST);
        ficheEvenement = new FicheEvenementUI(this);
        this.add(ficheEvenement, BorderLayout.CENTER);
    }

    /*
     * Retourne l'événement sélectionné
     */
    public Evenement getSelectedEvt() {
        return (Evenement) listeEvt.getSelectedValue();
    }

    /**
     * Ajoute une entrée dans la liste de événements
     *
     * @param title texte affiché dans la liste pour un contact
     * @param Contact objet contact associé
     */
    public boolean ajouterEvt(Evenement evt) {
        if (evt == null) {
            return false;
        } else {
            events.add(evt);
            tri(events);
            modelListeEvt.clear();
            for (Evenement e : events) {
                modelListeEvt.addElement(e);
            }
            return true;
        }
    }

    /**
     * Retire une entrée dans la liste de événements
     *
     * @param title texte affiché dans la liste pour un contact
     * @param Contact objet contact associé
     */
    public boolean retirerEvt(Evenement evt) {
        if (evt == null) {
            return false;
        } else {
            modelListeEvt.removeElement(evt);
            events.remove(evt);
            return true;
        }
    }

    /**
     *
     * @param modified
     */
    public void setEvenementModified(boolean modified) {
        Evenement c = getSelectedEvt();

        if (modified) {
            ficheEvenement.getValues(c);
            tri(events);
            modelListeEvt.clear();
            for (Evenement e : events) {
                modelListeEvt.addElement(e);
            }
            listeEvt.setSelectedIndex(modelListeEvt.indexOf(c));
        } else {
            ficheEvenement.setValues(c, listeEvt.getSelectedIndex());
        }
    }

    public void setSelectedEvt(Evenement evt) {
        int index = modelListeEvt.indexOf(evt);
        modelListeEvt.remove(index);
        modelListeEvt.add(index, evt);
        listeEvt.setSelectedIndex(index);
    }

    public Evenement getPrevEvt() {
        if (listeEvt.getSelectedIndex() < 1) {
            return null;
        } else {
            return events.get(listeEvt.getSelectedIndex() - 1);
        }
    }

    public Evenement getNextEvt() {
        if (listeEvt.getSelectedIndex() > events.size() - 2) {
            return null;
        } else {
            return events.get(listeEvt.getSelectedIndex() + 1);
        }
    }

    public Evenement getNextEvtFromNow() {
        Evenement evt = null;
        GregorianCalendar last = events.get(events.size() - 1).getDate();
        for (Evenement e : events) {
            if (e.getDate().after(new GregorianCalendar()) && e.getDate().before(last)) {
                last = e.getDate();
                evt = e;
            }
        }
        return evt;
    }

    private void tri(ArrayList<Evenement> events) {
        Collections.sort(events, new Comparator<Evenement>() {
            @Override
            public int compare(Evenement evt1, Evenement evt2) {
                return evt1.getDate().compareTo(evt2.getDate());
            }
        });
    }
}
