import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MarketOfferComponent } from './market-offer.component';
import { MarketOfferDetailComponent } from './market-offer-detail.component';
import { MarketOfferPopupComponent } from './market-offer-dialog.component';
import { MarketOfferDeletePopupComponent } from './market-offer-delete-dialog.component';

@Injectable()
export class MarketOfferResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const marketOfferRoute: Routes = [
    {
        path: 'market-offer',
        component: MarketOfferComponent,
        resolve: {
            'pagingParams': MarketOfferResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MarketOffers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'market-offer/:id',
        component: MarketOfferDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MarketOffers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const marketOfferPopupRoute: Routes = [
    {
        path: 'market-offer-new',
        component: MarketOfferPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MarketOffers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-offer/:id/edit',
        component: MarketOfferPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MarketOffers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'market-offer/:id/delete',
        component: MarketOfferDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'MarketOffers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
