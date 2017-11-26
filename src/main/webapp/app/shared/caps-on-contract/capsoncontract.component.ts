import {Component, OnInit} from '@angular/core';
import {Http} from "@angular/http";
import {Principal} from "../auth/principal.service";

@Component({
    selector: 'jhi-caps-on-contract',
    templateUrl: './capsoncontract.component.html'
})
export class CapsOnContractComponent implements OnInit {

    capsOnContract: any[]
    account: any;

    constructor(
        private http: Http,
        private principal: Principal
    ) {
    }

    ngOnInit(): void {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.http.get('/api/contracts/caps').subscribe(
            (data) => this.capsOnContract = data.json()
        );
    }
}
