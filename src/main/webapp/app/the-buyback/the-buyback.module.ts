import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TheBuybackSharedModule } from '../shared';

import { TheBuyback_ROUTE, TheBuybackComponent } from './';
import {AppraisalComponent} from "../appraisal/appraisal.component";
import {CapsOnContractComponent} from "../caps-on-contract/capsoncontract.component";

@NgModule({
    imports: [
        TheBuybackSharedModule,
        RouterModule.forRoot([ TheBuyback_ROUTE ], { useHash: true })
    ],
    declarations: [
        TheBuybackComponent,
        CapsOnContractComponent,
        AppraisalComponent
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TheBuybackTheBuybackModule {}
