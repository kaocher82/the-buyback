import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { AssetsComponent } from './assets.component';

const OR_ROUTES = [
    // auditsRoute,
    // configurationRoute,
];

export const assetsState: Routes = [{
    path: 'assets',
    component: AssetsComponent,
    data: {
        authorities: ['ROLE_USER']
    },
    canActivate: [UserRouteAccessService],
    children: OR_ROUTES
},
];
