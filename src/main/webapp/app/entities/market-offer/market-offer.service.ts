import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MarketOffer } from './market-offer.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MarketOffer>;

@Injectable()
export class MarketOfferService {

    private resourceUrl =  SERVER_API_URL + 'api/market-offers';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(marketOffer: MarketOffer): Observable<EntityResponseType> {
        const copy = this.convert(marketOffer);
        return this.http.post<MarketOffer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(marketOffer: MarketOffer): Observable<EntityResponseType> {
        const copy = this.convert(marketOffer);
        return this.http.put<MarketOffer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<MarketOffer>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MarketOffer[]>> {
        const options = createRequestOption(req);
        return this.http.get<MarketOffer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MarketOffer[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MarketOffer = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MarketOffer[]>): HttpResponse<MarketOffer[]> {
        const jsonResponse: MarketOffer[] = res.body;
        const body: MarketOffer[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MarketOffer.
     */
    private convertItemFromServer(marketOffer: MarketOffer): MarketOffer {
        const copy: MarketOffer = Object.assign({}, marketOffer);
        copy.created = this.dateUtils
            .convertDateTimeFromServer(marketOffer.created);
        copy.expiry = this.dateUtils
            .convertDateTimeFromServer(marketOffer.expiry);
        copy.expiryUpdated = this.dateUtils
            .convertDateTimeFromServer(marketOffer.expiryUpdated);
        return copy;
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
