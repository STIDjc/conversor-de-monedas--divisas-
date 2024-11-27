package com.jhorman.conversordemonedas.consultas;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jhorman.conversordemonedas.calculos.Conversion;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConsultaApi {
    private final String respuesta;

    public ConsultaApi(String monedaOrigen, String monedaDestino, BigDecimal monto) throws IOException {
        //Lectura de la API Key
        Path myKey = Path.of("key.txt");
        String content = Files.readString(myKey);
        String url = String.format("https://v6.exchangerate-api.com/v6/%s/pair/" + monedaOrigen + "/" + monedaDestino, content);

        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println("No se pudo elaborar la URI");
        }
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        assert response != null;
        ConversionApi conversionApi = gson.fromJson(response.body(), ConversionApi.class);
        var conversion = new Conversion(conversionApi, monto);
        this.respuesta = conversion.toString();
    }

    public String getRespuesta() {
        return this.respuesta;
    }
}
