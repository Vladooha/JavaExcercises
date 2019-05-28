package com.vladooha.data.repo;

import com.vladooha.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person, Long> {
    Person findByCustomId(long customId);
}
