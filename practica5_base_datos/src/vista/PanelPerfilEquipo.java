package vista;

import com.toedter.calendar.JDateChooser;
import controlador.*;
import modelo.Equipo;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class PanelPerfilEquipo extends JPanel {
    private final VentanaPrincipal ventana;
    private final EquipoDAO equipoDAO = new EquipoDAO();

    private JTextField txtId, txtUsuario, txtNombre, txtPresupuesto;
    private JTextField txtSede;
    private JDateChooser dateChooser;
    private JLabel lblPreview;

    private String logoPathActual;

    public PanelPerfilEquipo(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(10, 10));
        inicializar();
    }

    private void inicializar() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtId = noEditable();
        txtUsuario = noEditable();
        txtNombre = noEditable();
        txtPresupuesto = noEditable();
        txtSede = new JTextField();

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        int fila = 0;
        addFila(form, fila++, "ID equipo:", txtId);
        addFila(form, fila++, "Usuario:", txtUsuario);
        addFila(form, fila++, "Nombre equipo:", txtNombre);
        addFila(form, fila++, "Sede:", txtSede);
        addFila(form, fila++, "Presupuesto:", txtPresupuesto);
        addFila(form, fila++, "Fecha alta:", dateChooser);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(form, BorderLayout.NORTH);
        add(wrapper, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout());
        lblPreview = new JLabel("Sin logo", SwingConstants.CENTER);
        lblPreview.setPreferredSize(new Dimension(360, 360));
        centro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centro.add(lblPreview, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCambiarLogo = new JButton("Cambiar logo...");
        btnCambiarLogo.addActionListener(e -> cambiarLogo());

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());

        acciones.add(btnCambiarLogo);
        acciones.add(btnGuardar);

        add(acciones, BorderLayout.SOUTH);
    }

    private JTextField noEditable() {
        JTextField t = new JTextField();
        t.setEditable(false);
        return t;
    }
    
    private void addFila(JPanel panel, int y, String etiqueta, JComponent comp) {
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = y;
        c1.anchor = GridBagConstraints.WEST;
        c1.insets = new Insets(4, 4, 4, 4);
        panel.add(new JLabel(etiqueta), c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 1;
        c2.gridy = y;
        c2.weightx = 1.0;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.insets = new Insets(4, 4, 4, 4);
        panel.add(comp, c2);
    }

    public void cargarPerfil() {
        Equipo eq = ventana.getEquipoActual();
        if (eq == null) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(103));
            ventana.mostrarPanelLogin();
            return;
        }

        try {
            Equipo recargado = equipoDAO.obtenerPorId(eq.getIdEquipo());
            if (recargado == null) {
                GestorMensajes.mostrarError(GestorErrores.getMensajeError(401));
                return;
            }
            ventana.setEquipoActual(recargado);

            txtId.setText(String.valueOf(recargado.getIdEquipo()));
            txtUsuario.setText(recargado.getUsuario());
            txtNombre.setText(recargado.getNombreEquipo());
            txtSede.setText(recargado.getSede() != null ? recargado.getSede() : "");
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
            txtPresupuesto.setText(nf.format(recargado.getPresupuesto()));

            if (recargado.getFechaAlta() != null) {
                Date d = Date.from(recargado.getFechaAlta().atStartOfDay(ZoneId.systemDefault()).toInstant());
                dateChooser.setDate(d);
            } else {
                dateChooser.setDate(null);
            }

            logoPathActual = recargado.getLogoPath();

            refrescarPreview();

        } catch (ExcepcionF1 ex) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(ex.getCodigoError()));
        }
    }

    private void refrescarPreview() {
        int maxW = 360;
        int maxH = 360;
        
        if (logoPathActual == null || logoPathActual.trim().isEmpty()) {
            lblPreview.setText("Sin logo");
            lblPreview.setIcon(null);
            return;
        }
        File f = new File(logoPathActual);
        if (!f.exists()) {
            lblPreview.setText("Logo no encontrado");
            lblPreview.setIcon(null);
            return;
        }
        ImageIcon icon = new ImageIcon(logoPathActual);
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();

        if (w <= 0 || h <= 0) {
            lblPreview.setText("Logo inválido");
            lblPreview.setIcon(null);
            return;
        }

        double scale = Math.min((double) maxW / w, (double) maxH / h);
        int newW = (int) Math.round(w * scale);
        int newH = (int) Math.round(h * scale);

        Image scaled = icon.getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        lblPreview.setText("");
        lblPreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblPreview.setVerticalAlignment(SwingConstants.CENTER);
        lblPreview.setIcon(new ImageIcon(scaled));
    }

    private void cambiarLogo() {
        JFileChooser chooser = new JFileChooser();
        int ok = chooser.showOpenDialog(this);
        if (ok != JFileChooser.APPROVE_OPTION) return;

        File origen = chooser.getSelectedFile();
        try {
            File carpeta = new File("imagenes");
            if (!carpeta.exists()) carpeta.mkdirs();

            String nombreDestino = "equipo_" + ventana.getEquipoActual().getIdEquipo() + "_" + System.currentTimeMillis() + "_" + origen.getName();
            File destino = new File(carpeta, nombreDestino);

            Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

            logoPathActual = destino.getPath();
            refrescarPreview();

        } catch (Exception ex) {
            LogErrores.registrar(ex);
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(404));
        }
    }

    private void guardar() {
        Equipo eq = ventana.getEquipoActual();
        if (eq == null) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(103));
            return;
        }

        try {
            Date d = dateChooser.getDate();
            if (d == null) {
                LogErrores.registrar("Fecha nula al guardar perfil equipo");
                throw new ExcepcionF1(403, "Fecha nula");
            }
            LocalDate fecha = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String sede = txtSede.getText();

            int filas = equipoDAO.actualizarFechaLogoYSede(eq.getIdEquipo(), fecha, logoPathActual, sede);
            if (filas > 0) {
                GestorMensajes.mostrarInfo("Perfil actualizado.");
                cargarPerfil();
            } else {
                GestorMensajes.mostrarAviso("No se actualizó ninguna fila.");
            }

        } catch (ExcepcionF1 ex) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(ex.getCodigoError()));
        }
    }
}
