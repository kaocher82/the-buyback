import { BaseEntity } from './../../shared';

export class Contracts implements BaseEntity {
    constructor(
        public id?: number,
        public client?: string,
        public created?: any,
        public price?: number,
        public jitaBuy?: number,
        public declineMailSent?: boolean,
    ) {
    }
}
