package com.uclan.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "module")
public class Module extends BaseEntity<Long> {

    @NotEmpty(message = "cannot be empty")
    @Column(name = "module_name", unique = true)
    private String name;

    @NotNull(message = "cannot be empty")
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tutor_email", unique = true)
    private Tutor moduleLeader;

    @NotEmpty(message = "cannot be empty")
    @Column (name = "module_description")
    private String description;
}