import {Routes} from '@angular/router';

import {UserRouteAccessService} from '../../shared';

import {ContractsComponent} from './contracts.component';

export const contractsRoute: Routes = [
    {
        path: 'contracts',
        component: ContractsComponent,
        data: {
            authorities: ['ROLE_MANAGER'],
            pageTitle: 'Contracts'
        },
        canActivate: [UserRouteAccessService]
    }
];
