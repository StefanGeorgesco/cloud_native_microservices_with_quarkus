package fr.stefangeorgesco.smartbar.backoffice;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/foo")
public class FooResource {

    public final String myText;

    public FooResource(@ConfigProperty(name = "smartbar.backoffice.my-text", defaultValue = "Bar") String myText) {
        this.myText = myText;
    }

    @GET
    public Response foo() {
        return Response.ok(myText).build();
    }
}
