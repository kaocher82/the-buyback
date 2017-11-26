/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TheBuybackTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CapOrderDetailComponent } from '../../../../../../main/webapp/app/entities/cap-order/cap-order-detail.component';
import { CapOrderService } from '../../../../../../main/webapp/app/entities/cap-order/cap-order.service';
import { CapOrder } from '../../../../../../main/webapp/app/entities/cap-order/cap-order.model';

describe('Component Tests', () => {

    describe('CapOrder Management Detail Component', () => {
        let comp: CapOrderDetailComponent;
        let fixture: ComponentFixture<CapOrderDetailComponent>;
        let service: CapOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TheBuybackTestModule],
                declarations: [CapOrderDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CapOrderService,
                    JhiEventManager
                ]
            }).overrideTemplate(CapOrderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CapOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CapOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CapOrder('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.capOrder).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
