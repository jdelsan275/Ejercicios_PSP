package practica1_java_swing;

public class Cliente {
    private int numero;
    private String nombre;
    private int edad;
    private double saldo;
    
    public Cliente(int numero, String nombre, int edad, double saldo) {
        this.numero = numero;
        this.nombre = nombre;
        this.edad = edad;
        this.saldo = saldo;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
}
