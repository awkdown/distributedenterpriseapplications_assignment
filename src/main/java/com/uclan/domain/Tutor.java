package com.uclan.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "tutor")
public class Tutor extends BaseEntity<Long> {

    @Column(name = "tutor_name")
    private String tutorname;

    @NotEmpty
    @Email(message = "must use a valid mail")
    @Column(name = "tutor_mail", unique = true)
    private String email;

    @NotNull
    @Column(name = "tutor_password")
    private String password;
}
