import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { MarketOffer } from './market-offer.model';
import { MarketOfferService } from './market-offer.service';

@Injectable()
export class MarketOfferPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private marketOfferService: MarketOfferService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.marketOfferService.find(id).subscribe((marketOffer) => {
                    marketOffer.created = this.datePipe
                        .transform(marketOffer.created, 'yyyy-MM-ddTHH:mm:ss');
                    marketOffer.expiry = this.datePipe
                        .transform(marketOffer.expiry, 'yyyy-MM-ddTHH:mm:ss');
                    marketOffer.expiryUpdated = this.datePipe
                        .transform(marketOffer.expiryUpdated, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.marketOfferModalRef(component, marketOffer);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.marketOfferModalRef(component, new MarketOffer());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    marketOfferModalRef(component: Component, marketOffer: MarketOffer): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.marketOffer = marketOffer;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
