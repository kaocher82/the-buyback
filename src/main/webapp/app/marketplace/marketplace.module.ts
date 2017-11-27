import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TheBuybackSharedModule } from '../shared';

import { MarketPlace_ROUTE, MarketPlaceComponent } from './';
import {MarketplaceSectionComponent} from "./marketplace-section/marketplace-section.component";
import {MarketplaceEntryComponent} from "./marketplace-entry/marketplace-entry.component";

@NgModule({
    imports: [
        TheBuybackSharedModule,
        RouterModule.forRoot([ MarketPlace_ROUTE ], { useHash: true })
    ],
    declarations: [
        MarketPlaceComponent,
        MarketplaceSectionComponent,
        MarketplaceEntryComponent
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TheBuybackMarketPlaceModule {}
