package br.edu.dcc078;

public class Main {
    public static void main(String[] args) {
        Turma turma = new Turma("Engenharia de Software");
        Aluno aluno = new Aluno("Felipe");
        aluno.observar(turma);

        turma.publicarAviso("Primeira avaliação na próxima semana.");
        aluno.trancar();
        turma.publicarAviso("Aviso publicado durante o trancamento.");
        aluno.matricular();
        turma.publicarAviso("Atividade disponível para entrega.");

        System.out.println(aluno.getNome() + " - " + aluno.getEstado().getNome());
        aluno.getAvisos().forEach(System.out::println);
    }
}
