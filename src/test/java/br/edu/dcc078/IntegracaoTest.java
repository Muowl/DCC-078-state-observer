package br.edu.dcc078;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class IntegracaoTest {
    static Stream<Arguments> estadosDoObservador() {
        return Stream.of(
                Arguments.of("Matriculado", (Consumer<Aluno>) aluno -> { }, true),
                Arguments.of("Trancado", (Consumer<Aluno>) Aluno::trancar, false),
                Arguments.of("Formado", (Consumer<Aluno>) Aluno::formar, false),
                Arguments.of("Transferido", (Consumer<Aluno>) Aluno::transferir, false),
                Arguments.of("Evadido", (Consumer<Aluno>) Aluno::evadir, false),
                Arguments.of("Jubilado", (Consumer<Aluno>) Aluno::jubilar, false));
    }

    @ParameterizedTest(name = "Observador {0}")
    @MethodSource("estadosDoObservador")
    void deveReagirAoAvisoConformeEstado(String nomeEstado, Consumer<Aluno> preparar,
                                       boolean recebe) {
        Turma turma = new Turma("DCC078");
        Aluno aluno = new Aluno("Ana");
        aluno.observar(turma);
        preparar.accept(aluno);

        turma.publicarAviso("Avaliação marcada.");

        assertEquals(nomeEstado, aluno.getEstado().getNome());
        assertEquals(recebe ? List.of("DCC078: Avaliação marcada.") : List.of(),
                aluno.getAvisos());
    }

    @Test
    void deveSuspenderAvisosNoTrancamentoERetomarNaRematricula() {
        Turma turma = new Turma("DCC078");
        Aluno aluno = new Aluno("Ana");
        aluno.observar(turma);
        turma.publicarAviso("Primeiro aviso.");

        aluno.trancar();
        turma.publicarAviso("Aviso durante o trancamento.");
        aluno.matricular();
        turma.publicarAviso("Aviso após a rematrícula.");

        assertEquals(List.of("DCC078: Primeiro aviso.", "DCC078: Aviso após a rematrícula."),
                aluno.getAvisos());
    }

    @Test
    void transicaoInvalidaNaoDeveAlterarRecebimento() {
        Turma turma = new Turma("DCC078");
        Aluno aluno = new Aluno("Ana");
        aluno.observar(turma);
        aluno.trancar();

        assertThrows(IllegalStateException.class, aluno::formar);
        turma.publicarAviso("Aviso para matriculados.");

        assertInstanceOf(AlunoEstadoTrancado.class, aluno.getEstado());
        assertTrue(aluno.getAvisos().isEmpty());
    }

    @Test
    void deveNotificarVariosAlunosRespeitandoOEstadoIndividual() {
        Turma turma = new Turma("DCC078");
        Aluno ana = new Aluno("Ana");
        Aluno bruno = new Aluno("Bruno");
        Aluno carla = new Aluno("Carla");
        ana.observar(turma);
        bruno.observar(turma);
        carla.observar(turma);
        bruno.trancar();

        turma.publicarAviso("Entrega disponível.");

        assertEquals(List.of("DCC078: Entrega disponível."), ana.getAvisos());
        assertTrue(bruno.getAvisos().isEmpty());
        assertEquals(ana.getAvisos(), carla.getAvisos());
    }
}
