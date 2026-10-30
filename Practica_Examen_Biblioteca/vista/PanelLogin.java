package vista;

import controlador.SocioDAO;
import modelo.Socio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class PanelLogin extends JPanel {
    private JTextField txtNombre;
    private JPasswordField txtPassword;
    private JButton btnEntrar;
    private JButton btnCerrar;

    private SocioDAO socioDAO = new SocioDAO();
    private VentanaPrincipal ventanaPrincipal;

    public PanelLogin(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 5, 5));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(15);   // 15 columnas -> tamaño razonable
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Password:"));
        txtPassword = new JPasswordField(15);
        panelFormulario.add(txtPassword);

        btnEntrar = new JButton("Entrar");
        btnEntrar.addActionListener(this::accionEntrar);
        panelFormulario.add(btnEntrar);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> System.exit(0));
        panelFormulario.add(btnCerrar);

        // Opcional: un poco de margen alrededor
        JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contenedor.setBorder(BorderFactory.createEmptyBorder(80, 20, 20, 20));
        contenedor.add(panelFormulario);

        // Añadimos el contenedor al panel principal (que ya tiene FlowLayout)
        add(contenedor);
    }

    private void accionEntrar(ActionEvent e) {
        String nombre = txtNombre.getText();
        String pass = new String(txtPassword.getPassword());
        try {
            Socio s = socioDAO.validarSocio(nombre, pass);
            if (s != null) {
                GestorMensajes.mostrarInfo("Validación correcta. Bienvenido " + s.getNombre());
                ventanaPrincipal.setSocioActual(s);
                ventanaPrincipal.mostrarPanelPrincipal();
            } else {
                GestorMensajes.mostrarError("Usuario o contraseña incorrectos.");
            }
        } catch (SQLException ex) {
            GestorMensajes.mostrarError("Error al validar el socio.");
        }
    }
}
