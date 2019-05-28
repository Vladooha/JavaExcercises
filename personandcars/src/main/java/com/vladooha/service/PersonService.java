package com.vladooha.service;

import com.vladooha.data.dto.CarDTO;
import com.vladooha.data.dto.PersonDTO;
import com.vladooha.data.dto.PersonStatisticDTO;
import com.vladooha.data.entity.Car;
import com.vladooha.data.entity.Person;
import com.vladooha.data.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PersonService {
    @Autowired
    private PersonRepo personRepo;

    public Person fromDTO(PersonDTO personDTO) {
        Person result = new Person();

        result.setCustomId(personDTO.getId());
        result.setName(personDTO.getName());
        result.setBirthday(personDTO.getBirthday());

        return result;
    }

    public PersonDTO toDTO(Person person) {
        PersonDTO result = new PersonDTO();

        result.setId(person.getCustomId());
        result.setName(person.getName());
        result.setBirthday(person.getBirthday());

        return result;
    }

    public boolean saveDTO(PersonDTO personDTO) {
        Person person = fromDTO(personDTO);
        Person savedPerson = personRepo.save(person);

        return savedPerson != null;
    }

    public Person findByCustomId(long id) {
        return personRepo.findByCustomId(id);
    }

    public long count() {
        return personRepo.count();
    }

    public void deleteAll() {
        personRepo.deleteAll();
    }
}
