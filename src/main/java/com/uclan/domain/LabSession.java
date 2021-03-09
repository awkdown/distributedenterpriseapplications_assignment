package com.uclan.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "labSession")
public class LabSession extends BaseEntity<Long> {

    @NotEmpty(message = "must be unqiue and cannot be empty")
    @Column(name = "lab_name", unique = true)
    private String name;

    @NotEmpty(message = "cannot be empty")
    @Column (name = "lab_description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Module module;

    @NotNull(message = "cannot be empty")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tutor_email", unique = true)
    private Tutor sessionLeader;
}