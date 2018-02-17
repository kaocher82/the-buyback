import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { ItemStockComponent } from './item-stock.component';

const OR_ROUTES = [
    // auditsRoute,
    // configurationRoute,
];

export const orderReviewState: Routes = [{
    path: 'doctrine-stock/:systemName/:typeId',
    component: ItemStockComponent,
    data: {
        authorities: ['ROLE_USER']
    },
    canActivate: [UserRouteAccessService],
    children: OR_ROUTES
},
];
