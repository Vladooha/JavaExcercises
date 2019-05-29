package com.vladooha.controller;

import com.vladooha.data.dto.CarDTO;
import com.vladooha.data.dto.PersonDTO;
import com.vladooha.data.dto.PersonStatisticDTO;
import com.vladooha.data.dto.StatisticDTO;
import com.vladooha.data.entity.Car;
import com.vladooha.data.entity.Person;
import com.vladooha.data.validation.annotation.AdultPerson;
import com.vladooha.data.validation.annotation.HasCars;
import com.vladooha.data.validation.grouping.ValidationSequence;
import com.vladooha.service.CarService;
import com.vladooha.service.PersonService;
import com.vladooha.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Set;

@Controller
@Validated
public class MainController {
    @Autowired
    private PersonService personService;
    @Autowired
    private CarService carService;
    @Autowired
    private StatisticService statisticService;

    @PostMapping("/person")
    public ResponseEntity addPerson(
            @RequestBody @Validated(ValidationSequence.class) PersonDTO personDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.err.println(error.getDefaultMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        personService.saveDTO(personDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/car")
    public ResponseEntity addCar(
            @RequestBody @Validated(ValidationSequence.class) CarDTO carDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        carService.saveDTO(carDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/personwithcars")
    @ResponseBody
    public ResponseEntity getPersonWithCars(
            @RequestParam("personid") @Min(0L) Long personId) {
        Person person = personService.findByCustomId(personId);
        if (person == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Set<Car> personCars = person.getCars();
        if (personCars == null || personCars.size() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        PersonStatisticDTO personStat = statisticService.getPersonStats(person);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(personStat);
    }

    @GetMapping("/statistics")
    @ResponseBody
    public ResponseEntity getPersonWithCars() {
        StatisticDTO statisticDTO = statisticService.getStats();
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(statisticDTO);
    }

    @GetMapping("/clear")
    public ResponseEntity clearAll() {
        carService.deleteAll();
        personService.deleteAll();

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
