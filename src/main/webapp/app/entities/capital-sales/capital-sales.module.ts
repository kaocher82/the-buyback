import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import {
    CapitalSalesComponent,
    capitalSales,
} from './';
import {TheBuybackSharedModule} from "../../shared/shared.module";

const ENTITY_STATES = [
    ...capitalSales,
];

@NgModule({
    imports: [
        TheBuybackSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CapitalSalesComponent,
    ],
    entryComponents: [
        CapitalSalesComponent,
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CapitalSalesModule {}
