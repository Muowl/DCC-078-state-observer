package br.edu.dcc078;

import java.util.Objects;

public class Turma extends Observable {
    private final String nome;

    public Turma(String nome) {
        this.nome = Objects.requireNonNull(nome);
    }

    public void publicarAviso(String aviso) {
        notificarObservadores(nome + ": " + Objects.requireNonNull(aviso));
    }
}
