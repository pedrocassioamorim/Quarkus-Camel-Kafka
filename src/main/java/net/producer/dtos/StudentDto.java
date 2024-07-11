package net.producer.dtos;

import lombok.Value;
import net.producer.domain.Student;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Student}
 */
@Value
public class StudentDto implements Serializable {
    Long id;
    String nome;
    LocalDate dataNascimento;
    String cpf;
}