package com.vladooha.service;

import com.vladooha.data.dto.CarDTO;
import com.vladooha.data.entity.Car;
import com.vladooha.data.entity.Person;
import com.vladooha.data.repo.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    private CarRepo carRepo;
    @Autowired
    private PersonService personService;

    public Car fromDTO(CarDTO carDTO) {
        Car result = new Car();

        result.setCustomId(carDTO.getId());
        String[] modelNameParts = carDTO.getModel().split("-", 2);
        result.setVendor(modelNameParts[0]);
        result.setModel(modelNameParts[1]);
        result.setHorsepower(carDTO.getHorsepower());
        long ownerId = carDTO.getOwnerId();
        Person owner = personService.findByCustomId(ownerId);
        result.setOwner(owner);

        return result;
    }

    public CarDTO toDTO(Car car) {
        CarDTO result = new CarDTO();

        result.setId(car.getCustomId());
        result.setModel(car.getVendor() + "-" + car.getModel());
        result.setHorsepower(car.getHorsepower());
        result.setOwnerId(car.getOwner().getCustomId());

        return result;
    }

    public Car findByCustomId(long id) {
        return carRepo.findByCustomId(id);
    }

    public boolean saveDTO(CarDTO carDTO) {
        Car car = fromDTO(carDTO);
        Car savedCar = carRepo.save(car);

        return savedCar != null;
    }

    public long count() {
        return carRepo.count();
    }

    public long vendorCount() {
        return carRepo.countByVendor();
    }

    public void deleteAll() {
        carRepo.deleteAll();
    }
}
