/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.am.preguntas_backend.resources;

import com.am.preguntas_backend.logic.Meeting;
import com.am.preguntas_backend.logic.Service;
import com.am.preguntas_backend.logic.User;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author ESCINF
 */
@Path("/users")
@PermitAll
public class Users {

    @Context
    HttpServletRequest request;

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public User showClient(User candidate) {
        try {
            System.out.println("Entro");
            User user = Service.instance().user_read(candidate.getEmail());
            if (candidate.getPassword().equals(user.getPassword())) {
                request.getSession(true).setAttribute("user", user);
                return user;
            }
        } catch (Exception ex) {
            //Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException();
        }
        return null;
    }

    @POST
    @Path("/newMeeting")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void newMeeting(Meeting candidate) {
        try {
            System.out.println("Entro");
            HttpSession session = request.getSession(false);
            if (session != null) {
                User user = (User) session.getAttribute("user");
                candidate.setOwner(user);
                Service.instance().meetings_add(candidate);
            }
        } catch (Exception ex) {
            //Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException();
        }
    }

    @GET
    @Path("{email}")
    @Produces({MediaType.APPLICATION_JSON})
    public User showClientId(@PathParam("email") String email) {
        try {
            System.out.println("Entro");
            User user = Service.instance().user_read(email);
            return user;

        } catch (Exception ex) {
            //Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("/logout")
    public Response logout() {
        // Invalidar la sesión del usuario
        request.getSession().invalidate();
        // Opcionalmente, puedes realizar otras acciones de limpieza o respuesta
        // Por ejemplo, puedes devolver una respuesta exitosa o un mensaje de éxito
        return Response.ok().build();
    }

    @GET
    @Path("/meetings")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Meeting> getMetings() {
        try {
            System.out.println("Entro");
            HttpSession session = request.getSession(false);
            if (session != null) {
                User user = (User) session.getAttribute("user");
                return Service.instance().meetings_by_user(user);
            }

        } catch (Exception ex) {
            //Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            throw new NotFoundException();
        }
        return null;
    }

}
