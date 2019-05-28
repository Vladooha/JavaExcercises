package com.vladooha.service;

import com.vladooha.data.dto.CarDTO;
import com.vladooha.data.dto.PersonStatisticDTO;
import com.vladooha.data.dto.StatisticDTO;
import com.vladooha.data.entity.Car;
import com.vladooha.data.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StatisticService {
    @Autowired
    private CarService carService;
    @Autowired
    private PersonService personService;

    public PersonStatisticDTO getPersonStats(Person person) {
        PersonStatisticDTO result = new PersonStatisticDTO();

        result.setId(person.getCustomId());
        result.setName(person.getName());
        result.setBirthday(person.getBirthday());
        Set<CarDTO> carDTOList = new HashSet<>();
        for (Car car : person.getCars()) {
            carDTOList.add(carService.toDTO(car));
        }
        result.setCars(carDTOList);

        return result;
    }

    public StatisticDTO getStats() {
        StatisticDTO result = new StatisticDTO();

        result.setPersoncount(personService.count());
        result.setCarcount(carService.count());
        result.setUniquevendorcount(carService.vendorCount());

        return result;
    }
}
