package com.thebuyback.eve.web.rest.stock;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import com.thebuyback.eve.domain.stock.Doctrine;
import com.thebuyback.eve.domain.stock.Hub;
import com.thebuyback.eve.domain.stock.StockDoctrine;
import com.thebuyback.eve.repository.DoctrineRepository;
import com.thebuyback.eve.repository.HubRepository;
import com.thebuyback.eve.repository.StockDoctrineRepository;
import com.thebuyback.eve.security.AuthoritiesConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock/doctrines")
public class DoctrineResource {

    private final Logger log = LoggerFactory.getLogger(DoctrineResource.class);

    private final DoctrineRepository repository;
    private final StockDoctrineRepository stockDoctrineRepository;
    private final HubRepository hubRepository;

    public DoctrineResource(final DoctrineRepository repository,
                            final StockDoctrineRepository stockDoctrineRepository,
                            final HubRepository hubRepository) {
        this.repository = repository;
        this.stockDoctrineRepository = stockDoctrineRepository;
        this.hubRepository = hubRepository;
    }

    @PostMapping
    @Secured(AuthoritiesConstants.STOCK_MANAGER)
    public void postFitting(@RequestBody Doctrine doctrine) {
        repository.save(doctrine);
        log.info("Doctrine {} created.", doctrine.getName());
    }

    @GetMapping
    @Secured(AuthoritiesConstants.STOCK_MANAGER)
    public ResponseEntity<List<Doctrine>> getDoctrines() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{hubId}")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<StockDoctrine>> getStock(@PathVariable("hubId") Long hubId) {
        final Optional<Hub> hub = hubRepository.findById(hubId);
        if (hub.isPresent()) {
            return ResponseEntity.ok(stockDoctrineRepository.findByHub(hub.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
