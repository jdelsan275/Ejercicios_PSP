package vista;

import controlador.ConexionBD;
import modelo.Socio;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class VentanaPrincipal extends JFrame {
    private Socio socioActual;

    private CardLayout cardLayout;
    private JPanel panelContenedor;

    private PanelLogin panelLogin;
    private PanelPrestamos panelPrestamos;
    private PanelLibros panelLibros;
    private PanelDatosSocio panelDatosSocio;

    public VentanaPrincipal() {
        setTitle("Gestion Biblioteca");
        setSize(700, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inicializarConexionBD();
        inicializarMenu();
        inicializarPaneles();
        setLocationRelativeTo(null);
    }

    private void inicializarConexionBD() {
        try {
            ConexionBD.abrirConexion();
        } catch (SQLException e) {
            GestorMensajes.mostrarError("No se ha podido conectar con la base de datos.");
            System.exit(1);
        }
    }

    private void inicializarMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu menu = new JMenu("Opciones");

        JMenuItem miPrestamos = new JMenuItem("Ver préstamos");
        miPrestamos.addActionListener(e -> mostrarPanelPrestamos());

        JMenuItem miLibros = new JMenuItem("Ver libros / nuevo préstamo");
        miLibros.addActionListener(e -> mostrarPanelLibros());

        JMenuItem miDatos = new JMenuItem("Ver mis datos");
        miDatos.addActionListener(e -> mostrarPanelDatos());

        JMenuItem miSalir = new JMenuItem("Salir");
        miSalir.addActionListener(e -> System.exit(0));

        menu.add(miPrestamos);
        menu.add(miLibros);
        menu.add(miDatos);
        menu.addSeparator();
        menu.add(miSalir);

        barra.add(menu);
        setJMenuBar(barra);
    }

    private void inicializarPaneles() {
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        panelLogin = new PanelLogin(this);
        panelPrestamos = new PanelPrestamos(this);
        panelLibros = new PanelLibros(this);
        panelDatosSocio = new PanelDatosSocio(this);

        panelContenedor.add(panelLogin, "LOGIN");
        panelContenedor.add(panelPrestamos, "PRESTAMOS");
        panelContenedor.add(panelLibros, "LIBROS");
        panelContenedor.add(panelDatosSocio, "DATOS");

        add(panelContenedor);
        mostrarPanelLogin();
    }

    public void setSocioActual(Socio socio) {
        this.socioActual = socio;
    }

    public Socio getSocioActual() {
        return socioActual;
    }

    public void mostrarPanelLogin() {
        cardLayout.show(panelContenedor, "LOGIN");
    }

    public void mostrarPanelPrincipal() {
        // Por ejemplo, empezar por ver préstamos del socio
        mostrarPanelPrestamos();
    }

    public void mostrarPanelPrestamos() {
        if (socioActual == null) {
            GestorMensajes.mostrarError("Debe validarse antes de acceder a los préstamos.");
            mostrarPanelLogin();
        } else {
            panelPrestamos.cargarPrestamos();
            cardLayout.show(panelContenedor, "PRESTAMOS");
        }
    }

    public void mostrarPanelLibros() {
        if (socioActual == null) {
            GestorMensajes.mostrarError("Debe validarse antes de ver los libros.");
            mostrarPanelLogin();
        } else {
            panelLibros.cargarLibros();
            cardLayout.show(panelContenedor, "LIBROS");
        }
    }

    public void mostrarPanelDatos() {
        if (socioActual == null) {
            GestorMensajes.mostrarError("Debe validarse antes de ver sus datos.");
            mostrarPanelLogin();
        } else {
            panelDatosSocio.mostrarDatos();
            cardLayout.show(panelContenedor, "DATOS");
        }
    }
}
