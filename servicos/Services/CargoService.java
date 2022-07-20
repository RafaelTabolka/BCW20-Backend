package com.SoulCode.servicos.Services;

import com.SoulCode.servicos.Models.Cargo;
import com.SoulCode.servicos.Repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    CargoRepository cargoRepository;

    @Cacheable("cargoCache")
    public List<Cargo> mostrarTodosOsCargosService() {
        return cargoRepository.findAll();
    }

    @CachePut(value = "cargoCache", key = "#idCargo")
    public Cargo mostrarCargoPeloIdService(Integer idCargo) {
      Optional<Cargo> cargo = cargoRepository.findById(idCargo);
      return cargo.orElseThrow();
    }

    @CachePut(value = "cargoCache", key = "#cargo.idCargo")
    public Cargo cadastrarCargoService(Cargo cargo) {
        cargo.setIdCargo(null);
        return cargoRepository.save(cargo);
    }

    @CacheEvict(value = "cargoCache", key = "#idCargo", allEntries = true)
    public void excluirCargoService(Integer idCargo) {
        cargoRepository.deleteById(idCargo);
    }

    @CachePut(value = "cargoCache", key = "#cargo.idCargo")
    public Cargo editarCargoService(Cargo cargo) {
       return cargoRepository.save(cargo);
    }




}
