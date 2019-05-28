package com.vladooha.data.entity;

import com.vladooha.data.dto.CarDTO;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        name = "car",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"customId"})})
@Data
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(1)
    private Long customId;

    @NotNull
    private String vendor;

    @NotNull
    private String model;

    @NotNull
    @Min(value = 1)
    private Integer horsepower;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Person owner;
}
