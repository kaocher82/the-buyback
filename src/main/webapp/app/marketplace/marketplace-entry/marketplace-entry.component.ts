import {Component, Input, OnInit} from '@angular/core';
import {MarketOffer} from "../../entities/market-offer/market-offer.model";

@Component({
  selector: 'jhi-marketplace-entry',
  templateUrl: './marketplace-entry.component.html',
  styles: []
})
export class MarketplaceEntryComponent implements OnInit {

    @Input() offer: MarketOffer;

  constructor() { }

  ngOnInit() {
  }

}
