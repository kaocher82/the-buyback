import {AfterViewInit, Component, OnInit} from '@angular/core';

import {Http} from "@angular/http";

@Component({
    selector: 'jhi-capital-ales',
    templateUrl: './capital-sales.component.html'
})
export class CapitalSalesComponent implements OnInit {

    capsSold: any;
    newCapSaleIds: number[] = [];

    constructor(private http: Http) {
        this.http.get('/api/contracts/caps-sold').subscribe((data) => {
            const cookie = localStorage.getItem('cap-sale-ids');
            let oldSales: number[] = [];
            if (cookie) {
                cookie.split(',').forEach(el => oldSales.push(+el));
            }
            data.json().forEach((sale) => {
                if (oldSales.indexOf(sale.id) === -1) {
                    this.newCapSaleIds.push(sale.id);
                    oldSales.push(sale.id);
                    localStorage.setItem('cap-sale-ids', oldSales.join());
                }
            });
            this.capsSold = data.json();
        });
    }

    ngOnInit(): void {

    }

    getNewSaleClass(id: number) {
        if (this.newCapSaleIds.indexOf(id) !== -1) {
            return "new-sale";
        } else {
            console.log(id, " exist in ", this.newCapSaleIds);
            return "";
        }
    }

}
