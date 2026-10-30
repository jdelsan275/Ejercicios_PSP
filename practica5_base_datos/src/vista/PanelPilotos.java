package vista;

import controlador.*;
import modelo.Equipo;
import modelo.Piloto;
import modelo.Resultado;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelPilotos extends JPanel {
    private final VentanaPrincipal ventana;

    private final PilotoNavegadorDAO nav = new PilotoNavegadorDAO();
    private final ResultadoDAO resultadoDAO = new ResultadoDAO();
    private final PilotoDAO pilotoDAO = new PilotoDAO();

    private Piloto pilotoActual;

    private JTextField txtId, txtNombre, txtNacionalidad, txtFechaNac, txtNumero, txtPuntosTotales, txtPuntosObjetivo, txtMentor;
    
    private JButton btnPrimero, btnAnterior, btnSiguiente, btnUltimo;

    private DefaultListModel<Resultado> modeloResultados;
    private JList<Resultado> listResultados;

    public PanelPilotos(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(10, 10));
        inicializar();
    }

    private void inicializar() {
        JPanel datos = new JPanel(new GridLayout(8, 2, 6, 6));
        datos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtId = campoNoEditable();
        txtNombre = campoNoEditable();
        txtNacionalidad = campoNoEditable();
        txtFechaNac = campoNoEditable();
        txtNumero = campoNoEditable();
        txtPuntosTotales = campoNoEditable();
        txtPuntosObjetivo = campoNoEditable();
        txtMentor = campoNoEditable();

        datos.add(new JLabel("ID Piloto:")); datos.add(txtId);
        datos.add(new JLabel("Nombre:")); datos.add(txtNombre);
        datos.add(new JLabel("Nacionalidad:")); datos.add(txtNacionalidad);
        datos.add(new JLabel("Fecha nacimiento:")); datos.add(txtFechaNac);
        datos.add(new JLabel("Número coche:")); datos.add(txtNumero);
        datos.add(new JLabel("Puntos totales:")); datos.add(txtPuntosTotales);
        datos.add(new JLabel("Puntos objetivo (tope):")); datos.add(txtPuntosObjetivo);
        datos.add(new JLabel("Mentor (ID):")); datos.add(txtMentor);

        add(datos, BorderLayout.NORTH);

        modeloResultados = new DefaultListModel<>();
        listResultados = new JList<>(modeloResultados);
        add(new JScrollPane(listResultados), BorderLayout.CENTER);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.CENTER));

        btnPrimero = new JButton("<< Primero");
        btnAnterior = new JButton("< Anterior");
        btnSiguiente = new JButton("Siguiente >");
        btnUltimo = new JButton("Último >>");

        btnPrimero.addActionListener(e -> moverPrimero());
        btnAnterior.addActionListener(e -> moverAnterior());
        btnSiguiente.addActionListener(e -> moverSiguiente());
        btnUltimo.addActionListener(e -> moverUltimo());

        JButton btnAlta = new JButton("Alta resultado");
        btnAlta.addActionListener(e -> altaResultado());

        JButton btnBaja = new JButton("Baja resultado");
        btnBaja.addActionListener(e -> bajaResultado());

        acciones.add(btnPrimero);
        acciones.add(btnAnterior);
        acciones.add(btnSiguiente);
        acciones.add(btnUltimo);
        acciones.add(btnAlta);
        acciones.add(btnBaja);

        add(acciones, BorderLayout.SOUTH);
    }

    private JTextField campoNoEditable() {
        JTextField t = new JTextField();
        t.setEditable(false);
        return t;
    }

    public void cargarPilotosDelEquipo() {
        Equipo eq = ventana.getEquipoActual();
        if (eq == null) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(103));
            ventana.mostrarPanelLogin();
            return;
        }

        try {
            String sql = "SELECT * FROM piloto WHERE eqpId_equipo = " + eq.getIdEquipo() + " ORDER BY id_piloto";
            nav.iniciar(sql);

            if (nav.irPrimero()) {
                actualizarVistaConPiloto(nav.leer());
            } else {
                limpiar();
                modeloResultados.clear();
                GestorMensajes.mostrarInfo("Este equipo no tiene pilotos.");
            }
        } catch (ExcepcionF1 ex) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(ex.getCodigoError()));
        }
    }

    private void actualizarVistaConPiloto(Piloto p) {
        this.pilotoActual = p;
        if (p == null) {
            limpiar();
            modeloResultados.clear();
            return;
        }

        txtId.setText(String.valueOf(p.getIdPiloto()));
        txtNombre.setText(p.getNombre());
        txtNacionalidad.setText(p.getNacionalidad() != null ? p.getNacionalidad() : "");
        txtFechaNac.setText(p.getFechaNac() != null ? p.getFechaNac().toString() : "");
        txtNumero.setText(p.getNumeroCoche() != null ? String.valueOf(p.getNumeroCoche()) : "");
        txtPuntosTotales.setText(String.valueOf(p.getPuntosTotales()));
        txtPuntosObjetivo.setText(String.valueOf(p.getPuntosObjetivo()));
        txtMentor.setText(p.getPilIdPilotoMentor() != null ? String.valueOf(p.getPilIdPilotoMentor()) : "");

        cargarResultadosDelPiloto();

        if (p.getPuntosTotales() > p.getPuntosObjetivo()) {
            GestorMensajes.mostrarAviso("El piloto ha superado el valor tope de puntos objetivo.");
        }
        
        actualizarEstadoBotones();
    }

    private void cargarResultadosDelPiloto() {
        modeloResultados.clear();
        if (pilotoActual == null) return;

        try {
            List<Resultado> lista = resultadoDAO.obtenerResultadosPorPiloto(pilotoActual.getIdPiloto());
            for (Resultado r : lista) modeloResultados.addElement(r);
        } catch (ExcepcionF1 ex) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(ex.getCodigoError()));
        }
    }

    private void moverPrimero() {
        try {
            if (nav.irPrimero()) actualizarVistaConPiloto(nav.leer());
        } catch (ExcepcionF1 ex) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(ex.getCodigoError()));
        }
    }

    private void moverUltimo() {
        try {
            if (nav.irUltimo()) actualizarVistaConPiloto(nav.leer());
        } catch (ExcepcionF1 ex) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(ex.getCodigoError()));
        }
    }

    private void moverSiguiente() {
        try {
            if (nav.avanzar()) actualizarVistaConPiloto(nav.leer());
        } catch (ExcepcionF1 ex) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(ex.getCodigoError()));
        }
    }

    private void moverAnterior() {
        try {
            if (nav.retroceder()) actualizarVistaConPiloto(nav.leer());
        } catch (ExcepcionF1 ex) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(ex.getCodigoError()));
        }
    }
    
    private void actualizarEstadoBotones() {
        try {
            if (pilotoActual == null) {
                btnPrimero.setEnabled(false);
                btnAnterior.setEnabled(false);
                btnSiguiente.setEnabled(false);
                btnUltimo.setEnabled(false);
                return;
            }

            boolean enPrimero = nav.esPrimero();
            boolean enUltimo = nav.esUltimo();

            btnPrimero.setEnabled(!enPrimero);
            btnAnterior.setEnabled(!enPrimero);
            btnSiguiente.setEnabled(!enUltimo);
            btnUltimo.setEnabled(!enUltimo);

        } catch (ExcepcionF1 ex) {
            // Si hay error leyendo posición, no bloquees la UI
            btnPrimero.setEnabled(true);
            btnAnterior.setEnabled(true);
            btnSiguiente.setEnabled(true);
            btnUltimo.setEnabled(true);
        }
    }

    private void altaResultado() {
        if (pilotoActual == null) return;

        DialogNuevoResultado dlg = new DialogNuevoResultado((JFrame) SwingUtilities.getWindowAncestor(this));
        dlg.setVisible(true);
        if (!dlg.isConfirmado()) return;

        try {
            if (dlg.getFechaCarrera() == null) {
                LogErrores.registrar("Fecha nula en alta de resultado");
                throw new ExcepcionF1(403, "Fecha nula");
            }
            if (dlg.getGranPremio() == null || dlg.getGranPremio().trim().isEmpty()) {
                LogErrores.registrar("Gran premio vacío en alta de resultado");
                throw new ExcepcionF1(303, "Gran premio vacío");
            }

            int filas = resultadoDAO.insertarResultado(
                    pilotoActual.getIdPiloto(),
                    dlg.getFechaCarrera(),
                    dlg.getGranPremio().trim(),
                    dlg.getPosicion(),
                    dlg.getPuntos()
            );

            if (filas > 0) {
                pilotoDAO.recalcularPuntosPiloto(pilotoActual.getIdPiloto());
                Piloto pRecargado = pilotoDAO.obtenerPorId(pilotoActual.getIdPiloto());
                actualizarVistaConPiloto(pRecargado);
                GestorMensajes.mostrarInfo("Resultado insertado.");
            }
        } catch (ExcepcionF1 ex) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(ex.getCodigoError()));
        }
    }

    private void bajaResultado() {
        if (pilotoActual == null) return;

        Resultado sel = listResultados.getSelectedValue();
        if (sel == null) {
            GestorMensajes.mostrarAviso("Selecciona un resultado en la lista para borrar.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this, "¿Borrar resultado seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) return;

        try {
            int filas = resultadoDAO.borrarResultado(sel.getIdResultado());
            if (filas > 0) {
                pilotoDAO.recalcularPuntosPiloto(pilotoActual.getIdPiloto());
                Piloto pRecargado = pilotoDAO.obtenerPorId(pilotoActual.getIdPiloto());
                actualizarVistaConPiloto(pRecargado);
                GestorMensajes.mostrarInfo("Resultado borrado.");
            }
        } catch (ExcepcionF1 ex) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(ex.getCodigoError()));
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtNacionalidad.setText("");
        txtFechaNac.setText("");
        txtNumero.setText("");
        txtPuntosTotales.setText("");
        txtPuntosObjetivo.setText("");
        txtMentor.setText("");
    }
}
