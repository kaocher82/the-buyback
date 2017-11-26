import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TheBuybackSharedModule } from '../shared';

import { OrderCaps_ROUTE, OrderCapsComponent } from './';
import {HullSelectComponent} from "./hull-select/hull-select.component";

@NgModule({
    imports: [
        TheBuybackSharedModule,
        RouterModule.forRoot([ OrderCaps_ROUTE ], { useHash: true })
    ],
    declarations: [
        OrderCapsComponent,
        HullSelectComponent
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TheBuybackOrderCapsModule {}
