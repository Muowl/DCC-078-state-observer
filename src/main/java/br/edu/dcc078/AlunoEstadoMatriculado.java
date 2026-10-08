package br.edu.dcc078;

public class AlunoEstadoMatriculado extends AlunoEstado {
    @Override
    public String getNome() { return "Matriculado"; }

    @Override
    public void formar(Aluno aluno) { aluno.setEstado(new AlunoEstadoFormado()); }
    @Override
    public void transferir(Aluno aluno) { aluno.setEstado(new AlunoEstadoTransferido()); }
    @Override
    public void jubilar(Aluno aluno) { aluno.setEstado(new AlunoEstadoJubilado()); }
    @Override
    public void evadir(Aluno aluno) { aluno.setEstado(new AlunoEstadoEvadido()); }
    @Override
    public void trancar(Aluno aluno) { aluno.setEstado(new AlunoEstadoTrancado()); }

    @Override
    public void receberAviso(Aluno aluno, String aviso) {
        aluno.registrarAviso(aviso);
    }
}
