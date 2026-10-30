package vista;

import javax.swing.*;
import java.awt.*;

public class PanelAcercaDe extends JPanel {
    private final VentanaPrincipal ventana;
    
    public PanelAcercaDe(VentanaPrincipal ventana) {
        this.ventana = ventana;
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setText(
                "Gestión F1\n" +
                "Autor: Jesús Delgado\n" +
                "Versión: 1.2\n\n" +
                "Práctica 5 PSP - Docker / BD f1 PostgreSQL\n" +
                "Tabla A: Equipo (login)\n" +
                "Tabla B: Piloto (navegación)\n" +
                "Tabla C: Resultado (altas/bajas)\n"
        );
        add(new JScrollPane(area), BorderLayout.CENTER);
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> ventana.volverDesdeAcercaDe());
        sur.add(btnVolver);
        add(sur, BorderLayout.SOUTH);
    }
}
