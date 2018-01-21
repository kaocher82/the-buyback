import {Component, OnInit} from '@angular/core';

import {Contracts} from './contracts.model';
import {ContractsService} from './contracts.service';
import {Http} from "@angular/http";
import {ClipboardService} from "../../shared/appraisal/clipboard.service";

@Component({
    selector: 'jhi-contracts',
    templateUrl: './contracts.component.html'
})
export class ContractsComponent implements OnInit {

    contracts: Contracts[];
    declineSuccess: boolean;
    errorMessage: string = null;
    showCopiedPriceFor: string = null;

    constructor(private contractsService: ContractsService, private http: Http, private clipboard: ClipboardService) { }

    ngOnInit(): void {
        this.loadContracts();
    }

    private loadContracts() {
        this.contractsService.loadBuybackContracts().subscribe((data) => this.contracts = data)
    }

    copyPrice(price: number, id: string) {
        this.clipboard.copy(price + '');

        this.showCopiedPriceFor = id;
        setTimeout(function() {
            this.showCopiedPriceFor = null;
        }.bind(this), 4000);
    }

    approveContract(contractId: number) {
        this.http.post('/api/contracts/buyback/' + contractId + '/approve/', null).subscribe(
            (date) => this.loadContracts(),
            (err) => {
                if (err.status === 404) {
                    this.errorMessage = "The contract could not be found. Sad!";
                }
            }
        );

    }

    sendDeclineMail(contractId: number) {
        this.declineSuccess = false;
        this.errorMessage = null;

        this.contracts.forEach((contract) => {
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
