package com.softdevcode.ws_finflow.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AIAnalysisResponse {

    private Integer usuarioId;
    private Map<String, Object> riesgo;
    private Map<String, Object> proyeccion;
    private Map<String, Object> anomalias;
    private Map<String, Object> explicacion;
    private List<String> recomendaciones;
    private Map<String, Object> resumen;

}