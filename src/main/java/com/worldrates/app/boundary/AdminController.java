package com.worldrates.app.boundary;

import com.worldrates.app.entity.RateSyncStatus;
import com.worldrates.jobs.boundary.RatesSyncJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@ApplicationScoped
@Path("admin")
public class AdminController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    private final RatesSyncJob ratesSyncJob;

    @Inject
    public AdminController(RatesSyncJob ratesSyncJob) {
        this.ratesSyncJob = ratesSyncJob;
    }

    @POST
    @Path("/jobs/dailyRatesSync")
    public Response dailyRatesSync() {
        LocalDateTime startTime = LocalDateTime.now();
        try {
            ratesSyncJob.syncRates();
            return Response
                    .ok(new RateSyncStatus(startTime, LocalDateTime.now(), "OK", null, null, null))
                    .build();
        } catch (Exception e) {
            LOG.warn("Failed", e);
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new RateSyncStatus(startTime, LocalDateTime.now(), "ERROR", null, null, e.getMessage()))
                    .build();
        }
    }
}
