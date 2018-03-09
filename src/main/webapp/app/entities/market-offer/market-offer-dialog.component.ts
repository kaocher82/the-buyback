import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

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

    private subscribeToSaveResponse(result: Observable<HttpResponse<MarketOffer>>) {
        result.subscribe((res: HttpResponse<MarketOffer>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MarketOffer) {
        this.eventManager.broadcast({ name: 'marketOfferListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
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
