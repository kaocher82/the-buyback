import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TheBuybackSharedModule } from '../../shared';
import {
    CapConfigService,
    CapConfigPopupService,
    CapConfigComponent,
    CapConfigDetailComponent,
    CapConfigDialogComponent,
    CapConfigPopupComponent,
    CapConfigDeletePopupComponent,
    CapConfigDeleteDialogComponent,
    capConfigRoute,
    capConfigPopupRoute,
} from './';

const ENTITY_STATES = [
    ...capConfigRoute,
    ...capConfigPopupRoute,
];

@NgModule({
    imports: [
        TheBuybackSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CapConfigComponent,
        CapConfigDetailComponent,
        CapConfigDialogComponent,
        CapConfigDeleteDialogComponent,
        CapConfigPopupComponent,
        CapConfigDeletePopupComponent,
    ],
    entryComponents: [
        CapConfigComponent,
        CapConfigDialogComponent,
        CapConfigPopupComponent,
        CapConfigDeleteDialogComponent,
        CapConfigDeletePopupComponent,
    ],
    providers: [
        CapConfigService,
        CapConfigPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TheBuybackCapConfigModule {}
