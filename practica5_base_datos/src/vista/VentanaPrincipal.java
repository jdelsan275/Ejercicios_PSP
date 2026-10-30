package vista;

import controlador.ConexionBD;
import controlador.ExcepcionF1;
import controlador.GestorErrores;
import modelo.Equipo;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private Equipo equipoActual;

    private CardLayout cardLayout;
    private JPanel contenedor;

    private PanelLogin panelLogin;
    private PanelPilotos panelPilotos;
    private PanelPerfilEquipo panelPerfil;
    private PanelAcercaDe panelAcercaDe;

    private JMenuItem miPilotos;
    private JMenuItem miPerfil;
    private JMenuItem miCerrarSesion;
    private JMenuItem miAcercaDe;

    public VentanaPrincipal() {
        setTitle("Gestión F1");
        setSize(980, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarConexion();
        inicializarMenu();
        inicializarPaneles();

        setLocationRelativeTo(null);
    }

    private void inicializarConexion() {
        try {
            ConexionBD.abrirConexion();
        } catch (ExcepcionF1 ex) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(ex.getCodigoError()));
            System.exit(1);
        }
    }

    private void inicializarMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu menu = new JMenu("Opciones");

        miPilotos = new JMenuItem("Ver pilotos");
        miPilotos.addActionListener(e -> mostrarPanelPilotos());

        miPerfil = new JMenuItem("Mi equipo (perfil)");
        miPerfil.addActionListener(e -> mostrarPanelPerfil());

        miCerrarSesion = new JMenuItem("Cerrar sesión");
        miCerrarSesion.addActionListener(e -> cerrarSesion());
        
        miAcercaDe = new JMenuItem("Acerca de");
        miAcercaDe.addActionListener(e -> mostrarPanelAcercaDe());

        JMenuItem miSalir = new JMenuItem("Salir");
        miSalir.addActionListener(e -> System.exit(0));

        menu.add(miPilotos);
        menu.add(miPerfil);
        menu.add(miCerrarSesion);
        menu.addSeparator();
        menu.add(miAcercaDe);
        menu.add(miSalir);

        barra.add(menu);
        setJMenuBar(barra);

        habilitarOpciones(false);
    }

    private void inicializarPaneles() {
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        panelLogin = new PanelLogin(this);
        panelPilotos = new PanelPilotos(this);
        panelPerfil = new PanelPerfilEquipo(this);
        panelAcercaDe = new PanelAcercaDe(this);

        contenedor.add(panelLogin, "LOGIN");
        contenedor.add(panelPilotos, "PILOTOS");
        contenedor.add(panelPerfil, "PERFIL");
        contenedor.add(panelAcercaDe, "ACERCADE");

        add(contenedor);
        mostrarPanelLogin();
    }

    public void habilitarOpciones(boolean habilitar) {
        miPilotos.setEnabled(habilitar);
        miPerfil.setEnabled(habilitar);
        miCerrarSesion.setEnabled(habilitar);
    }

    public void setEquipoActual(Equipo equipoActual) {
        this.equipoActual = equipoActual;
    }

    public Equipo getEquipoActual() {
        return equipoActual;
    }

    public void mostrarPanelLogin() {
        cardLayout.show(contenedor, "LOGIN");
    }

    public void mostrarPanelPilotos() {
        if (equipoActual == null) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(103));
            mostrarPanelLogin();
            return;
        }
        panelPilotos.cargarPilotosDelEquipo();
        cardLayout.show(contenedor, "PILOTOS");
    }

    public void mostrarPanelPerfil() {
        if (equipoActual == null) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(103));
            mostrarPanelLogin();
            return;
        }
        panelPerfil.cargarPerfil();
        cardLayout.show(contenedor, "PERFIL");
    }

    public void cerrarSesion() {
        setEquipoActual(null);
        habilitarOpciones(false);
        mostrarPanelLogin();
        GestorMensajes.mostrarInfo("Sesión cerrada.");
    }
    
    public void mostrarPanelAcercaDe() {
        cardLayout.show(contenedor, "ACERCADE");
    }
    
    public void volverDesdeAcercaDe() {
        if (equipoActual == null) {
            mostrarPanelLogin();
        } else {
            mostrarPanelPilotos();
        }
    }
}
