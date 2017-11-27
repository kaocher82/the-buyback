import {Route} from '@angular/router';

import {MarketplacePrivateComponent} from './';

export const MarketplacePrivate_ROUTE: Route = {
    path: 'marketplace-private',
    component: MarketplacePrivateComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'The Buyback - Marketplace'
    }
};
