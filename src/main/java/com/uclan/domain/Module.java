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
@Table(name = "module")
public class Module extends BaseEntity<Long> {

    @NotEmpty(message = "must be unqiue and cannot be empty")
    @Column(name = "module_name", unique = true)
    private String name;

    @NotNull(message = "cannot be empty")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tutor_email")
    private Tutor moduleLeader;

    @Column (name = "module_description")
    private String description;
}
