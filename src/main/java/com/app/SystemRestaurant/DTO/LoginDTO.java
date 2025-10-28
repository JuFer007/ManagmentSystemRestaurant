package com.app.SystemRestaurant.DTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class LoginDTO {
    private String nombreUsuario;
    private String contraseña;
}
