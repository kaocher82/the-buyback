import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { TheBuybackSharedModule, UserRouteAccessService } from './shared';
import { TheBuybackHomeModule } from './home/home.module';
import { TheBuybackAdminModule } from './admin/admin.module';
import { TheBuybackAccountModule } from './account/account.module';
import { TheBuybackEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';
import {SsoComponent} from "./layouts/callback/sso.component";
import {TheBuybackTheBuybackModule} from "./the-buyback/the-buyback.module";
import {LoginComponent} from "./layouts/login/login.component";
import {TheBuybackOrderCapsModule} from "./order-caps/order-caps.module";
import {TheBuybackMarketPlaceModule} from "./marketplace/marketplace.module";
import {TheBuybackMarketplacePrivateModule} from "./marketplace-private/marketplace-private.module";
import {LayoutRoutingModule} from "./app-routing.module";
import {TheBuybackDoctrineStockModule} from "./doctrine-stock/doctrine-stock.module";
import {TheBuybackItemStockModule} from "./item-stock/item-stock.module";
import {TheBuybackAssetsModule} from "./assets/assets.module";

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        TheBuybackSharedModule,
        TheBuybackHomeModule,
        TheBuybackAdminModule,
        TheBuybackAccountModule,
        TheBuybackEntityModule,
        TheBuybackTheBuybackModule,
        TheBuybackOrderCapsModule,
        TheBuybackMarketPlaceModule,
        TheBuybackMarketplacePrivateModule,
        TheBuybackDoctrineStockModule,
        TheBuybackItemStockModule,
        TheBuybackAssetsModule
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent,
        SsoComponent,
        LoginComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class TheBuybackAppModule {}
