/*
 * GESTION DE BIBLIOTECA EN JAVA
 */
package biblioteca;

import vista.VentanaPrincipal;

public class Biblioteca {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            VentanaPrincipal v = new VentanaPrincipal();
            v.setVisible(true);
        });
    }
    
}
