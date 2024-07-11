package net.producer.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import net.producer.dtos.StudentDto;

import net.producer.services.StudentService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.hibernate.metamodel.mapping.ordering.ast.OrderByComplianceViolation;


import java.util.List;

@Path("/students")
public class StudentResource {


    @Inject
    StudentService studentService;


    @GET @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll(@QueryParam("pagina") int pagina, @QueryParam("tamanho") int tamanho) {
        List<StudentDto> studentDtos = studentService.listAll(pagina, tamanho);
        return Response.status(Response.Status.OK).entity(studentDtos).build();
   }


   @POST @Transactional
   @Produces(MediaType.APPLICATION_JSON)
   public Response create(@RequestBody StudentDto dto){
        StudentDto studentDto = studentService.create(dto);
        return Response.status(Response.Status.CREATED).entity(studentDto).build();
   }


   @GET @Path("/id/{id}") @Transactional
   @Produces(MediaType.APPLICATION_JSON)
   public Response findById(@PathParam("id") Long id){
        try{
            StudentDto studentDto = studentService.findById(id);
            return Response.status(Response.Status.OK).entity(studentDto).build();
        }catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
   }


    @GET @Path("/nome/{nome}") @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByName(@PathParam("nome") String nome){
        try{
            List<StudentDto> studentDtos = studentService.findByName(nome);
            return Response.status(Response.Status.OK).entity(studentDtos).build();
        }catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }


    @GET @Path("/cpf/{cpf}") @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByCpf(@PathParam("cpf") String cpf){
        try{
            StudentDto studentDto = studentService.findByCpf(cpf);
            return Response.status(Response.Status.OK).entity(studentDto).build();
        }catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }


    @PUT @Path("/id/{id}") @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, @RequestBody StudentDto dto){
        try{
            StudentDto studentDto = studentService.update(id, dto);
            return Response.status(Response.Status.OK).entity(studentDto).build();
        }catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }


    @DELETE @Path("/id/{id}") @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id){
        try{
                studentService.delete(id);
                return Response.status(Response.Status.NO_CONTENT).build();
        }catch (NullPointerException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }catch (OrderByComplianceViolation f){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

}
