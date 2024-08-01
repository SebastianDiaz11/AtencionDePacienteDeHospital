package hospital;

public class Atencion {
    private Long id;
    private Doctor doctor;
    private Paciente paciente;
    private String motivo;
    private double precio;
    private String diagnostico;

    // Constructor
    public Atencion(Long id, Doctor doctor, Paciente paciente, String motivo, double precio, String diagnostico) {
        this.id = id;
        this.doctor = doctor;
        this.paciente = paciente;
        this.motivo = motivo;
        this.precio = precio;
        this.diagnostico = diagnostico;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
}