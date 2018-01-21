/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TheBuybackTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MarketOfferDetailComponent } from '../../../../../../main/webapp/app/entities/market-offer/market-offer-detail.component';
import { MarketOfferService } from '../../../../../../main/webapp/app/entities/market-offer/market-offer.service';
import { MarketOffer } from '../../../../../../main/webapp/app/entities/market-offer/market-offer.model';

describe('Component Tests', () => {

    describe('MarketOffer Management Detail Component', () => {
        let comp: MarketOfferDetailComponent;
        let fixture: ComponentFixture<MarketOfferDetailComponent>;
        let service: MarketOfferService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TheBuybackTestModule],
                declarations: [MarketOfferDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MarketOfferService,
                    JhiEventManager
                ]
            }).overrideTemplate(MarketOfferDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketOfferDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketOfferService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MarketOffer('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.marketOffer).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
