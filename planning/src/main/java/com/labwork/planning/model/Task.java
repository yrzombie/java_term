package com.labwork.planning.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;               // Наименование
    private String code;               // Код
    private String parentCode;         // Код родителя
    private int duration;              // Длительность (время выполнения)
    private double operationTime;      // Время операции
    private double setupTime;          // Подготовительное время
    private int batchSize;             // Размер партии
    private String type;               // Тип 
    private String workshop;           // Цех

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;
}
