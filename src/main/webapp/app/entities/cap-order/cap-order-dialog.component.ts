import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CapOrder } from './cap-order.model';
import { CapOrderPopupService } from './cap-order-popup.service';
import { CapOrderService } from './cap-order.service';

@Component({
    selector: 'jhi-cap-order-dialog',
    templateUrl: './cap-order-dialog.component.html'
})
export class CapOrderDialogComponent implements OnInit {

    capOrder: CapOrder;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private capOrderService: CapOrderService,
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
        if (this.capOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.capOrderService.update(this.capOrder));
        } else {
            this.subscribeToSaveResponse(
                this.capOrderService.create(this.capOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<CapOrder>) {
        result.subscribe((res: CapOrder) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CapOrder) {
        this.eventManager.broadcast({ name: 'capOrderListModification', content: 'OK'});
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
    selector: 'jhi-cap-order-popup',
    template: ''
})
export class CapOrderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private capOrderPopupService: CapOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.capOrderPopupService
                    .open(CapOrderDialogComponent as Component, params['id']);
            } else {
                this.capOrderPopupService
                    .open(CapOrderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
