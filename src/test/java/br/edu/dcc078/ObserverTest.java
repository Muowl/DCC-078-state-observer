package br.edu.dcc078;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObserverTest {
    @Test
    void alunoNaoCadastradoNaoDeveReceberAviso() {
        Turma turma = new Turma("DCC078");
        Aluno aluno = new Aluno("Ana");
        turma.publicarAviso("Avaliação marcada.");
        assertTrue(aluno.getAvisos().isEmpty());
    }

    @Test
    void devePararDeNotificarObservadorRemovido() {
        Turma turma = new Turma("DCC078");
        Aluno aluno = new Aluno("Ana");
        aluno.observar(turma);
        turma.publicarAviso("Primeiro aviso.");
        aluno.deixarDeObservar(turma);
        turma.publicarAviso("Segundo aviso.");
        assertEquals(List.of("DCC078: Primeiro aviso."), aluno.getAvisos());
    }

    @Test
    void cadastroRepetidoNaoDeveDuplicarNotificacoes() {
        Turma turma = new Turma("DCC078");
        Aluno aluno = new Aluno("Ana");
        aluno.observar(turma);
        aluno.observar(turma);
        turma.publicarAviso("Aviso único.");
        assertEquals(List.of("DCC078: Aviso único."), aluno.getAvisos());
    }

    @Test
    void alunoPodeObservarDuasTurmasERemoverApenasUma() {
        Turma engenharia = new Turma("Engenharia");
        Turma algoritmos = new Turma("Algoritmos");
        Aluno aluno = new Aluno("Ana");
        aluno.observar(engenharia);
        aluno.observar(algoritmos);
        aluno.deixarDeObservar(engenharia);
        engenharia.publicarAviso("Aviso ignorado.");
        algoritmos.publicarAviso("Aviso recebido.");
        assertEquals(List.of("Algoritmos: Aviso recebido."), aluno.getAvisos());
    }

    @Test
    void historicoNaoDevePermitirAlteracaoExterna() {
        Turma turma = new Turma("DCC078");
        Aluno aluno = new Aluno("Ana");
        aluno.observar(turma);
        turma.publicarAviso("Primeiro aviso.");
        List<String> historico = aluno.getAvisos();
        assertThrows(UnsupportedOperationException.class, historico::clear);
        turma.publicarAviso("Segundo aviso.");
        assertEquals(1, historico.size());
        assertEquals(2, aluno.getAvisos().size());
    }

    @Test
    void observadorPodeSeRemoverDuranteNotificacao() {
        Turma turma = new Turma("DCC078");
        AtomicInteger recebimentos = new AtomicInteger();
        Observer observador = new Observer() {
            @Override
            public void atualizar(String aviso) {
                recebimentos.incrementAndGet();
                turma.removerObservador(this);
            }
        };
        turma.adicionarObservador(observador);
        turma.publicarAviso("Primeiro aviso.");
        turma.publicarAviso("Segundo aviso.");
        assertEquals(1, recebimentos.get());
    }
}
