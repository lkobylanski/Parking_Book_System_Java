package com.pbs.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbs.web.jdbc.ParkingBookSystem.Book;
import com.pbs.web.jdbc.ParkingBookSystem.BookDbUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("books")
@Produces(MediaType.APPLICATION_JSON)
public class BookingRestController {
    private BookDbUtil bookDbUtil = new BookDbUtil();

    @GET
    @Path("/")
    public Response getAllBooks() throws Exception {
        List<Book> books = bookDbUtil.getBooks();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(books);
        return Response.ok().entity(json).build();
    }

    @GET
    @Path("/{id}")
    public Response getSpecBook(@PathParam("id") int id) throws Exception {
    	List<Book> books = bookDbUtil.getSpecBook(id);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(books);
        return Response.ok().entity(json).build();
    }

    /*
    * GET books - cala lista rezerwacji
    * GET books/{id} - jedna rezerwacje o Id = {id}
    * POST books - zapisac rezerwacje w bazie danych (parametr to JSON z cala rezerwacja)
    * PUT books/{id} - update rezerwacji o danym Id = {id} (parametr to JSON z cala rezerwacja)
    * DELETE books/{id} - usuwa rezerwacje
    */
}
