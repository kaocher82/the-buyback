import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {CapitalSalesComponent} from "./capital-sales.component";

export const capitalSales: Routes = [
    {
        path: 'capital-sales',
        component: CapitalSalesComponent,
        data: {
            authorities: ['ROLE_MANAGER'],
            pageTitle: 'Capital Sales'
        },
        canActivate: [UserRouteAccessService]
    }
];
