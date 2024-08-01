package hospital;

import java.util.Date;

public class Paciente {
    private int legajo;
    private String nombres;
    private String apellidos;
    private String dni;
    private Date fechaNacimiento;
    private String tipo; // Puede ser "Particular", "Obra Social", "Afiliado"
    private String numeroTarjeta;
    private double porcentajeDescuento;

    // Constructor
    public Paciente(int legajo, String nombres, String apellidos, String dni, Date fechaNacimiento, String tipo, String numeroTarjeta, double porcentajeDescuento) {
        this.legajo = legajo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.tipo = tipo;
        this.numeroTarjeta = numeroTarjeta;
        this.porcentajeDescuento = porcentajeDescuento;
    }

    // Getters y Setters
    public int getLegajo() {
        return legajo;
    }

    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
}
