import {Component, OnInit} from '@angular/core';

import {Contracts} from './contracts.model';
import {ContractsService} from './contracts.service';

@Component({
    selector: 'jhi-contracts',
    templateUrl: './contracts.component.html'
})
export class ContractsComponent implements OnInit {

    contracts: Contracts[];

    constructor(private contractsService: ContractsService) { }

    ngOnInit(): void {
        this.contractsService.loadBuybackContracts().subscribe(
            (data) => this.contracts = data
        )
    }

}
