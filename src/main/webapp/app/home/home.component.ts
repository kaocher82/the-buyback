import { Component, OnInit } from '@angular/core';
import { JhiEventManager } from 'ng-jhipster';

import { Account, Principal } from '../shared';
import {Router} from "@angular/router";

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]
})
export class HomeComponent implements OnInit {
    account: Account;

    constructor(
        private principal: Principal,
        private eventManager: JhiEventManager,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
            if (!account) {
                this.router.navigate(['/the-buyback']);
            } else {
                if (this.account.authorities.indexOf('ROLE_MANAGER') !== -1) {
                    this.router.navigate(['/contracts']);
                } else {
                    this.router.navigate(['/the-buyback']);
                }
            }

            // if (this.account.authorities.indexOf('ROLE_MANAGER'))
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

}
