package org.bonvino.pantallas;

import org.bonvino.interfaces.IObservadorActualizacionDeBodega;

public class PantallaNotificacionActualizacion implements IObservadorActualizacionDeBodega {

    public void actualizarNovedades() {
        System.out.println("Se ha actualizado la bodega");
    }
}
