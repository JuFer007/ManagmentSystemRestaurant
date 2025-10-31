package com.app.SystemRestaurant.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagoDTO {
    private int idPago;
    private String codigoPedido;
    private double montoPago;
    private String metodoPago;
    private LocalDate fechaPago;
    private String estadoPago;
}
