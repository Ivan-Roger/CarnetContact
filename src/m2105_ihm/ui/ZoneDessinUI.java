/*
 * Module 2105 : module IHM : Carnet d'adresse
 */
package m2105_ihm.ui;

import java.awt.Point;
import java.awt.Color;
import java.awt.Canvas;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author IUT2
 */
/**
 * 
 * @class ZoneDessinUI
 * Zone d'édition d'un logo
 */
public class ZoneDessinUI extends Canvas  {

    /*
     * Liste des points définissant le logo
     */
    private Polygon polygon;
    
    public ZoneDessinUI() {
        super();
                
        polygon = new Polygon();
        /*
         * Abonne le canvas aux évènements souris
         */
        
        /** TP 2 : à compléter **/
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {
                polygon.addPoint(e.getX(), e.getY());
                repaint();
            }
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        
    }
    
    /**
     * Dessine le contenu du canvas, c'est-à-dire l'icone
     * @param g un contexte graphique
     */
    @Override
    public void paint(Graphics g) {
        Dimension dim = getSize();

        /** TP 2 : à modifier et compléter **/
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, dim.width, dim.height);
        g.setColor(Color.BLUE);
        g.drawRect(0, 0, dim.width-1, dim.height-1);
        g.setColor(Color.RED);        
        g.drawPolygon(polygon);
        g.fillPolygon(polygon);
        g.setColor(Color.BLACK);      
    }

    /**
     * Efface le dessin
     */
    public void effacer() {
        
        /** TP 2 : à compléter **/
        Graphics g = this.getGraphics();
        Dimension dim = getSize();        
        polygon.reset();
        this.repaint();
        System.out.println("Zone de Dessin effacé");
    }
        
    /**
     * Affecte le logo avec un ensemble de points
     * @param points tableau de points
     */
    public void setPoints(Point [] dessin) {
        
        /** TP 2 : à compléter **/
        polygon.reset();
        for (Point p : dessin) {
            polygon.addPoint(p.x,p.y);
        }

        this.repaint();
    }

    /**
     * Retourne les points définissant l'icône
     * @return tableau d'entiers
     */
    public Point [] getPoints() {
        Point [] res;
        
        res = new Point[polygon.npoints];
        for(int i = 0; i < res.length; i++) {
            res[i] = new Point(polygon.xpoints[i], polygon.ypoints[i]);
        }
        
        return res;
    }
        
    /*
     * Taille fixe
     */
    @Override
    public Dimension getSize() {
        return new Dimension(150, 150);        
    }          
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(150, 150);        
    }          
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(150, 150);        
    }          
}