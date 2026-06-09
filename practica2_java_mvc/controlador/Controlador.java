package controlador;

import modelo.Cliente;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Controlador extends ArrayList<Cliente> {
    private int actual = -1;
    
    public Controlador() { }
        public boolean esVacia() {
            return this.isEmpty();
        }
        public void siguiente() {
            if(actual < this.size() -1) actual++;
        }
        public void anterior() {
            if(actual > 0) actual--;
        }
        public void alPrimero() {
            if(!esVacia()) actual = 0;
        }
        public void alUltimo() {
            if(!esVacia()) actual = this.size() -1;
        }
        public boolean esPrimero() {
            return actual == 0;
        }
        public boolean esUltimo() {
            return actual == this.size() -1;
        }
        public void addCliente(Cliente c) {
            this.add(c);
            actual = this.size() -1;
        }
        public void borrarCliente() {
            if(!esVacia() && actual >= 0 && actual < this.size()) {
                this.remove(actual);
                if(actual >= this.size()) actual = this.size() -1;
            }
        }
        public Cliente obtenerCliente() {
            if(!esVacia() && actual >= 0 && actual < this.size()) {
                return this.get(actual);
            }
            return null;
        }
        public void ordenarCliente() {
            Collections.sort(this, Comparator.comparingInt(Cliente::getNumero));
        }
}
