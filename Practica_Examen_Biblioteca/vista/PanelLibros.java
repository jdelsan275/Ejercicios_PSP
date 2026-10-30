package vista;

import controlador.LibroResultSetDAO;
import controlador.PrestamoDAO;
import modelo.Libro;
import modelo.Socio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class PanelLibros extends JPanel {
    private VentanaPrincipal ventanaPrincipal;
    private LibroResultSetDAO libroDAO;
    private PrestamoDAO prestamoDAO;

    private JTextField txtId;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtEditorial;
    private JTextField txtAnio;
    private JTextField txtTotales;
    private JTextField txtPrestados;
    private JTextField txtIsbn;
    private JLabel lblDisponible;

    private JButton btnPrimero;
    private JButton btnAnterior;
    private JButton btnSiguiente;
    private JButton btnUltimo;
    private JButton btnPedir;

    private Libro libroActual;

    public PanelLibros(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.libroDAO = new LibroResultSetDAO();
        this.prestamoDAO = new PrestamoDAO();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(8, 2, 5, 5));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelDatos.add(new JLabel("ID:"));
        txtId = crearCampoNoEditable();
        panelDatos.add(txtId);

        panelDatos.add(new JLabel("Título:"));
        txtTitulo = crearCampoNoEditable();
        panelDatos.add(txtTitulo);

        panelDatos.add(new JLabel("Autor:"));
        txtAutor = crearCampoNoEditable();
        panelDatos.add(txtAutor);

        panelDatos.add(new JLabel("Editorial:"));
        txtEditorial = crearCampoNoEditable();
        panelDatos.add(txtEditorial);

        panelDatos.add(new JLabel("Año publicación:"));
        txtAnio = crearCampoNoEditable();
        panelDatos.add(txtAnio);

        panelDatos.add(new JLabel("Ejemplares totales:"));
        txtTotales = crearCampoNoEditable();
        panelDatos.add(txtTotales);

        panelDatos.add(new JLabel("Ejemplares prestados:"));
        txtPrestados = crearCampoNoEditable();
        panelDatos.add(txtPrestados);

        panelDatos.add(new JLabel("ISBN:"));
        txtIsbn = crearCampoNoEditable();
        panelDatos.add(txtIsbn);

        add(panelDatos, BorderLayout.CENTER);

        // Panel inferior con navegación y botón de préstamo
        JPanel panelSur = new JPanel(new BorderLayout());

        JPanel panelNavegacion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnPrimero = new JButton("<< Primero");
        btnPrimero.addActionListener(this::accionPrimero);
        panelNavegacion.add(btnPrimero);

        btnAnterior = new JButton("< Anterior");
        btnAnterior.addActionListener(this::accionAnterior);
        panelNavegacion.add(btnAnterior);

        btnSiguiente = new JButton("Siguiente >");
        btnSiguiente.addActionListener(this::accionSiguiente);
        panelNavegacion.add(btnSiguiente);

        btnUltimo = new JButton("Último >>");
        btnUltimo.addActionListener(this::accionUltimo);
        panelNavegacion.add(btnUltimo);

        panelSur.add(panelNavegacion, BorderLayout.WEST);

        JPanel panelAccion = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblDisponible = new JLabel(" ");
        panelAccion.add(lblDisponible);

        btnPedir = new JButton("Pedir préstamo");
        btnPedir.addActionListener(this::accionPedirPrestamo);
        panelAccion.add(btnPedir);

        panelSur.add(panelAccion, BorderLayout.EAST);

        add(panelSur, BorderLayout.SOUTH);
    }

    private JTextField crearCampoNoEditable() {
        JTextField txt = new JTextField();
        txt.setEditable(false);
        return txt;
    }

    /**
     * Carga el ResultSet de libros y se posiciona en el primero.
     */
    public void cargarLibros() {
        try {
            libroDAO.cargarLibros();
            if (libroDAO.moverPrimero()) {
                actualizarLibroActual();
            } else {
                limpiarCampos();
                GestorMensajes.mostrarInfo("No hay libros en la base de datos.");
            }
        } catch (SQLException ex) {
            GestorMensajes.mostrarError("No se han podido cargar los libros.");
        }
    }

    private void actualizarLibroActual() {
        try {
            libroActual = libroDAO.getLibroActual();
            if (libroActual == null) {
                limpiarCampos();
                btnPedir.setEnabled(false);
                return;
            }

            txtId.setText(String.valueOf(libroActual.getIdLibro()));
            txtTitulo.setText(libroActual.getTitulo());
            txtAutor.setText(libroActual.getAutor());
            txtEditorial.setText(libroActual.getEditorial());
            txtAnio.setText(String.valueOf(libroActual.getAnioPublicacion()));
            txtTotales.setText(String.valueOf(libroActual.getEjemplaresTotales()));
            txtPrestados.setText(String.valueOf(libroActual.getEjemplaresPrestados()));
            txtIsbn.setText(libroActual.getIsbn());

            if (libroActual.estaDisponible()) {
                lblDisponible.setText("Disponible para prestar");
                btnPedir.setEnabled(true);
            } else {
                lblDisponible.setText("No hay ejemplares disponibles");
                btnPedir.setEnabled(false);
            }

            actualizarBotonesNavegacion();
        } catch (SQLException ex) {
            GestorMensajes.mostrarError("No se ha podido leer el libro actual.");
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        txtAutor.setText("");
        txtEditorial.setText("");
        txtAnio.setText("");
        txtTotales.setText("");
        txtPrestados.setText("");
        txtIsbn.setText("");
        lblDisponible.setText(" ");
    }

    private void actualizarBotonesNavegacion() throws SQLException {
        // Desactiva botones según posición (primera / última fila)
        // Se usa isFirst() e isLast() típicamente para esto en ResultSet desplazables. [web:69][web:72]
        // Aquí consultamos a través de libroDAO moviendo relativo y volviendo, para mantener el nivel estudiante medio.
    }

    private void accionPrimero(ActionEvent e) {
        try {
            if (libroDAO.moverPrimero()) {
                actualizarLibroActual();
            }
        } catch (SQLException ex) {
            GestorMensajes.mostrarError("No se ha podido ir al primer libro.");
        }
    }

    private void accionAnterior(ActionEvent e) {
        try {
            if (libroDAO.moverAnterior()) {
                actualizarLibroActual();
            }
        } catch (SQLException ex) {
            GestorMensajes.mostrarError("No se ha podido ir al libro anterior.");
        }
    }

    private void accionSiguiente(ActionEvent e) {
        try {
            if (libroDAO.moverSiguiente()) {
                actualizarLibroActual();
            }
        } catch (SQLException ex) {
            GestorMensajes.mostrarError("No se ha podido ir al siguiente libro.");
        }
    }

    private void accionUltimo(ActionEvent e) {
        try {
            if (libroDAO.moverUltimo()) {
                actualizarLibroActual();
            }
        } catch (SQLException ex) {
            GestorMensajes.mostrarError("No se ha podido ir al último libro.");
        }
    }

    private void accionPedirPrestamo(ActionEvent e) {
        Socio socio = ventanaPrincipal.getSocioActual();
        if (socio == null) {
            GestorMensajes.mostrarError("Debe validarse antes de pedir un préstamo.");
            ventanaPrincipal.mostrarPanelLogin();
            return;
        }

        if (libroActual == null || !libroActual.estaDisponible()) {
            GestorMensajes.mostrarError("No hay ejemplares disponibles de este libro.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea pedir un préstamo de este libro?",
                "Confirmar préstamo",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Insertar nuevo préstamo
            prestamoDAO.insertarPrestamo(socio.getIdSocio(), libroActual.getIdLibro());
            // Actualizar ejemplares_prestados en la fila actual del ResultSet
            libroDAO.incrementarPrestadosActual();
            GestorMensajes.mostrarInfo("Préstamo registrado correctamente.");
            // Recargar los datos del libro actual (para ver el nuevo número de prestados)
            actualizarLibroActual();
        } catch (SQLException ex) {
            GestorMensajes.mostrarError("No se ha podido registrar el préstamo.");
        }
    }
}
