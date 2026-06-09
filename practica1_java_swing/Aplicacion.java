package practica1_java_swing;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Aplicacion extends JFrame {
    
    // Etiquetas atributos
    private JLabel numeroLabel;
    private JLabel nombreLabel;
    private JLabel edadLabel;
    private JLabel saldoLabel;
    
    // String atributos
    private static String numeroString = "Número:  ";
    private static String nombreString = "Nombre: ";
    private static String edadString = "Edad: ";
    private static String saldoString = "Saldo: ";
    
    // Campos de texto
    private TextField numeroTexto;
    private TextField nombreTexto;
    private TextField edadTexto;
    private TextField saldoTexto;
    
    // Botones
    private JButton anteriorBtn;
    private JButton siguienteBtn;
    
    private boolean focusIsSet = false;
    
    ArrayList<Cliente> clientes = new ArrayList();
    int indice = 0;
    
    public Aplicacion() {
        super("Clientes");
        
        clientes.add(new Cliente(1, "Fran", 26, 5500.50));
        clientes.add(new Cliente(2, "Rafa", 21, 814));
        clientes.add(new Cliente(3, "Israel", 21, 226.40));
        clientes.add(new Cliente(4, "Unai", 21, 112));
        clientes.add(new Cliente(5, "Cinta", 20, 3022.30));
        
        // Crear etiquetas de atributos y asociarlas a sus strings
        numeroLabel = new JLabel(numeroString);
        nombreLabel = new JLabel(nombreString);
        edadLabel = new JLabel(edadString);
        saldoLabel = new JLabel(saldoString);
        
        // Crear campos de texto y que no se puedan editar
        numeroTexto = new TextField(10);
        nombreTexto = new TextField(10);
        edadTexto = new TextField(10);
        saldoTexto = new TextField(10);
        numeroTexto.setEditable(false);
        nombreTexto.setEditable(false);
        edadTexto.setEditable(false);
        saldoTexto.setEditable(false);
        
        // Crear panel de atributos
        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(numeroLabel);
        labelPane.add(nombreLabel);
        labelPane.add(edadLabel);
        labelPane.add(saldoLabel);
        
        // Crear panel de campos de texto
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        fieldPane.add(numeroTexto);
        fieldPane.add(nombreTexto);
        fieldPane.add(edadTexto);
        fieldPane.add(saldoTexto);
        
        // Crear panel que englobe los dos paneles de atributos y campos de texto
        JPanel centro = new JPanel(new BorderLayout());
        centro.add(labelPane, BorderLayout.WEST);
        centro.add(fieldPane, BorderLayout.CENTER);
        
        // Crear botones
        anteriorBtn = new JButton("Anterior");
        siguienteBtn = new JButton("Siguiente");
        // Crear panel que englobe los botones
        JPanel botonPane = new JPanel(new FlowLayout());
        botonPane.add(anteriorBtn);
        botonPane.add(siguienteBtn);
        
        anteriorBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (indice > 0) {
                    indice--;
                    mostrarCliente();
                }
            }
        });

        siguienteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (indice < clientes.size() - 1) {
                    indice++;
                    mostrarCliente();
                }
            }
        });
        
        // Crear panel que englobe todos los paneles creados
        JPanel contentPane = new JPanel(new BorderLayout(10,10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        contentPane.add(centro, BorderLayout.CENTER);
        contentPane.add(botonPane, BorderLayout.SOUTH);
        setContentPane(contentPane);
        
        mostrarCliente();
    }
    
    private void mostrarCliente() {
        Cliente c = clientes.get(indice);
        numeroTexto.setText(String.valueOf(c.getNumero()));
        nombreTexto.setText(c.getNombre());
        edadTexto.setText(String.valueOf(c.getEdad()));
        saldoTexto.setText(String.valueOf(c.getSaldo()));
        anteriorBtn.setEnabled(indice > 0);
        siguienteBtn.setEnabled(indice < clientes.size() - 1);
    }
    
    public static void main(String[] args) {
        final Aplicacion app = new Aplicacion();
        app.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        public void windowActivated(WindowEvent e) {
                app.setFocus();
            }
        });
        app.pack();
        app.setVisible(true);
    }
    
    private void setFocus() {
        if (!focusIsSet) {
            numeroTexto.requestFocus();
            focusIsSet = true;
        }
    }
}