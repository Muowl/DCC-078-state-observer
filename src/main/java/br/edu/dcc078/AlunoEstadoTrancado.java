package br.edu.dcc078;

public class AlunoEstadoTrancado extends AlunoEstado {
    @Override
    public String getNome() { return "Trancado"; }

    @Override
    public void matricular(Aluno aluno) { aluno.setEstado(new AlunoEstadoMatriculado()); }
    @Override
    public void transferir(Aluno aluno) { aluno.setEstado(new AlunoEstadoTransferido()); }
    @Override
    public void jubilar(Aluno aluno) { aluno.setEstado(new AlunoEstadoJubilado()); }
    @Override
    public void evadir(Aluno aluno) { aluno.setEstado(new AlunoEstadoEvadido()); }
}
