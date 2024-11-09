package org.bonvino.interfaces;

import java.util.Date;
import java.util.List;

public interface IObservadorActualizacionDeBodega {

    public void actualizarNovedades(String nombreBodega, List<String> vinos, List<String> usuarioEnofilo, Date fechaHoraActual);
}
