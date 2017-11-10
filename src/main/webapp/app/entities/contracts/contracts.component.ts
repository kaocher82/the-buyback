import {Component, OnInit} from '@angular/core';

import {Contracts} from './contracts.model';
import {ContractsService} from './contracts.service';
import {Http} from "@angular/http";

@Component({
    selector: 'jhi-contracts',
    templateUrl: './contracts.component.html'
})
export class ContractsComponent implements OnInit {

    contracts: Contracts[];
    declineSuccess: boolean;
    errorMessage: string = null;

    constructor(private contractsService: ContractsService, private http: Http) { }

    ngOnInit(): void {
        this.contractsService.loadBuybackContracts().subscribe(
            (data) => this.contracts = data
        )
    }

    sendDeclineMail(contractId: number) {
        this.declineSuccess = false;
        this.errorMessage = null;

        this.contracts.forEach(contract => {
            if (contract.id === contractId) {
                contract.declineMailSent = true;
            }
        });

        this.http.post('/api/contracts/buyback/' + contractId + '/decline/', null).subscribe(
            (data) => this.declineSuccess = true,
            (err) => {
                if (err.status === 409) {
                    this.errorMessage = "The decline mail was already sent.";
                } else if (err.status === 500) {
                    this.errorMessage = "Failed to get an access token. Tell the administrator.";
                } else if (err.status === 400) {
                    this.errorMessage = "The mail could not be send. Maybe the issuer was a corporation?";
                } else if (err.status === 404) {
                    this.errorMessage = "The contract could not be found. Sad!";
                }
            }
        );
    }

}
