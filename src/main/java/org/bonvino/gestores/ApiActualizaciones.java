package org.bonvino.gestores;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bonvino.Models.Vino;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ApiActualizaciones {

    public List<Vino> obtenerActualizaciones(String bodegaSeleccionada) {
        // Construir la URL con el parámetro bodega
        String direccion = "http://localhost:8000/vino/?bodega=";
        String encodedBodega = URLEncoder.encode(bodegaSeleccionada, StandardCharsets.UTF_8);
        String url = direccion + encodedBodega;

        // Crear un cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Construir la solicitud GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;

        try {
            // Enviar la solicitud y obtener la respuesta
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Verificar si la respuesta fue exitosa (código 200)
        if (response.statusCode() == 200) {
            String respuestaConVinos = response.body();
            return parsearRespuesta(respuestaConVinos);
        } else if (response.statusCode() == 404){
            // Si no se encontraron vinos, devolver una lista vacía
            return List.of();
        } else {
            // Si no fue exitosa, lanzar una excepción con el código de estado
            throw new RuntimeException("Error en la respuesta de la API: " + response.statusCode());
        }
    }

    private List<Vino> parsearRespuesta(String respuestaConVinos) {
        Gson gson = new Gson();
        try {
            // Convertir la respuesta en una lista de vinos (porque la API devuelve una lista)
            Type tipoListaVinos = new TypeToken<List<Vino>>() {}.getType();
            return gson.fromJson(respuestaConVinos, tipoListaVinos);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
