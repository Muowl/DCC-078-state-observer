package br.edu.dcc078;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Observable {
    private final Set<Observer> observadores = new LinkedHashSet<>();

    public void adicionarObservador(Observer observador) {
        observadores.add(Objects.requireNonNull(observador));
    }

    public void removerObservador(Observer observador) {
        observadores.remove(observador);
    }

    protected void notificarObservadores(String aviso) {
        // Uma cópia permite que um observador se remova durante a notificação.
        for (Observer observador : Set.copyOf(observadores)) {
            observador.atualizar(aviso);
        }
    }
}
