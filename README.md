# Inventario y riesgo de quiebre
Stack: Spring Boot 3, PostgreSQL, RabbitMQ, Docker, REST (React como frontend futuro).

## Decisiones
- **Monolito modular**: equipo pequeño, un dominio, transacciones simples. Módulos con fronteras claras (paquetes) para extraer `pronostico` después.
- **Módulos**: `inventario` (existencias por bodega), `pronostico` (consumo esperado y riesgo), `recomendaciones` (transferir vs comprar), `aprobacion` (persona aprueba/rechaza), `auditoria` (quién, cuándo, qué, datos usados).
- **Integración con compras**: adaptador (anti-corruption layer) que llama a la API del sistema de compras solo *después* de la aprobación, con clave de idempotencia.
- **API vs evento**: consulta por **API REST** (la UI lista pendientes); se **publica evento** `RecomendacionAprobada` en RabbitMQ para ejecutar la compra/transferencia de forma asíncrona.
- **Pronóstico caído**: fallback a regla determinista (stock < punto de reorden), marcando `fuente=FALLBACK_REORDEN`; circuit breaker y alerta. Nunca se bloquea la operación.
- **Redis**: no se incluye hasta demostrar necesidad de caché.

## Métricas
Quiebres por producto, tiempo de aprobación, compras urgentes, recomendaciones aceptadas.

## Ejecutar
`cp .env.example .env && docker compose up --build` → http://localhost:8081/api/recommendations
