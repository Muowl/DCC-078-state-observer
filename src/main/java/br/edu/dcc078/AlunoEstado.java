package br.edu.dcc078;

public abstract class AlunoEstado {
    public abstract String getNome();

    public void matricular(Aluno aluno) { throw transicaoInvalida("matricular"); }
    public void formar(Aluno aluno) { throw transicaoInvalida("formar"); }
    public void transferir(Aluno aluno) { throw transicaoInvalida("transferir"); }
    public void jubilar(Aluno aluno) { throw transicaoInvalida("jubilar"); }
    public void evadir(Aluno aluno) { throw transicaoInvalida("evadir"); }
    public void trancar(Aluno aluno) { throw transicaoInvalida("trancar"); }

    public void receberAviso(Aluno aluno, String aviso) {
        // Estados sem matrícula ativa ignoram os avisos da turma.
    }

    private IllegalStateException transicaoInvalida(String operacao) {
        return new IllegalStateException("Não é possível " + operacao
                + " um aluno no estado " + getNome() + ".");
    }
}
