package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends Repository<Car, String> {

    Car save(Car car);
    Optional<Car> findById(String id);
    List<Car> findAll();
    Car update(Car car);
    void delete(String id);
}
