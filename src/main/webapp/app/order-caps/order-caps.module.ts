import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TheBuybackSharedModule } from '../shared';

import { OrderCaps_ROUTE, OrderCapsComponent } from './';

@NgModule({
    imports: [
        TheBuybackSharedModule,
        RouterModule.forRoot([ OrderCaps_ROUTE ], { useHash: true })
    ],
    declarations: [
        OrderCapsComponent
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TheBuybackOrderCapsModule {}
