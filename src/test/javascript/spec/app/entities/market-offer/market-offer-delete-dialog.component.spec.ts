/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { TheBuybackTestModule } from '../../../test.module';
import { MarketOfferDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/market-offer/market-offer-delete-dialog.component';
import { MarketOfferService } from '../../../../../../main/webapp/app/entities/market-offer/market-offer.service';

describe('Component Tests', () => {

    describe('MarketOffer Management Delete Component', () => {
        let comp: MarketOfferDeleteDialogComponent;
        let fixture: ComponentFixture<MarketOfferDeleteDialogComponent>;
        let service: MarketOfferService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TheBuybackTestModule],
                declarations: [MarketOfferDeleteDialogComponent],
                providers: [
                    MarketOfferService
                ]
            })
            .overrideTemplate(MarketOfferDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketOfferDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketOfferService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete('123');
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith('123');
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
