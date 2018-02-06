import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import {
    ConsolidationComponent,
    consolidation,
} from './';
import {TheBuybackSharedModule} from "../../shared/shared.module";

const ENTITY_STATES = [
    ...consolidation,
];

@NgModule({
    imports: [
        TheBuybackSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ConsolidationComponent,
    ],
    entryComponents: [
        ConsolidationComponent,
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ConsolidationModule {}
