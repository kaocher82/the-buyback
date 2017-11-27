import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { MarketOffer } from './market-offer.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MarketOfferService {

    private resourceUrl = 'api/market-offers';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(marketOffer: MarketOffer): Observable<MarketOffer> {
        const copy = this.convert(marketOffer);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(marketOffer: MarketOffer): Observable<MarketOffer> {
        const copy = this.convert(marketOffer);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: string): Observable<MarketOffer> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: string): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.created = this.dateUtils
            .convertDateTimeFromServer(entity.created);
        entity.expiry = this.dateUtils
            .convertDateTimeFromServer(entity.expiry);
        entity.expiryUpdated = this.dateUtils
            .convertDateTimeFromServer(entity.expiryUpdated);
    }

    private convert(marketOffer: MarketOffer): MarketOffer {
        const copy: MarketOffer = Object.assign({}, marketOffer);

        copy.created = this.dateUtils.toDate(marketOffer.created);

        copy.expiry = this.dateUtils.toDate(marketOffer.expiry);

        copy.expiryUpdated = this.dateUtils.toDate(marketOffer.expiryUpdated);
        return copy;
    }
}
