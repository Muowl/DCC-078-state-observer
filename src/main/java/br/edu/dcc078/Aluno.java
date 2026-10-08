package br.edu.dcc078;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Aluno implements Observer {
    private final String nome;
    private AlunoEstado estado = new AlunoEstadoMatriculado();
    private final List<String> avisos = new ArrayList<>();

    public Aluno(String nome) {
        this.nome = Objects.requireNonNull(nome);
    }

    public String getNome() { return nome; }
    public AlunoEstado getEstado() { return estado; }
    public List<String> getAvisos() { return List.copyOf(avisos); }

    void setEstado(AlunoEstado estado) {
        this.estado = Objects.requireNonNull(estado);
    }

    public void matricular() { estado.matricular(this); }
    public void formar() { estado.formar(this); }
    public void transferir() { estado.transferir(this); }
    public void jubilar() { estado.jubilar(this); }
    public void evadir() { estado.evadir(this); }
    public void trancar() { estado.trancar(this); }

    public void observar(Turma turma) {
        turma.adicionarObservador(this);
    }

    public void deixarDeObservar(Turma turma) {
        turma.removerObservador(this);
    }

    @Override
    public void atualizar(String aviso) {
        // O Observer entrega o aviso; o State decide como o aluno reage.
        estado.receberAviso(this, aviso);
    }

    void registrarAviso(String aviso) {
        avisos.add(Objects.requireNonNull(aviso));
    }
}
