package domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "tutor")
public class Tutor extends BaseEntity<Long> {

    @Column(name = "tutor name")
    private String tutorname;

    @NotNull
    @Email(message = "must use a valid mail")
    @Column(name = "tutor_mail")
    private String email;

    @NotNull
    @Column(name = "tutor_password")
    private String password;
}
