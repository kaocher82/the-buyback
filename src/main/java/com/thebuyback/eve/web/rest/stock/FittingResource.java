package com.thebuyback.eve.web.rest.stock;

import java.util.List;

import javax.websocket.server.PathParam;

import com.thebuyback.eve.domain.stock.Fitting;
import com.thebuyback.eve.repository.FittingRepository;
import com.thebuyback.eve.security.AuthoritiesConstants;
import com.thebuyback.eve.service.FittingParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock/fittings")
public class FittingResource {

    private final Logger log = LoggerFactory.getLogger(FittingResource.class);

    private final FittingRepository repository;
    private final FittingParser parser;

    public FittingResource(final FittingRepository repository, final FittingParser parser) {
        this.repository = repository;
        this.parser = parser;
    }

    @PutMapping
    @Secured(AuthoritiesConstants.STOCK_MANAGER)
    public ResponseEntity putFitting(@RequestBody Fitting fitting) {
        if (fitting.getId() == null) {
            return ResponseEntity.status(400).body("To update it, the fitting must have its database ID set.");
        }
        repository.save(fitting);
        log.info("Fitting {} updated.", fitting.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Secured(AuthoritiesConstants.STOCK_MANAGER)
    public ResponseEntity postFitting(@RequestBody String fittingText) {
        final Fitting fitting = parser.parse(fittingText);
        if (repository.findOne(fitting.getId()) != null) {
            return ResponseEntity.status(409).build();
        }
        repository.save(fitting);
        log.info("Fitting {} created.", fitting.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Secured(AuthoritiesConstants.STOCK_MANAGER)
    public ResponseEntity<List<Fitting>> getFittings() {
        return ResponseEntity.ok(repository.findAll());
    }
}
