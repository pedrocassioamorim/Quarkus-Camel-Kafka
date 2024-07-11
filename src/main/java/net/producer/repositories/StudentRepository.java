package net.producer.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import net.producer.domain.Student;

@ApplicationScoped
public class StudentRepository  implements PanacheRepository<Student>{}
