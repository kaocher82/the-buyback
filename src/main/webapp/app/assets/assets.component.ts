import {Component} from '@angular/core';
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
        this.data = null;
        this.loading = true;
        this.errorMessage = null;
        const request = { 'text': this.searchText }
        this.http.post('api/assets', request).subscribe((data) => {
            this.data = data.json();
            this.loading = false;
        }, (err) => {
            this.errorMessage = "Something bad happened. Did you try to sneak in more than 50 items?"
            this.loading = false;
        });
    }

}
