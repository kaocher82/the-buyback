/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { TheBuybackTestModule } from '../../../test.module';
import { MarketOfferComponent } from '../../../../../../main/webapp/app/entities/market-offer/market-offer.component';
import { MarketOfferService } from '../../../../../../main/webapp/app/entities/market-offer/market-offer.service';
import { MarketOffer } from '../../../../../../main/webapp/app/entities/market-offer/market-offer.model';

describe('Component Tests', () => {

    describe('MarketOffer Management Component', () => {
        let comp: MarketOfferComponent;
        let fixture: ComponentFixture<MarketOfferComponent>;
        let service: MarketOfferService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TheBuybackTestModule],
                declarations: [MarketOfferComponent],
                providers: [
                    MarketOfferService
                ]
            })
            .overrideTemplate(MarketOfferComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketOfferComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketOfferService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new MarketOffer('123')],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.marketOffers[0]).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
