import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Http} from "@angular/http";
import {Appraisal} from "./appraisal.model";
import {ClipboardService} from "./clipboard.service";

@Component({
    selector: 'jhi-appraisal',
    templateUrl: './appraisal.component.html'
})
export class AppraisalComponent implements OnInit {

    isLoadingAppraisal: boolean;
    submitDone: boolean;
    appraisal: Appraisal;

    showCopiedPrice: boolean;
    showCopiedLink: boolean;

    constructor(
        private http: Http,
        private clipboard: ClipboardService
    ) {
    }

    ngOnInit(): void {
        if (!this.appraisal) {
            this.appraisal = new Appraisal();
        }
    }

    executeAppraisal() {
        this.isLoadingAppraisal = true;
        this.submitDone = false;
        return this.http.post('api/appraisal', this.appraisal).subscribe((data) => {
            this.appraisal = data.json();
            this.isLoadingAppraisal = false;
        });
    }

    clearAppraisal() {
        this.appraisal = new Appraisal();
    }

    copyPrice() {
        this.clipboard.copy(this.appraisal.buybackPrice + '');

        this.showCopiedPrice = true;
        setTimeout(function() {
            this.showCopiedPrice = false;
        }.bind(this), 4000);
    }

    copyLink() {
        this.clipboard.copy(this.appraisal.link);

        this.showCopiedLink = true;
        setTimeout(function() {
            this.showCopiedLink = false;
        }.bind(this), 4000);
    }
}
