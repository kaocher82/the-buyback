import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MarketOffer } from './market-offer.model';
import { MarketOfferService } from './market-offer.service';

@Component({
    selector: 'jhi-market-offer-detail',
    templateUrl: './market-offer-detail.component.html'
})
export class MarketOfferDetailComponent implements OnInit, OnDestroy {

    marketOffer: MarketOffer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private marketOfferService: MarketOfferService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarketOffers();
    }

    load(id) {
        this.marketOfferService.find(id).subscribe((marketOffer) => {
            this.marketOffer = marketOffer;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarketOffers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketOfferListModification',
            (response) => this.load(this.marketOffer.id)
        );
    }
}
