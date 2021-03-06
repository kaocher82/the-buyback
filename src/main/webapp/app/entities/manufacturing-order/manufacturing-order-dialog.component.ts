import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {HttpResponse, HttpErrorResponse, HttpClient} from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ManufacturingOrder } from './manufacturing-order.model';
import { ManufacturingOrderPopupService } from './manufacturing-order-popup.service';
import { ManufacturingOrderService} from './manufacturing-order.service';

@Component({
    selector: 'jhi-manufacturing-order-dialog',
    templateUrl: './manufacturing-order-dialog.component.html'
})
export class ManufacturingOrderDialogComponent implements OnInit {

    manufacturingOrder: ManufacturingOrder;
    isSaving: boolean;
    itemDataLoaded: boolean;
    manufacturingMargin: number;
    geSell: number;
    jitaSell: number;
    daysRemaining: number;

    constructor(
        public activeModal: NgbActiveModal,
        private manufacturingOrderService: ManufacturingOrderService,
        private eventManager: JhiEventManager,
        private http: HttpClient
    ) {
    }

    getModifier(): number {
        return 1 + (this.manufacturingMargin / 100);
    }

    loadData() {
        console.log("Loading additional data.");
        this.itemDataLoaded = false;
        return this.http.get<any>(`api/stock/doctrines/item-details/` + this.manufacturingOrder.typeName).subscribe((data) => {
            this.geSell = data.sellPrice;
            this.jitaSell = data.jitaSell;
            this.daysRemaining = data.daysRemaining;
            this.manufacturingMargin = 10;
            this.itemDataLoaded = true;
        });
    }

    ngOnInit() {
        this.isSaving = false;
        if (this.manufacturingOrder && this.manufacturingOrder.typeName) {
            this.loadData();
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.manufacturingOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.manufacturingOrderService.update(this.manufacturingOrder));
        } else {
            this.subscribeToSaveResponse(
                this.manufacturingOrderService.create(this.manufacturingOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ManufacturingOrder>>) {
        result.subscribe((res: HttpResponse<ManufacturingOrder>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ManufacturingOrder) {
        this.eventManager.broadcast({ name: 'manufacturingOrderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-manufacturing-order-popup',
    template: ''
})
export class ManufacturingOrderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private manufacturingOrderPopupService: ManufacturingOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.manufacturingOrderPopupService
                    .open(ManufacturingOrderDialogComponent as Component, params['id']);
            } else {
                this.manufacturingOrderPopupService
                    .open(ManufacturingOrderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
