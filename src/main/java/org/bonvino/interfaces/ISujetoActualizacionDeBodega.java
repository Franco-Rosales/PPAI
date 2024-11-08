package org.bonvino.interfaces;

public interface ISujetoActualizacionDeBodega {

    public void suscribir(IObservadorActualizacionDeBodega observador);
    public void quitar(IObservadorActualizacionDeBodega observador);
    public void notificarNovedades();
}
