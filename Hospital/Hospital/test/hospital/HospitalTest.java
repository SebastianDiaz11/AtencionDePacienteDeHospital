package hospital;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HospitalTest {
    private Hospital hospital;

    @Before
    public void setUp() {
        hospital = new Hospital();
    }

    @Test
    public void dadoQueExisteUnHospitalAlAgregarUnDoctorObtengoVerdadero() {
        Doctor doctor = new Doctor("12345678", "Juan", "Perez", new Date(), 90001);
        assertTrue(hospital.agregarDoctor(doctor));
    }

    @Test(expected = MatriculaDuplicadaException.class)
    public void dadoQueExisteUnHospitalConDoctoresAlAgregarUnDoctorConMatriculaDuplicadaSeLanzaUnaMatriculaDuplicadaException() {
        Doctor doctor1 = new Doctor("12345678", "Juan", "Perez", new Date(), 90001);
        Doctor doctor2 = new Doctor("87654321", "Ana", "Garcia", new Date(), 90001);
        hospital.agregarDoctor(doctor1);
        hospital.agregarDoctor(doctor2); // Debe lanzar la excepción
    }

    @Test
    public void dadoQueExisteUnHospitalConDoctoresCuandoLosConosultoObtengoUnaColeccionDeDoctoresOrdenadasPorMatriculaAscendente() {
        Doctor doctor1 = new Doctor("12345678", "Juan", "Perez", new Date(), 90002);
        Doctor doctor2 = new Doctor("87654321", "Ana", "Garcia", new Date(), 90001);
        hospital.agregarDoctor(doctor1);
        hospital.agregarDoctor(doctor2);
        List<Doctor> doctores = hospital.obtenerDoctores();
        assertEquals(90001, doctores.get(0).getMatricula());
        assertEquals(90002, doctores.get(1).getMatricula());
    }

    @Test
    public void dadoQueExisteUnHospitalAlAgregarUnPacienteAfiliadoObtengoVerdadero() {
        Paciente paciente = new Paciente(1, "Carlos", "Lopez", "34567890", new Date(), "Afiliado", null, 0.50);
        assertTrue(hospital.agregarPaciente(paciente));
    }

    @Test
    public void dadoQueExisteUnHospitalConDoctoresYPacientesCuandoQuieroDiagnosticarUnaAtencionDeUnDoctorObtengoVerdaderoYElDiagnosticoEnLaAtencionEstaActualizado() {
        Doctor doctor = new Doctor("12345678", "Juan", "Perez", new Date(), 90001);
        Paciente paciente = new Paciente(1, "Carlos", "Lopez", "34567890", new Date(), "Afiliado", null, 0.50);
        hospital.agregarDoctor(doctor);
        hospital.agregarPaciente(paciente);
        Atencion atencion = new Atencion(1L, doctor, paciente, "Dolor de cabeza", 500, null);
        hospital.agregarAtencion(atencion);
        assertTrue(hospital.diagnosticarAtencion(1L, 90001, "Migraña"));
        assertEquals("Migraña", atencion.getDiagnostico());
    }

    @Test(expected = TareaNoPermitidaException.class)
    public void dadoQueExisteUnHospitalConDoctoresYPacientesCuandoQuieroDiagnosticarUnaAtencionQueNoEsDelDoctorSeLanzaUnaTareaNoPermitidaException() {
        Doctor doctor1 = new Doctor("12345678", "Juan", "Perez", new Date(), 90001);
        Doctor doctor2 = new Doctor("87654321", "Ana", "Garcia", new Date(), 90002);
        Paciente paciente = new Paciente(1, "Carlos", "Lopez", "34567890", new Date(), "Afiliado", null, 0.50);
        hospital.agregarDoctor(doctor1);
        hospital.agregarDoctor(doctor2);
        hospital.agregarPaciente(paciente);
        Atencion atencion = new Atencion(1L, doctor1, paciente, "Dolor de cabeza", 500, null);
        hospital.agregarAtencion(atencion);
        hospital.diagnosticarAtencion(1L, 90002, "Migraña"); // Debe lanzar la excepción
    }

    @Test
    public void dadoQueExisteUnHospitalConDoctoresPacientesYAtencionesCuandoQuieroVerLasAtencionesAPacientesConObraSocialPorDoctorObtengoLasAtencionesParaEseDoctorDePacientesConObraSocial() {
        Doctor doctor = new Doctor("12345678", "Juan", "Perez", new Date(), 90001);
        Paciente paciente1 = new Paciente(1, "Carlos", "Lopez", "34567890", new Date(), "Obra Social", "1234-4567-8901", 0);
        Paciente paciente2 = new Paciente(2, "Ana", "Garcia", "45678901", new Date(), "Particular", null, 0);
        hospital.agregarDoctor(doctor);
        hospital.agregarPaciente(paciente1);
        hospital.agregarPaciente(paciente2);
        Atencion atencion1 = new Atencion(1L, doctor, paciente1, "Dolor de cabeza", 800, null);
        Atencion atencion2 = new Atencion(2L, doctor, paciente2, "Dolor de espalda", 1000, null);
        hospital.agregarAtencion(atencion1);
        hospital.agregarAtencion(atencion2);
        List<Atencion> atencionesObraSocial = hospital.obtenerAtencionesDePacientesConObraSocialAtendidasPorUnDoctor(90001);
        assertEquals(1, atencionesObraSocial.size());
        assertEquals(paciente1.getDni(), atencionesObraSocial.get(0).getPaciente().getDni());
    }

    @Test
    public void dadoQueExisteUnHospitalCuandoConsultoElPrecioDeUnaAtencionParaUnPacienteAfiliadoObtengoElTotal500() {
        Paciente paciente = new Paciente(1, "Carlos", "Lopez", "34567890", new Date(), "Afiliado", null, 0.50);
        hospital.agregarPaciente(paciente);
        assertEquals(500, hospital.obtenerPrecioDeLaAtencion(1), 0);
    }

    @Test
    public void dadoQueExisteUnHospitalCuandoConsultoLaFacturacionDeCadaDoctorObtengoUnMapaPorDoctorYTotalOrdenadoPorMatriculaDeDoctorDescendente() {
        Doctor doctor1 = new Doctor("12345678", "Juan", "Perez", new Date(), 90002);
        Doctor doctor2 = new Doctor("87654321", "Ana", "Garcia", new Date(), 90001);
        Paciente paciente = new Paciente(1, "Carlos", "Lopez", "34567890", new Date(), "Particular", null, 0);
        hospital.agregarDoctor(doctor1);
        hospital.agregarDoctor(doctor2);
        hospital.agregarPaciente(paciente);
        Atencion atencion1 = new Atencion(1L, doctor1, paciente, "Dolor de cabeza", 1000, null);
        Atencion atencion2 = new Atencion(2L, doctor2, paciente, "Dolor de espalda", 1000, null);
        hospital.agregarAtencion(atencion1);
        hospital.agregarAtencion(atencion2);
        Map<Doctor, Double> facturacion = hospital.obtenerFacturacionDeAtencionesPorDoctor();
        List<Doctor> doctoresOrdenados = new ArrayList<>(facturacion.keySet());
        assertEquals(90002, doctoresOrdenados.get(0).getMatricula());
        assertEquals(90001, doctoresOrdenados.get(1).getMatricula());
        assertEquals(1000.0, facturacion.get(doctoresOrdenados.get(0)), 0);
        assertEquals(1000.0, facturacion.get(doctoresOrdenados.get(1)), 0);
    }
}
