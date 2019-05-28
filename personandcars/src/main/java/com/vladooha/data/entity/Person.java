package com.vladooha.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(
        name = "person",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"customId"})})
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(1)
    private Long customId;

    @NotNull
    private String name;

    @NotNull
    private Date birthday;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Car> cars;
}
