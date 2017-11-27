import {Route} from '@angular/router';

import {MarketPlaceComponent} from './';

export const MarketPlace_ROUTE: Route = {
    path: 'marketplace',
    component: MarketPlaceComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'The Buyback - Marketplace'
    }
};
