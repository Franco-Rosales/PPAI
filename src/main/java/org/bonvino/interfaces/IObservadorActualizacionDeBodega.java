package org.bonvino.interfaces;

import java.util.Date;
import java.util.List;

public interface IObservadorActualizacionDeBodega {

    public void actualizarNovedades(String bodegaSeleccionada, List<String> vinos, List<String> usuariosEnofilos, Date fechaHoraActual);
}
