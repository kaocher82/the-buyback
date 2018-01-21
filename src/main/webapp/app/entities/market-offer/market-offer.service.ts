import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MarketOffer } from './market-offer.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MarketOfferService {

    private resourceUrl =  SERVER_API_URL + 'api/market-offers';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(marketOffer: MarketOffer): Observable<MarketOffer> {
        const copy = this.convert(marketOffer);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(marketOffer: MarketOffer): Observable<MarketOffer> {
        const copy = this.convert(marketOffer);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: string): Observable<MarketOffer> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
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
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to MarketOffer.
     */
    private convertItemFromServer(json: any): MarketOffer {
        const entity: MarketOffer = Object.assign(new MarketOffer(), json);
        entity.created = this.dateUtils
            .convertDateTimeFromServer(json.created);
        entity.expiry = this.dateUtils
            .convertDateTimeFromServer(json.expiry);
        entity.expiryUpdated = this.dateUtils
            .convertDateTimeFromServer(json.expiryUpdated);
        return entity;
    }

    /**
     * Convert a MarketOffer to a JSON which can be sent to the server.
     */
    private convert(marketOffer: MarketOffer): MarketOffer {
        const copy: MarketOffer = Object.assign({}, marketOffer);

        copy.created = this.dateUtils.toDate(marketOffer.created);

        copy.expiry = this.dateUtils.toDate(marketOffer.expiry);

        copy.expiryUpdated = this.dateUtils.toDate(marketOffer.expiryUpdated);
        return copy;
    }
}
