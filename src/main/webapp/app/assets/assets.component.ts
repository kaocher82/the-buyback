import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {Http} from "@angular/http";
declare let ga: Function;

@Component({
               selector: 'jhi-assets', templateUrl: './assets.component.html', styles: []
           })
export class AssetsComponent {

    loading: boolean;
    data: any;
    errorMessage: string;

    searchText: string;

    constructor(private http: Http) { }

    search() {
        this.loading = false;
        this.errorMessage = null;
        const request = { 'text': this.searchText }
        this.http.post('api/assets', request).subscribe((data) => {
            this.data = data.json();
            this.loading = false;
        }, (err) => {
            this.errorMessage = "Something bad happened. Did you try to sneak in more than 10 items?"
            this.loading = false;
        });
    }

}
