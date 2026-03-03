package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;
    private final IdGenerator idGenerator;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, IdGenerator idGenerator) {
        this.carRepository = carRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public Car create(Car car) {
        if(car.getCarId() == null || car.getCarId().isEmpty()){
            car.setCarId(idGenerator.generateId());
        }
        return carRepository.save(car);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Car findById(String carId) {
        return carRepository.findById(carId).orElse(null);
    }

    @Override
    public Car update(Car updatedCar){
        return carRepository.update(updatedCar);
    }

    @Override
    public void delete(String carId) {
        carRepository.delete(carId);
    }
}
