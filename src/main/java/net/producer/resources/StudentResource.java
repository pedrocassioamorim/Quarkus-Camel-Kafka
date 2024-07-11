package net.producer.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.bytebuddy.description.type.TypeVariableToken;
import net.producer.domain.Student;
import net.producer.dtos.StudentDto;
import net.producer.repositories.StudentRepository;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;

import java.lang.reflect.Type;
import java.util.List;

@Path("/students")
public class StudentResource {

    @Inject
    StudentRepository studentRepository;

    @GET @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll(@QueryParam("pagina") int pagina, @QueryParam("tamanho") int tamanho) {
        ModelMapper modelMapper = new ModelMapper();
        List<Student> students = studentRepository.findAll().page(pagina, tamanho).list();
        List<StudentDto> studentDtos = students.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();
        return Response.status(Response.Status.OK).entity(studentDtos).build();
   }

   @POST @Transactional
   @Produces(MediaType.APPLICATION_JSON)
   public Response create(@RequestBody StudentDto studentDto){
        ModelMapper modelMapper = new ModelMapper();
        Student student = modelMapper.map(studentDto, Student.class);
        studentRepository.persist(student);
        return Response.status(Response.Status.CREATED).entity(studentDto).build();
   }

   @GET @Path("/{id}") @Transactional
   @Produces(MediaType.APPLICATION_JSON)
   public Response findById(@PathParam("id") Long id){
        ModelMapper modelMapper = new ModelMapper();
        Student student = studentRepository.findById(id);
        if (student == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        StudentDto dto = modelMapper.map(student, StudentDto.class);
        return Response.status(Response.Status.OK).entity(dto).build();
   }

    @GET @Path("/{nome}") @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByName(@PathParam("nome") String nome){
        ModelMapper modelMapper = new ModelMapper();
        List<Student> students = studentRepository.find("nome", nome).stream().toList();
        List<StudentDto> studentDtos = students.stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
                .toList();
        return Response.status(Response.Status.OK).entity(studentDtos).build();
    }


}
