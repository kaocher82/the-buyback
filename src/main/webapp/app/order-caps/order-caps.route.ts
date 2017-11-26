import {Route} from '@angular/router';

import {OrderCapsComponent} from './';

export const OrderCaps_ROUTE: Route = {
    path: 'order-caps',
    component: OrderCapsComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'The Buyback - Capitals'
    }
};
