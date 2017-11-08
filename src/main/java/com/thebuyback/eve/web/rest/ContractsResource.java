package com.thebuyback.eve.web.rest;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.thebuyback.eve.domain.Contract;
import com.thebuyback.eve.repository.ContractRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ContractsResource
 *
 * Created on 08.11.2017
 *
 * Copyright (C) 2017 Volkswagen AG, All rights reserved.
 */
@RestController
@RequestMapping("/api/contracts")
@PreAuthorize("hasRole('ROLE_MANAGER')")
public class ContractsResource {

    private static final long THE_BUYBACK = 98503372L;
    private final ContractRepository contractRepository;

    public ContractsResource(final ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @GetMapping("/buyback/pending")
    public ResponseEntity<List<ContractDTO>> getPendingBuybacks() {
        List<ContractDTO> contracts = contractRepository.findAllByStatusAndAssigneeId("outstanding", THE_BUYBACK)
                                                          .stream().map(mapToDTO()).collect(Collectors.toList());
        return ResponseEntity.ok(contracts);
    }

    private static Function<Contract, ContractDTO> mapToDTO() {
        return contract -> new ContractDTO(contract.getContractId(),
                                           contract.getClient(),
                                           contract.getDateIssued(),
                                           contract.getPrice(),
                                           contract.getBuyValue());
    }
}
