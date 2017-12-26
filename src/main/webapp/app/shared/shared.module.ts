import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    TheBuybackSharedLibsModule,
    TheBuybackSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    Principal,
    HasAnyAuthorityDirective,
    ConfigService
} from './';
import {CapsOnContractComponent} from "./caps-on-contract/capsoncontract.component";
import {AppraisalComponent} from "./appraisal/appraisal.component";
import {RouterModule} from "@angular/router";
import {ClipboardService} from "./appraisal/clipboard.service";

@NgModule({
    imports: [
        TheBuybackSharedLibsModule,
        TheBuybackSharedCommonModule,
        RouterModule
    ],
    declarations: [
        HasAnyAuthorityDirective,
        CapsOnContractComponent,
        AppraisalComponent
    ],
    providers: [
        LoginService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        UserService,
        DatePipe,
        ConfigService,
        ClipboardService
    ],
    entryComponents: [
    ],
    exports: [
        TheBuybackSharedCommonModule,
        HasAnyAuthorityDirective,
        DatePipe,
        CapsOnContractComponent,
        AppraisalComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class TheBuybackSharedModule {}
