package com.utn.phones.service;

import com.utn.phones.exception.ApiErrorException;
import com.utn.phones.model.Province;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class ProvinceComponent {

    private RestTemplate restTemplate;
    public static String endpoint = "http://localhost:8080/api/provinces/id/cities";

    Province testProvince;

    @PostConstruct
    private void init() {
        restTemplate = new RestTemplateBuilder().build();
    }

    //Consigna primer parcial: Enpoint que devuelva listado de ciudades filtradas por Provincia, ocultar ID.
    //Mi RestTemplate se comunica con mi aplicación inicial y obtiene las ciudades filtradas por provincia.

    public ResponseEntity<List> getCitiesByProvince(Integer id) {
        try {
            String uri = endpoint.replace("id", id.toString());
            ResponseEntity<List> list = restTemplate.getForEntity(uri, List.class);
            return list;
        }
        catch (RuntimeException ex) {
            throw new ApiErrorException();
        }
    }
}