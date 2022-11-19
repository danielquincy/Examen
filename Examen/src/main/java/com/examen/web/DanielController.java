package com.examen.web;

import com.examen.model.entity.Daniel;
import com.google.gson.Gson;
import lombok.Data;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Scope(value = "session")
@Component
@Data
public class DanielController implements Serializable {
    private String saludo = "Hola Daniel";
    private String itNombre1;
    private String itNombre2;
    private String itApellido1;
    private String itApellido2;
    private Integer itEdad;
    private String itCedula;
    private String itSexo;
    private List<Daniel> lista;
    private final String server = "http://localhost:8080/Daniel";
    private final RestTemplate rest;
    private final HttpHeaders headers;

    @Setter
    private HttpStatus status;

    @PostConstruct
    public void init() {
      System.out.println("Holaa....");
    }


    public DanielController() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
        headers.add("Authorization", "Basic " + new String(Base64.getEncoder().encode("UCEM_IRENE:1234".getBytes())));
    }

    public void guardar() throws Exception {
        try{
        Daniel oNuevo = new Daniel();
        oNuevo.setNombre1(itNombre1);
        oNuevo.setNombre2(itNombre2);
        oNuevo.setApellido1(itApellido1);
        oNuevo.setApellido2(itApellido2);
        oNuevo.setCedula(itCedula);
        oNuevo.setEdad(itEdad);
        oNuevo.setSexo(itSexo);

        String uri = server.concat("/guardar");

         lista = Collections.singletonList(post(uri, oNuevo));
        }catch (Exception e){
            throw new Exception();
        }
    }


    public <T> T post(String uri, T obj) {
        String json = new Gson().toJson(obj);
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        ResponseEntity<String> responseEntity = rest.exchange(server + uri, HttpMethod.POST, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return (T) new Gson().fromJson(responseEntity.getBody(),obj.getClass());
    }

}
