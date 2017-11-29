import {Component, Input, OnInit} from '@angular/core';
import {MarketOffer} from "../../entities/market-offer/market-offer.model";
import {Http} from "@angular/http";

@Component({
  selector: 'jhi-marketplace-private-entry',
  templateUrl: './marketplace-entry.component.html',
  styles: []
})
export class MarketplacePrivateEntryComponent implements OnInit {

    @Input() offer: MarketOffer;
    editActive: boolean;
    deleted: boolean;

    constructor(private http: Http) {
        this.editActive = false;
    }

    ngOnInit() {
        if (!this.offer.id) {
            this.editActive = true;
        }
    }

    setEdit(editActive: boolean): void {
        if (!editActive) {
            this.http.put("/api/market-offers", this.offer).subscribe(
                (data) => {
                    this.offer = data.json();
                    this.editActive = false;
                    this.offer.expiry = new Date().toISOString();
                    this.extendUI();
                },
                (err) => alert("Save failed, make sure the link is valid. Your changes were not persisted.")
            );
        } else {
            this.editActive = true;
        }
    }

    canExtend(): boolean {
        const date = new Date(this.offer.expiry);
        date.setDate(date.getDate() - 7);
        return new Date() > date;
    }

    extend(): void {
        this.extendUI();
    }

    private extendUI() {
        let days;
        if (this.offer.id) {
            days = 14;
        } else {
            days = 7;
        }
        const date = new Date(this.offer.expiry);
        date.setDate(date.getDate() + days);
        this.offer.expiry = date.toISOString();
        this.offer.expiryUpdated = new Date().toISOString();
    }

    delete(): void {
        if (this.offer.id) {
            this.http.delete("/api/market-offers/" + this.offer.id).subscribe(
                (data) => this.deleted = true,
                (err) => alert("Save failed. Your changes were not persisted.")
            );
        } else {
            this.deleted = true;
        }
    }

}
