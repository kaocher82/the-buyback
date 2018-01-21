import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TheBuybackSharedModule } from '../shared';

import {
    SettingsComponent,
    accountState
} from './';

@NgModule({
    imports: [
        TheBuybackSharedModule,
        RouterModule.forChild(accountState)
    ],
    declarations: [
        SettingsComponent
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TheBuybackAccountModule {}
