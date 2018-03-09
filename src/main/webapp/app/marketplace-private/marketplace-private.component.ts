import {Component, OnInit} from '@angular/core';
import {Http} from "@angular/http";
import {MarketOffer, MarketOfferType} from "../entities/market-offer/market-offer.model";

@Component({
    selector: 'jhi-marketplace-private',
    templateUrl: './marketplace-private.component.html'
})
export class MarketplacePrivateComponent implements OnInit {

    loadBuyFailed: boolean;
    loadSellFailed: boolean;
    buyList: MarketOffer[];
    sellList: MarketOffer[];

    constructor(private http: Http) {

    }

    addNew(isSell: boolean) {
        const marketOffer = new MarketOffer();
        if (isSell) {
            marketOffer.type = MarketOfferType.SELL;
            this.sellList.unshift(marketOffer);
        } else {
            marketOffer.type = MarketOfferType.BUY;
            this.buyList.unshift(marketOffer);
        }
    }

    ngOnInit(): void {
        this.http.get("/api/market-offers/private/SELL").subscribe(
            (data) => this.sellList = data.json(),
            (err) => this.loadSellFailed = true
        );
        this.http.get("/api/market-offers/private/BUY").subscribe(
            (data) => this.buyList = data.json(),
            (err) => this.loadBuyFailed = true
        );
    }

}
