import {Component, Input, OnInit} from '@angular/core';
import {MarketOffer} from "../../entities/market-offer/market-offer.model";

@Component({
  selector: 'jhi-marketplace-section',
  templateUrl: './marketplace-section.component.html',
  styles: []
})
export class MarketplaceSectionComponent implements OnInit {

    @Input() offers: MarketOffer[];

    constructor() {
    }

    ngOnInit() {
    }

}
