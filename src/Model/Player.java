package Model;

public class Player {
    private int id;
    private String nombre;
    private int numero;
    private double altura;

    public Player(int id, String nombre, int dorsal, double altura) {
        this.id = id;
        this.nombre = nombre;
        this.numero = dorsal;
        this.altura = altura;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumero() {
        return numero;
    }

    public double getAltura() {
        return numero;
        
    }
    
}