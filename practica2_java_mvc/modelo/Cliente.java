package modelo;

import java.util.GregorianCalendar;

public class Cliente {

    private int numero;
    private String nombre;
    private float saldo;
    private GregorianCalendar fechaNac;
    private float saldoMax;
    
    public Cliente(int numero, String nombre, float saldo, GregorianCalendar fechaNac, float saldoMax) {
        this.numero = numero;
        this.nombre = nombre;
        this.saldoMax = saldoMax;
        setSaldo(saldo);
        setFechaNac(fechaNac);
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getSaldo() {
        return saldo;
    }
    
    // Validar el saldo
    private void setSaldo(float saldo) {
        if(saldo <= saldoMax) {
            this.saldo = saldo;
        } else {
            throw new IllegalArgumentException("Saldo superior al saldo máximo");
        }
    }

    public GregorianCalendar getFechaNac() {
        return fechaNac;
    }
    
    // Validar el rango del año
    private void setFechaNac(GregorianCalendar fechaNac) {
        int anio = fechaNac.get(GregorianCalendar.YEAR);
        if(anio >= 1900 && anio <= 2015) {
            this.fechaNac = fechaNac;
        } else {
            throw new IllegalArgumentException("Fecha fuera de rango");
        }
    }

    public float getSaldoMax() {
        return saldoMax;
    }

    public void setSaldoMax(float saldoMax) {
        this.saldoMax = saldoMax;
    }
    
    @Override
    public String toString() {
        return "CLIENTE: "+"Número ["+numero+"] Nombre: "+nombre+" / Saldo: "+saldo+" / Fecha Nacimiento: "+fechaNac.getTime()+" / Saldo Máximo: "+saldoMax;
    }
}
