import {Component, OnInit} from '@angular/core';
import {Http} from "@angular/http";
import {CapConfig} from "../entities/cap-config/cap-config.model";
import {CapOrder} from "../entities/cap-order/cap-order.model";
import {MarketOffer} from "../entities/market-offer/market-offer.model";
import {Principal} from "../shared/auth/principal.service";

@Component({
    selector: 'jhi-marketplace',
    templateUrl: './marketplace.component.html'
})
export class MarketPlaceComponent implements OnInit {

    loadBuyFailed: boolean;
    loadSellFailed: boolean;
    buyOffers: MarketOffer[];
    sellOffers: MarketOffer[];

    constructor(private http: Http, private principal: Principal) {

    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    ngOnInit(): void {
        this.http.get("/api/market-offers/active/SELL").subscribe(
            (data) => this.sellOffers = data.json(),
            (err) => this.loadSellFailed = true
        )
        this.http.get("/api/market-offers/active/BUY").subscribe(
            (data) => this.buyOffers = data.json(),
            (err) => this.loadBuyFailed = true
        )
    }

}
