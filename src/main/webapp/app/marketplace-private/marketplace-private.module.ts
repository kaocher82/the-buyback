import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TheBuybackSharedModule } from '../shared';

import { MarketplacePrivate_ROUTE, MarketplacePrivateComponent } from './';
import {MarketplacePrivateSectionComponent} from "./marketplace-section/marketplace-section.component";
import {MarketplacePrivateEntryComponent} from "./marketplace-entry/marketplace-entry.component";

@NgModule({
    imports: [
        TheBuybackSharedModule,
        RouterModule.forRoot([ MarketplacePrivate_ROUTE ], { useHash: true })
    ],
    declarations: [
        MarketplacePrivateComponent,
        MarketplacePrivateSectionComponent,
        MarketplacePrivateEntryComponent
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TheBuybackMarketplacePrivateModule {}
