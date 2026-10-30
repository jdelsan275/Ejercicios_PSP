package vista;

import modelo.Socio;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelDatosSocio extends JPanel {
    private VentanaPrincipal ventanaPrincipal;

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtDni;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextField txtFechaNac;
    private JTextField txtDireccion;

    private JButton btnEdad;

    public PanelDatosSocio(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        JPanel panelDatos = new JPanel(new GridLayout(7, 2, 5, 5));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelDatos.add(new JLabel("ID socio:"));
        txtId = crearCampoNoEditable();
        panelDatos.add(txtId);

        panelDatos.add(new JLabel("Nombre:"));
        txtNombre = crearCampoNoEditable();
        panelDatos.add(txtNombre);

        panelDatos.add(new JLabel("DNI:"));
        txtDni = crearCampoNoEditable();
        panelDatos.add(txtDni);

        panelDatos.add(new JLabel("Email:"));
        txtEmail = crearCampoNoEditable();
        panelDatos.add(txtEmail);

        panelDatos.add(new JLabel("Teléfono:"));
        txtTelefono = crearCampoNoEditable();
        panelDatos.add(txtTelefono);

        panelDatos.add(new JLabel("Fecha nacimiento:"));
        txtFechaNac = crearCampoNoEditable();
        panelDatos.add(txtFechaNac);

        panelDatos.add(new JLabel("Dirección:"));
        txtDireccion = crearCampoNoEditable();
        panelDatos.add(txtDireccion);

        add(panelDatos, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnEdad = new JButton("Edad");
        btnEdad.addActionListener(this::accionEdad);
        panelSur.add(btnEdad);

        add(panelSur, BorderLayout.SOUTH);
    }

    private JTextField crearCampoNoEditable() {
        JTextField txt = new JTextField();
        txt.setEditable(false);
        return txt;
    }

    /**
     * Refresca los datos mostrando la información del socio actual.
     */
    public void mostrarDatos() {
        Socio socio = ventanaPrincipal.getSocioActual();
        if (socio == null) {
            limpiarCampos();
            GestorMensajes.mostrarError("No hay ningún socio validado.");
            return;
        }

        txtId.setText(String.valueOf(socio.getIdSocio()));
        txtNombre.setText(socio.getNombre());
        txtDni.setText(socio.getDni());
        txtEmail.setText(socio.getEmail());
        txtTelefono.setText(socio.getTelefono());
        txtFechaNac.setText(socio.getFechaNac() != null ? socio.getFechaNac().toString() : "");
        txtDireccion.setText(socio.getDireccion());
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtDni.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        txtFechaNac.setText("");
        txtDireccion.setText("");
    }

    private void accionEdad(ActionEvent e) {
        Socio socio = ventanaPrincipal.getSocioActual();
        if (socio == null) {
            GestorMensajes.mostrarError("No hay ningún socio validado.");
            return;
        }

        int edad = socio.getEdad();
        GestorMensajes.mostrarInfo("La edad del socio es: " + edad + " años.");
    }
}
