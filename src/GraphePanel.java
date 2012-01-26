

import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;

import javax.swing.JPanel;

/**
 * un panneau de dessin pour le loft
 * 
 * @author moreau
 *
 */
class GraphePanel extends JPanel {
	/**
	 * référence sur la liste des objets à dessiner
	 */
	
	public Image image;
	
	/*public Image getImage(){
		Image image = getToolkit().getImage("smiley.jpg");
		return image;
	}*/
	private LinkedList<ObjetDessinable> listeObjets;
	
	/**
	 * constructeur
	 * 
	 * @param listeObjets référence sur la liste des objets (gérée par la ZoneGraphique)
	 */
	public CartePanel(LinkedList<ObjetDessinable> listeObjets) {
		this.listeObjets = listeObjets;
	}
	
	/**
	 * on redéfinit la méthode paint() : elle se contente d'appeler les méthodes
	 * dessinerObjet() de la liste d'objets dessinables
	 */
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		
		
		// on redessine tout
		for (ObjetDessinable x : listeObjets) {
			x.dessinerObjet(g);
		}
	}
}
