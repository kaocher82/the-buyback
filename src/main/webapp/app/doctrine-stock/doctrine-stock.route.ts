import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { DoctrineStockComponent } from './doctrine-stock.component';

const OR_ROUTES = [
    // auditsRoute,
    // configurationRoute,
];

export const doctrineStockState: Routes = [{
    path: 'doctrine-stock',
    component: DoctrineStockComponent,
    data: {
        authorities: ['ROLE_USER']
    },
    canActivate: [UserRouteAccessService],
    children: OR_ROUTES
},
];
