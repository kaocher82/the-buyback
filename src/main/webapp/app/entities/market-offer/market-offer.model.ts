import { BaseEntity } from './../../shared';

const enum MarketOfferType {
    'SELL',
    'BUY'
}

const enum MarketOfferCategory {
    'NONE'
}

export class MarketOffer implements BaseEntity {
    constructor(
        public id?: string,
        public issuer?: string,
        public created?: any,
        public expiry?: any,
        public expiryUpdated?: any,
        public type?: MarketOfferType,
        public category?: MarketOfferCategory,
        public location?: string,
        public isRecurring?: boolean,
        public text?: string,
        public appraisalLink?: string,
    ) {
        this.isRecurring = false;
    }
}
