package br.edu.dcc078;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class AlunoEstadoTest {
    private record EstadoCaso(String nome, Supplier<AlunoEstado> criar) { }
    private record Operacao(String nome, Consumer<Aluno> executar) { }

    static Stream<Arguments> transicoes() {
        List<EstadoCaso> estados = List.of(
                new EstadoCaso("Matriculado", AlunoEstadoMatriculado::new),
                new EstadoCaso("Trancado", AlunoEstadoTrancado::new),
                new EstadoCaso("Formado", AlunoEstadoFormado::new),
                new EstadoCaso("Transferido", AlunoEstadoTransferido::new),
                new EstadoCaso("Evadido", AlunoEstadoEvadido::new),
                new EstadoCaso("Jubilado", AlunoEstadoJubilado::new));
        List<Operacao> operacoes = List.of(
                new Operacao("matricular", Aluno::matricular),
                new Operacao("formar", Aluno::formar),
                new Operacao("transferir", Aluno::transferir),
                new Operacao("jubilar", Aluno::jubilar),
                new Operacao("evadir", Aluno::evadir),
                new Operacao("trancar", Aluno::trancar));

        // Matriz de transições extraída dos diagramas de estado das aulas.
        Map<String, Map<String, String>> destinos = Map.of(
                "Matriculado", Map.of("formar", "Formado", "transferir", "Transferido",
                        "jubilar", "Jubilado", "evadir", "Evadido", "trancar", "Trancado"),
                "Trancado", Map.of("matricular", "Matriculado", "transferir", "Transferido",
                        "jubilar", "Jubilado", "evadir", "Evadido"));

        return estados.stream().flatMap(estado -> operacoes.stream().map(operacao ->
                Arguments.of(estado.nome(), estado.criar(), operacao.nome(), operacao.executar(),
                        destinos.getOrDefault(estado.nome(), Map.of()).get(operacao.nome()))));
    }

    @ParameterizedTest(name = "{0}: {2}")
    @MethodSource("transicoes")
    void deveRespeitarTransicoesDeCadaEstado(String origem, Supplier<AlunoEstado> criar,
                                            String operacao, Consumer<Aluno> executar,
                                            String destino) {
        Aluno aluno = new Aluno("Ana");
        AlunoEstado estadoInicial = criar.get();
        aluno.setEstado(estadoInicial);

        if (destino == null) {
            IllegalStateException erro = assertThrows(IllegalStateException.class,
                    () -> executar.accept(aluno));
            assertSame(estadoInicial, aluno.getEstado());
            assertTrue(erro.getMessage().contains(operacao));
            assertTrue(erro.getMessage().contains(origem));
        } else {
            executar.accept(aluno);
            assertEquals(destino, aluno.getEstado().getNome());
        }
    }

    @Test
    void deveIniciarMatriculado() {
        assertInstanceOf(AlunoEstadoMatriculado.class, new Aluno("Ana").getEstado());
    }
}
