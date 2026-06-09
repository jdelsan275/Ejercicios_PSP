package vista;

import controlador.Controlador;
import modelo.Cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;

public class Vista extends JFrame {
    private final Controlador controlador;
    
    // Etiquetas, campos de texto y botones
    private final JLabel numeroLabel, nombreLabel, saldoLabel, fechaNacLabel, saldoMaxLabel, errorLabel;
    private final TextField numeroTexto, nombreTexto, saldoTexto, fechaTexto, saldoMaxTexto;
    private final JButton anteriorBtn, siguienteBtn, primeroBtn, ultimoBtn, insertarBtn, borrarBtn, modificarBtn, ordenarBtn, aceptarBtn, cancelarBtn;
    
    public Vista(Controlador controlador) {
        super("Gestión de Clientes");
        this.controlador = controlador;
        setResizable(false); // Tamaño de ventana fijo
        
        numeroLabel = new JLabel("Número: ");
        nombreLabel = new JLabel("Nombre: ");
        saldoLabel = new JLabel("Saldo: ");
        fechaNacLabel = new JLabel("Fecha Nacimiento (dd-mm-aaaa): ");
        saldoMaxLabel = new JLabel("Saldo Máximo: ");
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED); // Mensajes en color rojo
        
        numeroTexto = new TextField(10);
        nombreTexto = new TextField(10);
        saldoTexto = new TextField(10);
        fechaTexto = new TextField(10);
        saldoMaxTexto = new TextField(10);
        
        numeroTexto.setEditable(false);
        nombreTexto.setEditable(false);
        saldoTexto.setEditable(false);
        fechaTexto.setEditable(false);
        saldoMaxTexto.setEditable(false);
        
        // Panel de atributos
        JPanel labelPane = new JPanel(new GridLayout(0, 1));
        labelPane.add(numeroLabel);
        labelPane.add(nombreLabel);
        labelPane.add(saldoLabel);
        labelPane.add(fechaNacLabel);
        labelPane.add(saldoMaxLabel);
        
        // Panel de campos de texto
        JPanel fieldPane = new JPanel(new GridLayout(0, 1));
        fieldPane.add(numeroTexto);
        fieldPane.add(nombreTexto);
        fieldPane.add(saldoTexto);
        fieldPane.add(fechaTexto);
        fieldPane.add(saldoMaxTexto);

        // Panel central con labelPane y fieldPane
        JPanel centro = new JPanel(new BorderLayout());
        centro.add(labelPane, BorderLayout.WEST);
        centro.add(fieldPane, BorderLayout.CENTER);

        // Botones
        siguienteBtn = new JButton("Siguiente");
        anteriorBtn = new JButton("Anterior");
        primeroBtn = new JButton("Primero");
        ultimoBtn = new JButton("Último");
        insertarBtn = new JButton("Insertar");
        borrarBtn = new JButton("Borrar");
        modificarBtn = new JButton("Modificar");
        ordenarBtn = new JButton("Ordenar");
        aceptarBtn = new JButton("Aceptar");
        cancelarBtn = new JButton("Cancelar");
        aceptarBtn.setVisible(false);
        cancelarBtn.setVisible(false);

        // Panel de botones de navegación
        JPanel navPane = new JPanel(new FlowLayout());
        navPane.add(primeroBtn);
        navPane.add(anteriorBtn);
        navPane.add(siguienteBtn);
        navPane.add(ultimoBtn);

        // Panel de botones de acción
        JPanel accionPane = new JPanel(new FlowLayout());
        accionPane.add(insertarBtn);
        accionPane.add(borrarBtn);
        accionPane.add(modificarBtn);
        accionPane.add(ordenarBtn);
        
        // Panel de botones aceptar/cancelar
        JPanel confirmarPane = new JPanel(new FlowLayout());
        confirmarPane.add(aceptarBtn);
        confirmarPane.add(cancelarBtn);

        // Panel inferior con todos los botones
        JPanel botonPane = new JPanel(new GridLayout(3, 1));
        botonPane.add(navPane);
        botonPane.add(accionPane);
        botonPane.add(confirmarPane);

        // Panel final
        JPanel contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.add(errorLabel, BorderLayout.NORTH);
        contentPane.add(centro, BorderLayout.CENTER);
        contentPane.add(botonPane, BorderLayout.SOUTH);
        setContentPane(contentPane);
        
        // Listeners botones
        primeroBtn.addActionListener(e -> { controlador.alPrimero(); mostrarCliente(); });
        anteriorBtn.addActionListener(e -> { controlador.anterior(); mostrarCliente(); });
        siguienteBtn.addActionListener(e -> { controlador.siguiente(); mostrarCliente(); });
        ultimoBtn.addActionListener(e -> { controlador.alUltimo(); mostrarCliente(); });
        insertarBtn.addActionListener(e -> iniciaInsertar());
        modificarBtn.addActionListener(e -> iniciaModificar());
        borrarBtn.addActionListener(e -> iniciaBorrar());
        ordenarBtn.addActionListener(e -> iniciaOrdenar());
        aceptarBtn.addActionListener(e -> aceptarAccion());
        cancelarBtn.addActionListener(e -> cancelarAccion());

        mostrarCliente();
    }
    
    // Estado para saber que acción está activa, se inicializa con "navegación"
    private String estado = "navegacion";

    private void mostrarCliente() {
        Cliente c = controlador.obtenerCliente();
        boolean listaVacia = controlador.esVacia();
        if (c != null) {
            numeroTexto.setText(String.valueOf(c.getNumero()));
            nombreTexto.setText(c.getNombre());
            saldoTexto.setText(String.valueOf(c.getSaldo()));
            fechaTexto.setText(
                String.format("%02d", c.getFechaNac().get(GregorianCalendar.DAY_OF_MONTH)) + "-"
                + String.format("%02d", c.getFechaNac().get(GregorianCalendar.MONTH)+1) + "-"
                + c.getFechaNac().get(GregorianCalendar.YEAR)
            );
            saldoMaxTexto.setText(String.valueOf(c.getSaldoMax()));
        } else {
            numeroTexto.setText("");
            nombreTexto.setText("");
            saldoTexto.setText("");
            fechaTexto.setText("");
            saldoMaxTexto.setText("");
        }
        // Control de navegación/botones
        anteriorBtn.setEnabled(!listaVacia && !controlador.esPrimero());
        siguienteBtn.setEnabled(!controlador.esUltimo());
        primeroBtn.setEnabled(!listaVacia && !controlador.esPrimero());
        ultimoBtn.setEnabled(!controlador.esUltimo());
        borrarBtn.setEnabled(c != null);
        modificarBtn.setEnabled(c != null);
        ordenarBtn.setEnabled(c != null);
        errorLabel.setText("");
        setCamposEditables(false);
        aceptarBtn.setVisible(false);
        cancelarBtn.setVisible(false);
        estado = "navegacion";
    }
    
    private void setCamposEditables(boolean editable) {
        numeroTexto.setEditable(editable);
        nombreTexto.setEditable(editable);
        saldoTexto.setEditable(editable);
        fechaTexto.setEditable(editable);
        saldoMaxTexto.setEditable(editable);
    }
    
    private void iniciaInsertar() {
        setCamposEditables(true);
        numeroTexto.setText("");
        nombreTexto.setText("");
        saldoTexto.setText("");
        fechaTexto.setText("");
        saldoMaxTexto.setText("");
        aceptarBtn.setVisible(true);
        cancelarBtn.setVisible(true);
        estado = "insertar";
    }

    private void iniciaModificar() {
        setCamposEditables(true);
        aceptarBtn.setVisible(true);
        cancelarBtn.setVisible(true);
        estado = "modificar";
    }

    private void iniciaBorrar() {
        aceptarBtn.setVisible(true);
        cancelarBtn.setVisible(true);
        estado = "borrar";
    }
    
    private void iniciaOrdenar() {
        controlador.ordenarCliente();
        errorLabel.setText("Lista ordenada correctamente");
    }

    private void aceptarAccion() {
        try {
            if (estado.equals("insertar") || estado.equals("modificar")) {
                int numero = Integer.parseInt(numeroTexto.getText());
                String nombre = nombreTexto.getText();
                float saldo = Float.parseFloat(saldoTexto.getText());
                float saldoMaximo = Float.parseFloat(saldoMaxTexto.getText());
                String[] fechaPartes = fechaTexto.getText().split("-");
                if (fechaPartes.length != 3) throw new Exception("Formato de fecha incorrecto");
                int dia = Integer.parseInt(fechaPartes[0]);
                int mes = Integer.parseInt(fechaPartes[1]);
                int anio = Integer.parseInt(fechaPartes[2]);
                GregorianCalendar fechaNac = new GregorianCalendar(anio, mes-1, dia);

                // Validaciones
                if (anio < 1900 || anio > 2015) throw new Exception("Año fuera de rango");
                if (saldo < 0 || saldoMaximo < 0) throw new Exception("Saldo inválido");
                if (saldo > saldoMaximo) throw new Exception("El saldo debe ser menor o igual al saldo máximo");

                Cliente nuevoCliente = new Cliente(numero, nombre, saldo, fechaNac, saldoMaximo);
                if (estado.equals("insertar")) {
                    controlador.addCliente(nuevoCliente);
                } else if (estado.equals("modificar")) {
                    controlador.remove(controlador.obtenerCliente());
                    controlador.add(nuevoCliente);
                }
                mostrarCliente();
                errorLabel.setText("Cliente añadido/modificado correctamente");
            } else if (estado.equals("borrar")) {
                controlador.borrarCliente();
                mostrarCliente();
                errorLabel.setText("Cliente borrado correctamente");
            }
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }
    }
    private void cancelarAccion() {
        mostrarCliente();
    }
    
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        Vista app = new Vista(controlador);
        app.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        app.pack();
        app.setVisible(true);
    }
}