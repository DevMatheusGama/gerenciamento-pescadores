package dev.matheusGama.gerenciamento_pescadores_api.controller;

import dev.matheusGama.gerenciamento_pescadores_api.dto.request.PescadorRequest;
import dev.matheusGama.gerenciamento_pescadores_api.dto.response.PescadorResponse;
import dev.matheusGama.gerenciamento_pescadores_api.service.PescadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pescadores")
@RequiredArgsConstructor
public class PescadorController {
    private final PescadorService pescadorService;

    @PostMapping
    public ResponseEntity<PescadorResponse> createPescador(@RequestBody PescadorRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pescadorService.createPescador(request));
    }

    @GetMapping
    public ResponseEntity<List<PescadorResponse>> findAllPescadores() {
        return ResponseEntity.ok(pescadorService.fyndAllPescadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PescadorResponse> findByPescadorId(@PathVariable UUID id) {
        return ResponseEntity.ok(pescadorService.fyndByPescadorId(id));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<PescadorResponse> findByPescadorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(pescadorService.fyndByPescadorCpf(cpf));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PescadorResponse> updatePescador(@PathVariable UUID id, @RequestBody PescadorRequest request) {
        return ResponseEntity.ok(pescadorService.updatePescador(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePescador(@PathVariable UUID id) {
        pescadorService.deletePescador(id);
        return ResponseEntity.noContent().build();
    }
}
