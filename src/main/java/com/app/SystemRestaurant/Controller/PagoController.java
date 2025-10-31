package com.app.SystemRestaurant.Controller;


import com.app.SystemRestaurant.Model.ClasesFInanzas.Pago;
import com.app.SystemRestaurant.Service.PagoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/pagos")
@AllArgsConstructor
public class PagoController {

    @Autowired
    private final PagoService pagoService;

    @GetMapping("/listar")
    @ResponseBody
    public List<Pago> listarPagos() {
        return pagoService.listarPagos();
    }

    @GetMapping("/buscar/{id}")
    @ResponseBody
    public ResponseEntity<Pago> obtenerPagoPorId(@PathVariable int id) {
        return pagoService.obtenerPagoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/actulizar")
    @ResponseBody
    public ResponseEntity<Pago> actualizarPago(@PathVariable int id, @RequestBody Pago pagoActualizado) {
        try {
            Pago pago = pagoService.actualizarPago(id, pagoActualizado);
            return ResponseEntity.ok(pago);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<String> cambiarEstadoPago(@PathVariable Integer id,
                                                    @RequestParam("estadoPago")String nuevoEstado){
        boolean actualizado = pagoService.cambiarEstado(id,nuevoEstado);
        if(actualizado){
            return ResponseEntity.ok("Estado de pago actulizado correctamente");
        }
        return  ResponseEntity.notFound().build();
    }

}