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

    @NotEmpty(message = "Please enter a password")
    @Column(name = "tutor_name")
    private String tutorname;

    @NotEmpty(message = "Please enter an email")
    @Email(message = "must use a valid mail")
    @Column(name = "tutor_mail", unique = true)
    private String email;

    @NotEmpty (message = "Enter Password")
    @Column(name = "tutor_password")
    private String password;
}