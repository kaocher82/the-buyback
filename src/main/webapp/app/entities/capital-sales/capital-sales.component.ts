import {Component, OnInit} from '@angular/core';

import {Http} from "@angular/http";

@Component({
    selector: 'jhi-capital-ales',
    templateUrl: './capital-sales.component.html'
})
export class CapitalSalesComponent implements OnInit {

    capsSold: any;

    constructor(private http: Http) { }

    ngOnInit(): void {
        this.http.get('/api/contracts/caps-sold').subscribe((data) => {
            this.capsSold = data.json();
        });
    }


}
