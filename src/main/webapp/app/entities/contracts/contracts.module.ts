import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import {
    ContractsService,
    ContractsComponent,
    contractsRoute,
} from './';
import {TheBuybackSharedModule} from "../../shared/shared.module";

const ENTITY_STATES = [
    ...contractsRoute,
];

@NgModule({
    imports: [
        TheBuybackSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ContractsComponent,
    ],
    entryComponents: [
        ContractsComponent,
    ],
    providers: [
        ContractsService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContractsModule {}
