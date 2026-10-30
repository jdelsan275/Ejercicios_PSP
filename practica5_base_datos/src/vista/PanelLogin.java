package vista;

import controlador.EquipoDAO;
import controlador.ExcepcionF1;
import controlador.GestorErrores;
import modelo.Equipo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelLogin extends JPanel {
    private final VentanaPrincipal ventana;
    private final EquipoDAO equipoDAO = new EquipoDAO();

    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    public PanelLogin(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        inicializar();
    }

    private void inicializar() {
        JPanel form = new JPanel(new GridLayout(3, 2, 6, 6));
        form.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        form.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField(15);
        form.add(txtUsuario);

        form.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField(15);
        form.add(txtPassword);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.addActionListener(this::accionEntrar);
        form.add(btnEntrar);

        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));
        form.add(btnSalir);

        add(form);
    }

    private void accionEntrar(ActionEvent e) {
        String usuario = txtUsuario.getText().trim();
        String pass = new String(txtPassword.getPassword());

        try {
            Equipo eq = equipoDAO.validarEquipo(usuario, pass);
            if (eq == null) {
                GestorMensajes.mostrarError(GestorErrores.getMensajeError(102));
                return;
            }
            ventana.setEquipoActual(eq);
            ventana.habilitarOpciones(true);
            GestorMensajes.mostrarInfo("Bienvenido: " + eq.getNombreEquipo());
            ventana.mostrarPanelPilotos();
        } catch (ExcepcionF1 ex) {
            GestorMensajes.mostrarError(GestorErrores.getMensajeError(ex.getCodigoError()));
        }
    }
}
