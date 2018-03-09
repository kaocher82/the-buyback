import {Component, OnInit} from '@angular/core';
import {Http} from "@angular/http";
import {CapConfig} from "../entities/cap-config/cap-config.model";
import {CapOrder} from "../entities/cap-order/cap-order.model";

@Component({
    selector: 'jhi-order-caps',
    templateUrl: './order-caps.component.html'
})
export class OrderCapsComponent implements OnInit {
    initFailed: boolean;
    capConfigs: CapConfig[];

    pickedHull: CapConfig;

    deliveryLocation: string;
    deliveryPrice = 0;

    orderComplete: boolean;
    orderFailed: boolean;

    constructor(private http: Http) {

    }

    ngOnInit(): void {
        this.http.get("/api/cap-configs").subscribe(
            (data) => this.capConfigs = data.json(),
            (err) => this.initFailed = true
        );
    }

    selectConf(name: string) {
        let result = null;
        this.capConfigs.forEach((value) => {
            if (value.typeName === name) {
                result = value;
            }
        });
        return result;
    }

    handleCapSelected(cap: CapConfig) {
        this.pickedHull = cap;
    }

    selectLocation(location: string, price: number) {
        this.deliveryLocation = location;
        this.deliveryPrice = price;
    }

    buy() {
        const order = new CapOrder(
            null,
            null,
            this.pickedHull.typeId,
            this.pickedHull.typeName,
            this.pickedHull.price,
            null,
            this.deliveryLocation,
            this.deliveryPrice,
            null
        );
        this.http.post("/api/cap-orders", order).subscribe(
            (data) => this.orderComplete = true,
            (err) => this.orderFailed = true
        );
    }
}
