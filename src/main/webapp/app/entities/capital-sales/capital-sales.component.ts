import {Component} from '@angular/core';

import {Http} from "@angular/http";

@Component({
    selector: 'jhi-capital-ales',
    templateUrl: './capital-sales.component.html'
})
export class CapitalSalesComponent {

    capsSold: any;
    newCapSaleIds: number[] = [];

    constructor(private http: Http) {
        this.http.get('/api/contracts/caps-sold').subscribe((data) => {
            const cookie = localStorage.getItem('cap-sale-ids');
            const oldSales: number[] = [];
            if (cookie) {
                cookie.split(',').forEach((id) => oldSales.push(+id));
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

    getNewSaleClass(id: number) {
        if (this.newCapSaleIds.indexOf(id) !== -1) {
            return "new-sale";
        } else {
            console.log(id, " exist in ", this.newCapSaleIds);
            return "";
        }
    }

}
