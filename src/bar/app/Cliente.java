package app;

public class Cliente {
    private String nombre;
    private int edad;
    private int id;
    private String bebida_favorita;

    // Constructor con todos los parámetros
    public Cliente(String nombre, int id, int edad, String bebida_favorita) {
        this.nombre = nombre;
        this.id = id;
        this.edad = edad;
        this.bebida_favorita = bebida_favorita;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public int getId() {
        return id;
    }

    public String getBebida_favorita() {
        return bebida_favorita;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBebida_favorita(String bebida_favorita) {
        this.bebida_favorita = bebida_favorita;
    }

    // toString
    @Override
    public String toString() {
        return "Cliente " + id + " {" + nombre + ", edad: " + edad + ", bebida: " + bebida_favorita + "}";
    }

    // equals
    public boolean equals(Cliente c1) {
        if (c1 == null) return false;
        return this.id == c1.id;
    }
}
