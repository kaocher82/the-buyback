import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MarketOffer } from './market-offer.model';
import { MarketOfferPopupService } from './market-offer-popup.service';
import { MarketOfferService } from './market-offer.service';

@Component({
    selector: 'jhi-market-offer-dialog',
    templateUrl: './market-offer-dialog.component.html'
})
export class MarketOfferDialogComponent implements OnInit {

    marketOffer: MarketOffer;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private marketOfferService: MarketOfferService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.marketOffer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.marketOfferService.update(this.marketOffer));
        } else {
            this.subscribeToSaveResponse(
                this.marketOfferService.create(this.marketOffer));
        }
    }

    private subscribeToSaveResponse(result: Observable<MarketOffer>) {
        result.subscribe((res: MarketOffer) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MarketOffer) {
        this.eventManager.broadcast({ name: 'marketOfferListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-market-offer-popup',
    template: ''
})
export class MarketOfferPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private marketOfferPopupService: MarketOfferPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.marketOfferPopupService
                    .open(MarketOfferDialogComponent as Component, params['id']);
            } else {
                this.marketOfferPopupService
                    .open(MarketOfferDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
