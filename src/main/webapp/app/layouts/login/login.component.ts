import {Component, OnInit} from '@angular/core';
import {Http} from "@angular/http";

@Component({selector: 'jhi-login', templateUrl: './login.component.html'})
export class LoginComponent implements OnInit {

    constructor(private http: Http) {
    }

    ngOnInit(): void {
        this.http.get('api/config/ssourl').subscribe((data) => window.location.replace(data.text()));
    }
}
