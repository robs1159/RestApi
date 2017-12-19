package ca.ulaval.glo4002.billing.interfaces.rest;

import ca.ulaval.glo4002.billing.application.dto.HeartbeatDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("/heartbeat")
public class HeartbeatResource {
    public static final String TOKEN_PARAMETER = "token";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public HeartbeatDto get(@QueryParam(TOKEN_PARAMETER) String token) {
        HeartbeatDto heartbeatDto = new HeartbeatDto();
        heartbeatDto.date = new Date().getTime();
        heartbeatDto.token = token;

        return heartbeatDto;
    }
}
