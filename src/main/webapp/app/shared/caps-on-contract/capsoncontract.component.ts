import {Component, OnInit} from '@angular/core';
import {Http} from "@angular/http";

@Component({
    selector: 'jhi-caps-on-contract',
    templateUrl: './capsoncontract.component.html'
})
export class CapsOnContractComponent implements OnInit {

    capsOnContract: any[]

    constructor(
        private http: Http
    ) {
    }

    ngOnInit(): void {
        this.http.get('/api/contracts/caps').subscribe(
            (data) => this.capsOnContract = data.json()
        );
    }
}
