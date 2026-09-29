package com.demo.inventario;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api")
public class RiskController {
  // Stub: sustituir por consulta a existencias + pronóstico. Si el pronóstico cae, se usa regla de respaldo (punto de reorden).
  @GetMapping("/recommendations")
  public List<Map<String,Object>> recommendations() {
    return List.of(Map.of("producto","Tomate","bodegaOrigen","B2","bodegaDestino","B1",
      "accion","TRANSFERENCIA","riesgoQuiebre",0.82,"estado","PENDIENTE_APROBACION","fuente","FALLBACK_REORDEN"));
  }
  @PostMapping("/recommendations/{id}/approve")
  public Map<String,Object> approve(@PathVariable long id, @RequestHeader("X-User") String user) {
    return Map.of("id",id,"estado","APROBADA","aprobadoPor",user);
  }
}
