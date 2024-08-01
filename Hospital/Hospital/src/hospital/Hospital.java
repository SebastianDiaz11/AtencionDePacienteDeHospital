package hospital;

import java.util.*;

public class Hospital {
    private List<Doctor> doctores;
    private List<Paciente> pacientes;
    private List<Atencion> atenciones;
    private double precioAtencion = 1000;
    private double descuentoObraSocial = 200;
    private double porcentajeDescuentoAfiliado = 0.50;

    public Hospital() {
        doctores = new ArrayList<>();
        pacientes = new ArrayList<>();
        atenciones = new ArrayList<>();
    }

    // Método para agregar un doctor
    public boolean agregarDoctor(Doctor doctor) {
        for (Doctor d : doctores) {
            if (d.getMatricula() == doctor.getMatricula()) {
                throw new MatriculaDuplicadaException("Matricula duplicada: " + doctor.getMatricula());
            }
        }
        doctores.add(doctor);
        return true;
    }

    // Método para obtener doctores ordenados por matrícula
    public List<Doctor> obtenerDoctores() {
        doctores.sort(Comparator.comparingInt(Doctor::getMatricula));
        return doctores;
    }

    // Método para agregar un paciente
    public boolean agregarPaciente(Paciente paciente) {
        return pacientes.add(paciente);
    }

    // Método para agregar una atención
    public boolean agregarAtencion(Atencion atencion) {
        return atenciones.add(atencion);
    }

    // Método para diagnosticar una atención
    public boolean diagnosticarAtencion(Long idAtencion, int matriculaDoctor, String diagnostico) {
        for (Atencion atencion : atenciones) {
            if (atencion.getId().equals(idAtencion)) {
                if (atencion.getDoctor().getMatricula() != matriculaDoctor) {
                    throw new TareaNoPermitidaException("El doctor no tiene permitido diagnosticar esta atención.");
                }
                atencion.setDiagnostico(diagnostico);
                return true;
            }
        }
        return false;
    }

    // Método para obtener atenciones de pacientes con obra social atendidas por un doctor
    public List<Atencion> obtenerAtencionesDePacientesConObraSocialAtendidasPorUnDoctor(int matriculaDoctor) {
        List<Atencion> result = new ArrayList<>();
        for (Atencion atencion : atenciones) {
            if (atencion.getDoctor().getMatricula() == matriculaDoctor && "Obra Social".equals(atencion.getPaciente().getTipo())) {
                result.add(atencion);
            }
        }
        return result;
    }

    // Método para obtener el precio de la atención
    public double obtenerPrecioDeLaAtencion(int legajoPaciente) {
        for (Paciente paciente : pacientes) {
            if (paciente.getLegajo() == legajoPaciente) {
                switch (paciente.getTipo()) {
                    case "Afiliado":
                        return precioAtencion * (1 - porcentajeDescuentoAfiliado);
                    case "Obra Social":
                        return precioAtencion - descuentoObraSocial;
                    default:
                        return precioAtencion;
                }
            }
        }
        return 0;
    }

    // Método para obtener la facturación de atenciones por doctor
    public Map<Doctor, Double> obtenerFacturacionDeAtencionesPorDoctor() {
        Map<Doctor, Double> facturacion = new HashMap<>();
        for (Atencion atencion : atenciones) {
            facturacion.put(atencion.getDoctor(), facturacion.getOrDefault(atencion.getDoctor(), 0.0) + atencion.getPrecio());
        }

        // Ordenar el mapa por matrícula de doctor en orden descendente
        Map<Doctor, Double> sortedFacturacion = new TreeMap<>((d1, d2) -> Integer.compare(d2.getMatricula(), d1.getMatricula()));
        sortedFacturacion.putAll(facturacion);

        return sortedFacturacion;
    }
}