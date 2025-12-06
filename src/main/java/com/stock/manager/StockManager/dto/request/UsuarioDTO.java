package com.stock.manager.StockManager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {
    private Long id;
    @NotBlank(message = "O nome não pode ser vazio.")
    private String name;
    @Email(message = "O email é inválido")
    private String email;
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    private String password;
}
