import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TheBuybackSharedModule } from '../../shared';
import {
    MarketOfferService,
    MarketOfferPopupService,
    MarketOfferComponent,
    MarketOfferDetailComponent,
    MarketOfferDialogComponent,
    MarketOfferPopupComponent,
    MarketOfferDeletePopupComponent,
    MarketOfferDeleteDialogComponent,
    marketOfferRoute,
    marketOfferPopupRoute,
    MarketOfferResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...marketOfferRoute,
    ...marketOfferPopupRoute,
];

@NgModule({
    imports: [
        TheBuybackSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MarketOfferComponent,
        MarketOfferDetailComponent,
        MarketOfferDialogComponent,
        MarketOfferDeleteDialogComponent,
        MarketOfferPopupComponent,
        MarketOfferDeletePopupComponent,
    ],
    entryComponents: [
        MarketOfferComponent,
        MarketOfferDialogComponent,
        MarketOfferPopupComponent,
        MarketOfferDeleteDialogComponent,
        MarketOfferDeletePopupComponent,
    ],
    providers: [
        MarketOfferService,
        MarketOfferPopupService,
        MarketOfferResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TheBuybackMarketOfferModule {}
