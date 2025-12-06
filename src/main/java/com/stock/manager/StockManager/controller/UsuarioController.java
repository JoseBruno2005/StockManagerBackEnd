package com.stock.manager.StockManager.controller;
import com.stock.manager.StockManager.dto.request.UsuarioDTO;
import com.stock.manager.StockManager.dto.response.UsuarioDTOResponse;
import com.stock.manager.StockManager.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("/save")
    public ResponseEntity<UsuarioDTOResponse> save(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.save(usuarioDTO));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTOResponse usuarioLogado = usuarioService.login(usuarioDTO.getEmail(), usuarioDTO.getPassword());
            return ResponseEntity.ok(usuarioLogado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
