package vista;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DialogNuevoResultado extends JDialog {
    private boolean confirmado = false;

    private JDateChooser dateChooser;
    private JTextField txtGranPremio;
    private JSpinner spPosicion;
    private JSpinner spPuntos;

    public DialogNuevoResultado(JFrame owner) {
        super(owner, "Nuevo Resultado", true);
        setSize(420, 240);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        initUI();
    }

    private void initUI() {
        JPanel centro = new JPanel(new GridLayout(4, 2, 8, 8));
        centro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        centro.add(new JLabel("Fecha carrera:"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        centro.add(dateChooser);

        centro.add(new JLabel("Gran Premio:"));
        txtGranPremio = new JTextField();
        centro.add(txtGranPremio);

        centro.add(new JLabel("Posición:"));
        spPosicion = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        centro.add(spPosicion);

        centro.add(new JLabel("Puntos obtenidos:"));
        spPuntos = new JSpinner(new SpinnerNumberModel(0, 0, 60, 1));
        centro.add(spPuntos);

        add(centro, BorderLayout.CENTER);

        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnOk = new JButton("Aceptar");
        JButton btnCancel = new JButton("Cancelar");

        btnOk.addActionListener(e -> {
            confirmado = true;
            setVisible(false);
        });

        btnCancel.addActionListener(e -> {
            confirmado = false;
            setVisible(false);
        });

        sur.add(btnOk);
        sur.add(btnCancel);

        add(sur, BorderLayout.SOUTH);
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public LocalDate getFechaCarrera() {
        Date d = dateChooser.getDate();
        if (d == null) return null;
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public String getGranPremio() {
        return txtGranPremio.getText();
    }

    public int getPosicion() {
        return (Integer) spPosicion.getValue();
    }

    public int getPuntos() {
        return (Integer) spPuntos.getValue();
    }
}
