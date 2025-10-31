package com.app.SystemRestaurant.Service;

import com.app.SystemRestaurant.Model.ClasesFInanzas.Pago;
import com.app.SystemRestaurant.Repository.PagoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PagoService {

    @Autowired
    private final PagoRepository pagoRepository;

    // Listar todos los pagos
    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    // Buscar pago por ID
    public Optional<Pago> obtenerPagoPorId(int id) {
        return pagoRepository.findById(id);
    }

    // Actualizar un pago existente
    public Pago actualizarPago(int id, Pago pagoActualizado) {
        return pagoRepository.findById(id)
                .map(pago -> {
                    pago.setMontoPago(pagoActualizado.getMontoPago());
                    pago.setMetodoPago(pagoActualizado.getMetodoPago());
                    pago.setFechaPago(pagoActualizado.getFechaPago());
                    pago.setIdPedido(pagoActualizado.getIdPedido());
                    return pagoRepository.save(pago);
                }).orElseThrow(() -> new RuntimeException("Pago no encontrado con ID: " + id));
    }

    public boolean cambiarEstado(Integer id, String nuevoEstado){
        Optional<Pago>optPago = pagoRepository.findById(id);
        if(optPago.isPresent()){
            Pago pago = optPago.get();
            pago.setEstadoPago(nuevoEstado);
            pagoRepository.save(pago);
            return true;
        }
        return false;
    }

}
