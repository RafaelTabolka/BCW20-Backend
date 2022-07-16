package com.SoulCode.servicos.Services;

import com.SoulCode.servicos.Models.Cargo;
import com.SoulCode.servicos.Repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    CargoRepository cargoRepository;

    public List<Cargo> mostrarTodosOsCargosService() {
        return cargoRepository.findAll();
    }

    public Cargo mostrarCargoPeloIdService(Integer idCargo) {
      Optional<Cargo> cargo = cargoRepository.findById(idCargo);
      return cargo.orElseThrow();
    }

    public Cargo cadastrarCargoService(Cargo cargo) {
        cargo.setIdCargo(null);
        return cargoRepository.save(cargo);
    }

    public void excluirCargoService(Integer idCargo) {
        cargoRepository.deleteById(idCargo);
    }

    public Cargo editarCargoService(Cargo cargo) {
       return cargoRepository.save(cargo);
    }




}
