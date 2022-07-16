package com.SoulCode.servicos.Controllers;

import com.SoulCode.servicos.Models.Cargo;
import com.SoulCode.servicos.Services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("servicos")
public class CargoController {

    @Autowired
    CargoService cargoService;

    @GetMapping("/cargos")
    public List<Cargo> mostrarTodosOsCargos(){
        List<Cargo> cargos = cargoService.mostrarTodosOsCargosService();
        return cargos;
    }

    @GetMapping("/cargos/{idCargo}")
    public ResponseEntity<Cargo> mostrarCargoPeloId(@PathVariable Integer idCargo){
        Cargo cargo = cargoService.mostrarCargoPeloIdService(idCargo);
        return ResponseEntity.ok().body(cargo);
    }

    @PostMapping("/cargos")
    public ResponseEntity<Cargo> cadastrarCargo(@RequestBody Cargo cargo){
        cargo = cargoService.cadastrarCargoService(cargo);
        URI novaUri = ServletUriComponentsBuilder.fromCurrentRequest().path("id")
                .buildAndExpand(cargo.getIdCargo()).toUri();
        return ResponseEntity.created(novaUri).body(cargo);
    }

    @DeleteMapping("/cargos/{idCargo}")
    public ResponseEntity<Void> excluirCargo(@PathVariable Integer idCargo){
        cargoService.excluirCargoService(idCargo);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cargos/{idCargo}")
    public ResponseEntity<Cargo> editarCargo(@RequestBody Cargo cargo,
                                             @PathVariable Integer idCargo ) {
    cargo.setIdCargo(idCargo);
    Cargo cargo2 =  cargoService.editarCargoService(cargo);
    return ResponseEntity.ok().body(cargo2);
    }
}
