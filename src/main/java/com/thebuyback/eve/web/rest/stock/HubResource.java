package com.thebuyback.eve.web.rest.stock;

import java.util.List;

import com.thebuyback.eve.domain.stock.Hub;
import com.thebuyback.eve.repository.HubRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock/hubs")
public class HubResource {

    private final Logger log = LoggerFactory.getLogger(HubResource.class);

    private final HubRepository repository;

    public HubResource(final HubRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Hub>> getHubs() {
        return ResponseEntity.ok(repository.findAll());
    }
}
