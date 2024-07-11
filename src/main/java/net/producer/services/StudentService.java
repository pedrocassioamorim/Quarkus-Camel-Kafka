package net.producer.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import net.producer.domain.Student;
import net.producer.dtos.StudentDto;
import net.producer.repositories.StudentRepository;
import org.modelmapper.ModelMapper;

import java.util.List;

@ApplicationScoped
public class StudentService {

    public static final String STUDENT_NOT_FOUND = "Student not found!";

    @Inject
    StudentRepository studentRepository;

    public List<StudentDto> listAll(int pagina, int tamanho){
        ModelMapper modelMapper = new ModelMapper();
        List<Student> students = studentRepository.findAll().page(pagina, tamanho).list();
        List<StudentDto> studentDtos = students.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();
        return studentDtos;
    }

    public StudentDto create(StudentDto dto){
        ModelMapper modelMapper = new ModelMapper();
        Student student = modelMapper.map(dto, Student.class);
        studentRepository.persist(student);
        return dto;
    }

    public StudentDto findById(long id){
        ModelMapper modelMapper = new ModelMapper();
        Student student = studentRepository.findById(id);
        if (student == null){
            throw new NullPointerException(STUDENT_NOT_FOUND);
        }
        StudentDto dto = modelMapper.map(student, StudentDto.class);
        return dto;
    }

    public List<StudentDto> findByName(String nome){
        ModelMapper modelMapper = new ModelMapper();
        List<Student> students = studentRepository.find("nome", nome).stream().toList();
        if (students.isEmpty()){
            throw new NullPointerException(STUDENT_NOT_FOUND);
        }
        List<StudentDto> studentDtos = students.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();
        return studentDtos;
    }

    public StudentDto findByCpf(String cpf){
        ModelMapper modelMapper = new ModelMapper();
        Student student = studentRepository.find("cpf", cpf).firstResult();
        if (student == null){
            throw new NullPointerException(STUDENT_NOT_FOUND);
        }
        StudentDto dto = modelMapper.map(student, StudentDto.class);
        return dto;
    }

    public StudentDto update(long id, StudentDto studentDto){
        ModelMapper modelMapper = new ModelMapper();
        Student student = studentRepository.findById(id);
        if (student == null){
            throw new NullPointerException(STUDENT_NOT_FOUND);
        }
        student.setNome(studentDto.getNome());
        student.setCpf(studentDto.getCpf());
        student.setDataNascimento(studentDto.getDataNascimento());
        studentRepository.persist(student);
        return modelMapper.map(student, StudentDto.class);
    }

    public void delete(long id){
        Student student = studentRepository.findById(id);
        if (student == null){
            throw new NullPointerException(STUDENT_NOT_FOUND);
        }
        studentRepository.delete(student);
    }



}
