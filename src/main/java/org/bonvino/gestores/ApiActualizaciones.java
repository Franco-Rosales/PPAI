package org.bonvino.gestores;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bonvino.Models.Vino;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiActualizaciones {

    public List<Vino> obtenerActualizaciones(String bodegaSeleccionada) {
        //Todo: actualizar Api para que reciba un string de una bodega y me traiga los vinos actualizables
        String direccion = "http://localhost:3000/vinos";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Obtener la respuesta y parsearla a una lista de vinos
        String respuestaConActualizaciones = response.body();

        return parsearRespuesta(respuestaConActualizaciones);
    }

    private List<Vino> parsearRespuesta(String respuestaConActualizaciones) {
        Gson gson = new Gson();
        try {
            Type tipoListaVinos = new TypeToken<List<Vino>>() {}.getType();
            return gson.fromJson(respuestaConActualizaciones, tipoListaVinos);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
