import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MarketOffer } from './market-offer.model';
import { MarketOfferPopupService } from './market-offer-popup.service';
import { MarketOfferService } from './market-offer.service';

@Component({
    selector: 'jhi-market-offer-delete-dialog',
    templateUrl: './market-offer-delete-dialog.component.html'
})
export class MarketOfferDeleteDialogComponent {

    marketOffer: MarketOffer;

    constructor(
        private marketOfferService: MarketOfferService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.marketOfferService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'marketOfferListModification',
                content: 'Deleted an marketOffer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-market-offer-delete-popup',
    template: ''
})
export class MarketOfferDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketOfferPopupService: MarketOfferPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.marketOfferPopupService
                .open(MarketOfferDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
