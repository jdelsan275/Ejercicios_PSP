package vista;

import controlador.PrestamoDAO;
import modelo.Prestamo;
import modelo.Socio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class PanelPrestamos extends JPanel {
    private VentanaPrincipal ventanaPrincipal;
    private PrestamoDAO prestamoDAO;

    private JList<String> listaPrestamos;
    private DefaultListModel<String> modeloLista;
    private JComboBox<String> comboFiltro;
    private JButton btnDevolver;
    private JButton btnActualizar;

    // Mantiene la lista real de préstamos para saber qué hay en cada posición de la JList
    private List<Prestamo> prestamosActuales;

    public PanelPrestamos(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.prestamoDAO = new PrestamoDAO();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        // Norte: filtro
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNorte.add(new JLabel("Filtro:"));

        comboFiltro = new JComboBox<>();
        comboFiltro.addItem("Todos");
        comboFiltro.addItem("Solo sin devolver");
        comboFiltro.addItem("Solo devueltos");
        comboFiltro.addActionListener(this::accionCambiarFiltro);
        panelNorte.add(comboFiltro);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarPrestamos());
        panelNorte.add(btnActualizar);

        add(panelNorte, BorderLayout.NORTH);

        // Centro: lista de préstamos en un JScrollPane
        modeloLista = new DefaultListModel<>();
        listaPrestamos = new JList<>(modeloLista);
        listaPrestamos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(listaPrestamos);
        add(scroll, BorderLayout.CENTER);

        // Sur: botón devolver
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnDevolver = new JButton("Devolver préstamo");
        btnDevolver.addActionListener(this::accionDevolver);
        panelSur.add(btnDevolver);
        add(panelSur, BorderLayout.SOUTH);
    }

    /**
     * Carga los préstamos del socio actual aplicando el filtro seleccionado.
     */
    public void cargarPrestamos() {
        Socio socio = ventanaPrincipal.getSocioActual();
        if (socio == null) {
            modeloLista.clear();
            return;
        }

        Boolean soloDevueltos = null;
        String opcion = (String) comboFiltro.getSelectedItem();
        if ("Solo sin devolver".equals(opcion)) {
            soloDevueltos = Boolean.FALSE;
        } else if ("Solo devueltos".equals(opcion)) {
            soloDevueltos = Boolean.TRUE;
        }

        try {
            prestamosActuales = prestamoDAO.obtenerPrestamosPorSocio(socio.getIdSocio(), soloDevueltos);
            modeloLista.clear();
            for (Prestamo p : prestamosActuales) {
                String texto = formatearPrestamo(p);
                modeloLista.addElement(texto);
            }
        } catch (SQLException ex) {
            GestorMensajes.mostrarError("No se ha podido cargar la lista de préstamos.");
        }
    }

    private String formatearPrestamo(Prestamo p) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nº ").append(p.getNumero());
        sb.append(" | Salida: ").append(p.getFechaSal());
        sb.append(" | Devolución: ");
        if (p.getFechaDev() == null) {
            sb.append("SIN DEVOLVER");
        } else {
            sb.append(p.getFechaDev());
        }
        sb.append(" | Libro ID: ").append(p.getIdLibro());
        return sb.toString();
    }

    private void accionCambiarFiltro(ActionEvent e) {
        cargarPrestamos();
    }

    private void accionDevolver(ActionEvent e) {
        int indice = listaPrestamos.getSelectedIndex();
        if (indice < 0) {
            GestorMensajes.mostrarError("Debe seleccionar un préstamo.");
            return;
        }

        Prestamo seleccionado = prestamosActuales.get(indice);
        if (seleccionado.getFechaDev() != null) {
            GestorMensajes.mostrarInfo("Ese préstamo ya está devuelto.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea marcar este préstamo como devuelto?",
                "Confirmar devolución",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            prestamoDAO.marcarDevuelto(seleccionado.getNumero());
            GestorMensajes.mostrarInfo("Préstamo devuelto correctamente.");
            cargarPrestamos();
        } catch (SQLException ex) {
            GestorMensajes.mostrarError("No se ha podido marcar el préstamo como devuelto.");
        }
    }
}
