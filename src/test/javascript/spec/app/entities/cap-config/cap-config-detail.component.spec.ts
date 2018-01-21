/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { TheBuybackTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CapConfigDetailComponent } from '../../../../../../main/webapp/app/entities/cap-config/cap-config-detail.component';
import { CapConfigService } from '../../../../../../main/webapp/app/entities/cap-config/cap-config.service';
import { CapConfig } from '../../../../../../main/webapp/app/entities/cap-config/cap-config.model';

describe('Component Tests', () => {

    describe('CapConfig Management Detail Component', () => {
        let comp: CapConfigDetailComponent;
        let fixture: ComponentFixture<CapConfigDetailComponent>;
        let service: CapConfigService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [TheBuybackTestModule],
                declarations: [CapConfigDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CapConfigService,
                    JhiEventManager
                ]
            }).overrideTemplate(CapConfigDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CapConfigDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CapConfigService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CapConfig('aaa')));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.capConfig).toEqual(jasmine.objectContaining({id: 'aaa'}));
            });
        });
    });

});
