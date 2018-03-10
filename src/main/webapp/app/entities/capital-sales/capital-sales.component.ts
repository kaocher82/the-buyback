import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
    selector: 'jhi-capital-ales',
    templateUrl: './capital-sales.component.html'
})
export class CapitalSalesComponent {

    capsSold: any;
    newCapSaleIds: number[] = [];

    constructor(private http: HttpClient) {
        this.http.get<any[]>('/api/contracts/caps-sold').subscribe(data => {
            const cookie = localStorage.getItem('cap-sale-ids');
            const oldSales: number[] = [];
            if (cookie) {
                cookie.split(',').forEach((id) => oldSales.push(+id));
            }
            data.forEach((sale) => {
                if (oldSales.indexOf(sale.id) === -1) {
                    this.newCapSaleIds.push(sale.id);
                    oldSales.push(sale.id);
                    localStorage.setItem('cap-sale-ids', oldSales.join());
                }
            });
            this.capsSold = data;
        });
    }

    getNewSaleClass(id: number) {
        if (this.newCapSaleIds.indexOf(id) !== -1) {
            return "new-sale";
        } else {
            return "";
        }
    }

}
